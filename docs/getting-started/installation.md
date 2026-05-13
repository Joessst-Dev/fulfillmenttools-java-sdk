# Installation

## Requirements

- Java 17 or higher
- Gradle 8+ or Maven 3.8+

## Gradle

Add the SDK to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("de.joesst.dev:sdk-core:0.1.0")
}
```

## Maven

Add to your `pom.xml`:

```xml
<dependency>
    <groupId>de.joesst.dev</groupId>
    <artifactId>sdk-core</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Spring Boot Starter

If you are using Spring Boot 3.x, you can use the starter module for automatic configuration:

```kotlin
dependencies {
    implementation("de.joesst.dev:sdk-spring-boot-starter:0.1.0")
}
```

The starter provides auto-configuration of the `FulfillmenttoolsClient` bean with properties-based configuration.
