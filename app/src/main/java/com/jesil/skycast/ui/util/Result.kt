package com.jesil.skycast.ui.util

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

//typealias DomainError = Error
interface Error

sealed interface Re<out D, out E : Error> {
    data class Success<out D>(val data: D) : Re<D, Nothing>
    data class Error<out E : com.jesil.skycast.ui.util.Error>(val error: E) : Re<Nothing, E>
}

inline fun <T, E : Error, R> Re<T, E>.map(map: (T) -> R): Re<R, E> {
    return when (this) {
        is Re.Error -> Re.Error(error)
        is Re.Success -> Re.Success(map(data))
    }
}

inline fun <T, E : Error> Re<T, E>.onSuccess(action: (T) -> Unit): Re<T, E> {
    return when (this) {
        is Re.Error -> this
        is Re.Success -> {
            action(data)
            this
        }
    }
}

inline fun <T, E : Error> Re<T, E>.onError(action: (E) -> Unit): Re<T, E> {
    return when (this) {
        is Re.Error -> {
            action(error)
            this
        }

        is Re.Success -> this
    }
}

sealed class Response<out T> {
    data object Loading : Response<Nothing>()
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val message: String, val code: Int = 0) : Response<Nothing>()
}



inline fun <reified T> safeApiCall(
   crossinline execute: suspend () -> HttpResponse
): Flow<Response<T>> = flow {
    emit(Response.Loading)

    try {
        val response = execute()
        emit(responseToResult(response))
    } catch (e: UnresolvedAddressException) {
        emit(Response.Error("NetworkError.NO_INTERNET_CONNECTION"))
    } catch (e: SerializationException) {
        emit(Response.Error("NetworkError.SERIALIZATION_ERROR"))
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        emit(Response.Error("NetworkError.UNKNOWN_ERROR"))
    }
}

suspend inline fun <reified T> responseToResult(
    httpResponse: HttpResponse
): Response<T> {
    return when (httpResponse.status.value) {
        in 200..299 -> {

            try {
                val data = httpResponse.body<T>()
                return Response.Success(data)
            } catch (e: NoTransformationFoundException) {
                Response.Error("NetworkError.SERIALIZATION_ERROR")
            }
        }

        400 -> Response.Error("NetworkError.BAD_REQUEST")
        401 -> Response.Error("NetworkError.UNAUTHORIZED")
        403 -> Response.Error("NetworkError.FORBIDDEN")
        404 -> Response.Error("NetworkError.NOT_FOUND")
        429 -> Response.Error("NetworkError.TOO_MANY_REQUESTS")
        in 500..599 -> Response.Error("NetworkError.SERVER_ERROR")
        else -> Response.Error("NetworkError.UNKNOWN_ERROR")
    }
}
