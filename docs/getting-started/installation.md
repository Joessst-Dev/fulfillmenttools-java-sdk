# Installation

## Requirements

- Java 21 or higher
- Gradle 8+ or Maven 3.8+

## Maven Central

Both artifacts are published to Maven Central. No extra repository configuration is needed.

## Gradle

Add the core SDK to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.github.joessst-dev:fulfillmenttools-java-sdk:0.1.0")
}
```

## Maven

Add to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.joessst-dev</groupId>
    <artifactId>fulfillmenttools-java-sdk</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Spring Boot Starter

If you are using Spring Boot 3.x, use the starter module for automatic configuration:

### Gradle

```kotlin
dependencies {
    implementation("io.github.joessst-dev:fulfillmenttools-java-sdk-springboot-starter:0.1.0")
}
```

### Maven

```xml
<dependency>
    <groupId>io.github.joessst-dev</groupId>
    <artifactId>fulfillmenttools-java-sdk-springboot-starter</artifactId>
    <version>0.1.0</version>
</dependency>
```

The starter provides auto-configuration of the `FulfillmenttoolsClient` bean with properties-based configuration. It also pulls in `fulfillmenttools-java-sdk` transitively, so you do not need to declare both dependencies.

## GitHub Packages (alternative)

The artifacts are also mirrored to GitHub Packages. This requires authentication with a GitHub token:

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

See [GitHub Packages documentation](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry#authenticating-to-github-packages) for authentication setup.
