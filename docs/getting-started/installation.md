# Installation

## Requirements

- Java 21 or higher
- Gradle 8+ or Maven 3.8+

## GitHub Packages Repository

Add the GitHub Packages repository to your build configuration first:

### Gradle

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/Joessst-Dev/fulfillmenttools-java-sdk")
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>github</id>
        <name>GitHub Packages</name>
        <url>https://maven.pkg.github.com/Joessst-Dev/fulfillmenttools-java-sdk</url>
    </repository>
</repositories>
```

See [GitHub Packages documentation](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry#authenticating-to-github-packages) for authentication setup.

## Gradle

Add the SDK to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.github.joessst-dev:fulfillmenttools-java-sdk:0.0.1")
}
```

## Maven

Add to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.joessst-dev</groupId>
    <artifactId>fulfillmenttools-java-sdk</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Spring Boot Starter

If you are using Spring Boot 3.x, you can use the starter module for automatic configuration:

```kotlin
dependencies {
    implementation("io.github.joessst-dev:fulfillmenttools-java-sdk-springboot-starter:0.0.1")
}
```

The starter provides auto-configuration of the `FulfillmenttoolsClient` bean with properties-based configuration.
