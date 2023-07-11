# Image Enhance CutouPro Android Application

Image Enhance CutouPro is an Android application that allows users to enhance their old images or photos using the CutouPro API. The app utilizes Retrofit 2 for network requests and Hilt for dependency injection.

## Features

- Image Enhancement: Enhance the quality of old images or photos.
- Cutout Pro API Integration: Utilize the CutouPro API to enhance images.
- Retrofit 2: Perform network requests efficiently.
- Hilt: Simplify dependency injection in the application.

## Installation

To install the Image Enhance CutouPro Android application, follow these steps:

1. Clone the repository:

```bash
git clone https://github.com/adnanamin69/Image_enhance_cutoupro.git
```

2. Open the project in Android Studio.

3. In the `pkg/network/ApiService.kt` file, replace the placeholder API key with your own CutouPro API key:

```kotlin
private const val API_KEY = "YOUR_API_KEY_HERE"
```

4. Build and run the application on an Android device or emulator.

## Usage

Once the Image Enhance CutouPro app is installed and running on your Android device, follow these steps to enhance an image:

1. Launch the app and navigate to the home screen.

2. Tap on the "Select Image" button to choose an image from your device's gallery.

3. After selecting an image, tap on the "Enhance Image" button.

4. The app will send a request to the CutouPro API to enhance the image. Wait for the response.

5. Once the enhancement is complete, the enhanced image will be displayed on the screen.

## Dependencies

The Image Enhance CutouPro Android application relies on the following dependencies:

- Retrofit 2: A type-safe HTTP client for Android and Java.
- Hilt: A dependency injection library for Android.

Please refer to the `build.gradle` file for the specific versions of these dependencies used in the project.

## Contributing

Contributions to the Image Enhance CutouPro Android application are welcome! If you would like to contribute, please follow the guidelines outlined in [CONTRIBUTING.md](CONTRIBUTING.md).

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

If you have any questions, suggestions, or concerns, please open an issue on the [GitHub repository](https://github.com/adnanamin69/Image_enhance_cutoupro) or contact the project maintainer directly at [your-email@example.com](mailto:your-email@example.com).
