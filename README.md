# NoteIt
NoteIt is a simple note taking app showing how to implement the latest recommended DI library, Hilt.
## Dependencies
1. Add the ```hilt-android-gradle-plugin``` plugin to the project's root ```build.gradle``` file:
```bash
buildscript {
    ...
    dependencies {
        ...
        classpath 'com.google.dagger:hilt-android-gradle-plugin:latest_version'
    }
}
```
2. Apply the gradle plugin and add the dependencies below to the ```app/build.gradle``` file:
```bash
...
apply plugin: 'kotlin-kapt'
apply plugin: 'dagger.hilt.android.plugin'

android {
    ...
}

dependencies {
    implementation "com.google.dagger:hilt-android:latest_version"
    kapt "com.google.dagger:hilt-android-compiler:latest_version"
}
```
3. Hilt requires Java 8 features:
```android {
  ...
  compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
  }
}
```
