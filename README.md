[![Build Status](https://travis-ci.org/robertogds/TxViewer.svg?branch=master)](https://travis-ci.org/robertogds/TxViewer) 

# TxViewer

Simple example to demo a clean architecture based in MVVM using Blockchain multiaddress API endpoint. 


Tools used on the  project
------------------------------------
* [Kotlin][6]
* [RxJava and RxAndroid][2]
* [Dagger 2][3]
* [Retrofit][4]
* [OkHttp][5]
* [Material Components: Collapsing Toolbars][8]
* [Espresso UI Tests][10]
* [Unit tests - Mockito][11]
* [Blockchain Data API][7]
* [MVVM Architecture][1]
* [Clean Architecture][9]




[1]: https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel
[2]: https://github.com/ReactiveX/RxAndroid
[3]: https://github.com/google/dagger
[4]: https://github.com/square/retrofit
[5]: https://github.com/square/okhttp
[6]: https://kotlinlang.org/
[7]: https://blockchain.info/api/blockchain_api
[8]: https://material.io/components/android/catalog/collapsing-toolbar-layout/
[9]: https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html
[10]: https://developer.android.com/training/testing/espresso/index.html
[11]: http://site.mockito.org/

## Getting started

Install Android Studio: https://developer.android.com/sdk/index.html

Import as Android Studio project.

Build -> Make Project

If there are build errors, in Android Studio go to Tools -> Android -> SDK Manager and install any available updates.

## Gradle

Here are some useful Gradle/adb commands for executing this example:

 * `./gradlew clean build` - Build the entire example and execute lint check.
 * `./gradlew installDebug` - Install the debug apk on the current connected device.
 * `./gradlew test` - Run unit tests (Report: ./app/build/reports/tests)
 * `./gradlew connectedAndroidTest` - Installs and rund instrumentation tests

### Todo

Clean Lint errors: i.e. remove unused colors and styles.

Try new Android Architecture Components 
https://developer.android.com/topic/libraries/architecture/index.html
