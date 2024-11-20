# FlickBox

FlickBox is a movie and TV show streaming app with a modern design and smooth user experience. The app is built using the latest Android development tools and technologies to ensure strong performance and an engaging user experience.

## Technologies Used:
- **Jetpack Navigation Component**: To manage navigation between screens seamlessly, making it easy to handle transitions between the home screen, search screen, and details screen.
- **Dagger Hilt**: For dependency injection to manage app dependencies in a modular and scalable way.
- **Room Database**: For local data storage using SQLite, allowing users to access cached content even when offline.
- **Retrofit + OkHttp**: For fetching data from remote APIs related to movies and TV shows.
- **MVVM Architecture**: The Model-View-ViewModel design pattern is followed to separate the business logic, UI, and data, improving maintainability and testability.
- **Kotlin Coroutines**: To handle asynchronous tasks such as fetching data from the network or database without blocking the UI thread, ensuring a smooth and responsive experience.
- **Lottie Animations**: To add lightweight, interactive animations throughout the app, such as loading animations, to enhance the user experience.

## Main Screens:

1. **Splash Screen**:
   ![SplashScreen](https://github.com/user-attachments/assets/c966d47a-1b64-4004-8790-a66d266b1f9c)
   A splash screen with animations providing a smooth transition into the app.

2. **Home Screen**:
   ![HomeScreen](https://github.com/user-attachments/assets/1adbfe65-8b44-4173-a2e2-be0b9f9d35f6)
   Displays a list of movies and TV shows for users to browse and discover.

3. **Search Screen**:
   ![SearchScreen](https://github.com/user-attachments/assets/b04aa776-d26f-490f-9645-183d396a2f1e)
   The search screen allows users to find specific movies or TV shows using an interactive search feature.

4. **Details Screen**:
   ![DetailsScreen](https://github.com/user-attachments/assets/cce0f1d7-91c3-4e3a-a99e-67f3d932b5ec)
   Displays detailed information about a selected movie or TV show, including release dates, ratings, and a synopsis.

## Key Features:
- **Dynamic Search**: Users can search quickly and see results as they type.
- **Local Storage**: Room database ensures data can be accessed offline.
- **Smooth Navigation**: Jetpack Navigation makes it easy to navigate between screens.
- **High Performance**: Kotlin Coroutines and Retrofit ensure fast and smooth interactions.

## Build Instructions:
1. Clone this repository:
    ```bash
    git clone https://github.com/yourusername/FlickBox.git
    ```
2. Open the project in **Android Studio**.
3. Install dependencies using:
    ```bash
    ./gradlew build
    ```
4. Run the app on an emulator or physical device.

## Contributing:
If you'd like to contribute to the development of the app, feel free to open an **issue** or submit a **pull request** to improve or fix bugs in the app.

## App Link:
You can download or try out the app by visiting the following link:  
[Download FlickBox App](https://drive.google.com/drive/u/0/folders/1bLL1X7sdNWbaQ34jd2gnnHVUjGTDWCi4)

