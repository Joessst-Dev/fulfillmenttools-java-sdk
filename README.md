# fulfillmenttools Java SDK

> [!WARNING]
> **Preview — Not Production Ready.** This library is under active development. APIs may change without notice between versions. Use in production at your own risk.

> **Disclaimer:** This is an independent, community-maintained open source project and is **not** officially developed, maintained, or endorsed by fulfillmenttools GmbH or any of its affiliates. Use at your own risk.

A lightweight Java 21 client library for the [fulfillmenttools](https://fulfillmenttools.de) platform API. Typed resource clients, automatic token management, and an optional Spring Boot starter — no framework required.

[![Java](https://img.shields.io/badge/Java-21+-orange)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Docs](https://img.shields.io/badge/docs-GitHub%20Pages-blue)](https://joessst-dev.github.io/fulfillmenttools-java-sdk/)

📖 **[Full documentation →](https://joessst-dev.github.io/fulfillmenttools-java-sdk/)**

## Getting Started

**Gradle (Kotlin DSL):**

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

dependencies {
    implementation("io.github.joessst-dev:fulfillmenttools-java-sdk:0.0.1")
    // Optional: Spring Boot auto-configuration
    implementation("io.github.joessst-dev:fulfillmenttools-java-sdk-springboot-starter:0.0.1")
}
```

**Create a client:**

```java
FulfillmenttoolsClient client = FulfillmenttoolsClient.builder()
    .baseUrl("https://<projectId>.api.fulfillmenttools.com")
    .credentials(new EmailPasswordCredentials(email, password, apiKey))
    .build();
```

**Use a resource client:**

```java
Order order = client.orders().get(new OrderId("ord-123"));

for (Facility f : client.facilities().listAll(FacilityListRequest.builder().build())) {
    System.out.println(f.name());
}
```

For full installation options, configuration, all resource clients, error handling, pagination, and the Spring Boot starter, see the **[documentation site](https://joessst-dev.github.io/fulfillmenttools-java-sdk/)**.

## Building

```bash
./gradlew build   # compile + test
./gradlew test    # tests only
```

## License

MIT — see [LICENSE](LICENSE) for details.

## Issues & Support

Open an issue on [GitHub](https://github.com/Joessst-Dev/fulfillmenttools-java-sdk/issues).
