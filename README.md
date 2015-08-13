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

```xml
<dependency>
    <groupId>com.github.agileinfoways</groupId>
    <artifactId>android-app-rate-alert</artifactId>
    <version>1.0</version>
</dependency>

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
