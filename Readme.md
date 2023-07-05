# NFCCompose

NFCCompose is an Android app that serves as a code example on how to use the NFC stack to read information from an NFC tag on Android. The app is built using clean architecture principles and utilizes libraries such as Compose for building the user interface and Hilt for dependency injection.

![nfc_example](screenshots/nfc.gif)

## Features
- Read information from NFC tags using the Android NFC stack.
- Display the read NFC tag information in a user-friendly manner.
- Clean architecture for a modular and maintainable codebase.
- Utilizes Jetpack Compose for building a modern and declarative user interface.
- Dependency injection with Hilt for managing dependencies.

## Requirements
- Android device with NFC capabilities.
- Android SDK version 24 (Nougat) or higher.

## Setup and Configuration

1. Clone the repository:
```bash
git clone https://github.com/your-username/NFCCompose.git
```

2. Open the project in Android Studio.
3. Build and run the app on your Android device or emulator.

## Usage
1. Launch the NFCCompose app on your Android device. 
2. Ensure that NFC is enabled on your device. 
3. Bring an NFC tag close to your device. 
4. The app will automatically detect and read the information from the NFC tag. 
5. The tag information will be displayed on the screen.

## Project Structure
The project follows a modular structure based on clean architecture principles. Here's an overview of the main directories and their purposes:

- **data**: Includes the data layer implementation, including repositories, data sources, and network clients.
- **domain**: Defines the core business logic and use cases of the application.
- **presentation**: Contains the user interface and presentation logic implemented using Jetpack Compose.
- **di**: Handles dependency injection using Hilt.

## Libraries Used
- AndroidX: Android Jetpack libraries for modern Android development.
- Compose: Jetpack Compose UI toolkit for building declarative user interfaces.
- Hilt: Dependency injection library for Android.
- NFC: Android's Near Field Communication stack for reading NFC tags.