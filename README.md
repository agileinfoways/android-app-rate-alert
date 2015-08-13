# Android Application Rating Alert

###### an library to show app rating alert with customized alert button label, action and alert interval.

## Gradle
repositories {
    // ...
    maven { url "https://jitpack.io" }
}

dependencies {
        compile 'com.github.agileinfoways:android-app-rate-alert:1.0'
}

## Maven
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
<dependency>
    <groupId>com.github.agileinfoways</groupId>
    <artifactId>android-app-rate-alert</artifactId>
    <version>1.0</version>
</dependency>
```

## Usage
```java
 new AppRateAlert(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Rate our app")
                .setMessage("Rate our app if you love it.")
                .setPositiveButton("Rate", AppRateAlert.Action.RATE_APP)
                .setNeutralButton("Send Feedback", AppRateAlert.Action.SEND_FEEDBACK).setFeedbackMail("xyz@gmail.com")
                .setNegativeButton("Close", AppRateAlert.Action.DISMISS)
                .setAlertInterval(0) // days
                .initialize();
```

## Licence
Copyright 2015 Agileinfoways Pvt. Ltd.
Copyright 2015 The Android Open Source Project for AppRateAlert

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
