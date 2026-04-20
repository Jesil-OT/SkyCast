# SkyCast ☁️

**SkyCast** is a modern, high-precision weather companion designed to give you clarity on the conditions around you. Built with a focus on speed, accuracy, and a minimalist user experience, SkyCast ensures you're always prepared for whatever the clouds bring.

---

## 🌟 Key Features

* **📍 Real-Time Local Weather**: Automatically detects your current location to provide hyper-local temperature, humidity, and atmospheric conditions.
* **🏙️ Global City Tracking**: Search and save an unlimited number of cities. Monitor weather for your next travel destination or check in on family across the globe.
* **🔄 Instant Synchronization**: Powered by the **OpenWeather API**, providing live data updates at your fingertips.
* **🎨 Modern Interface**: A clean, ad-free UI built entirely with **Jetpack Compose** for a smooth and fluid experience.

---

## 📸 Screenshots
<img src = "https://github.com/Jesil-OT/SkyCast/blob/cities_feature/screenshots/Screenshot_20260420_125746.png" width="200" align="left"/> 
<img src = "https://github.com/Jesil-OT/SkyCast/blob/cities_feature/screenshots/Screenshot_20260420_125808.png" width="200" align="left"/>
<img src = "https://github.com/Jesil-OT/SkyCast/blob/cities_feature/screenshots/Screenshot_20260420_125824.png" width="200" align="left"/>
<img src = "https://github.com/Jesil-OT/SkyCast/blob/cities_feature/screenshots/Screenshot_20260420_125848.png" width="200" align="left"/>
<img src = "https://github.com/Jesil-OT/SkyCast/blob/cities_feature/screenshots/Screenshot_20260420_125941.png" width="200" align="left"/>
<img src = "https://github.com/Jesil-OT/SkyCast/blob/cities_feature/screenshots/Screenshot_20260420_130033.png" width="200" align="left"/>
<img src = "https://github.com/Jesil-OT/SkyCast/blob/cities_feature/screenshots/Screenshot_20260420_130240.png" width="200" align="left"/>
<img src = "https://github.com/Jesil-OT/SkyCast/blob/cities_feature/screenshots/Screenshot_20260420_130305.png" width="200" align="left"/>

---

# Author

## 🚀 Technical Stack

This project serves as a showcase of modern Android development best practices:

* **Language**: [Kotlin](https://kotlinlang.org/)
* **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
* **Networking**: [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/)
* **Local Database**: [Room Persistence Library](https://developer.android.com/training/data-storage/room)
* **Concurrency**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
* **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* **Data Source**: [OpenWeather API](https://openweathermap.org/api)

---

## 🛠️ Installation & Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Jesil-OT/Skycast.git

2. **Get an API Key**:
   * Visit [OpenWeatherMap](https://openweathermap.org/).
   * Create a free account and navigate to the **API Keys** tab in your dashboard.
   * Generate a new key (it may take up to 2 hours to activate).

3. **Configure the API Key**:
   * Open your project in Android Studio.
   * Locate your `local.properties` file in the root directory.
   * Add the following line:
     ```properties
     OPENWEATHER_API_KEY=your_api_key_here
     ```

4. **Build and Run**:
   * Sync your project with Gradle files.
   * Select your device or emulator.
   * Click the **Run** button (Shift + F10).

---

## 📋 Permissions

To provide a seamless weather experience, SkyCast utilizes the following permissions:
* `ACCESS_FINE_LOCATION`: Used to fetch high-accuracy weather data for your specific coordinate.
* `ACCESS_COARSE_LOCATION`: Used as a battery-efficient fallback for location detection.
* `INTERNET`: Required to communicate with the OpenWeather servers.

---

## 📄 License

This project is licensed under the **MIT License**. You are free to use, modify, and distribute this software with attribution. 

---

> **SkyCast** — *Clearer skies, smarter days.*
