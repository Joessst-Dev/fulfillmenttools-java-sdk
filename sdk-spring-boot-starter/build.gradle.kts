plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":sdk-core"))
    implementation("org.springframework.boot:spring-boot-autoconfigure:3.3.2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.3.2")
    testImplementation("org.springframework.boot:spring-boot-test:3.3.2")
    testImplementation("org.springframework.boot:spring-boot-test-autoconfigure:3.3.2")
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.26.3")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name = "fulfillmenttools Spring Boot Starter"
                description = "Spring Boot auto-configuration for the fulfillmenttools Java SDK"
                url = "https://github.com/joesst-dev/fulfillmenttools-java-sdk"
                licenses {
                    license {
                        name = "MIT License"
                        url = "https://opensource.org/licenses/MIT"
                    }
                }
                developers {
                    developer {
                        id = "jost.weyers"
                        name = "Jost Weyers"
                        email = "jostweyers@gmail.com"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/Joessst-Dev/fulfillmenttools-java-sdk.git"
                    developerConnection = "scm:git:ssh://github.com/Joessst-Dev/fulfillmenttools-java-sdk.git"
                    url = "https://github.com/Joessst-Dev/fulfillmenttools-java-sdk"
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/${System.getenv("GITHUB_REPOSITORY") ?: "owner/fulfillmenttools-java-sdk"}")
            credentials {
                // GITHUB_ACTOR and GITHUB_TOKEN are injected by the CI workflow.
                // For local runs these will be absent and `publish` will fail intentionally
                // — snapshot publishing is CI-only.
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
        maven {
            name = "MavenCentral"
            // Sonatype Central Portal deployment endpoint
            url = uri(
                if (version.toString().endsWith("SNAPSHOT"))
                    "https://central.sonatype.com/repository/maven-snapshots/"
                else
                    "https://central.sonatype.com/api/v1/publisher/upload"
            )
            credentials {
                // SONATYPE_USERNAME and SONATYPE_TOKEN are stored as GitHub Actions secrets.
                username = System.getenv("SONATYPE_USERNAME")
                password = System.getenv("SONATYPE_TOKEN")
            }
        }
    }
}

// Sign all publications when the signing key is present.
// In CI the key is injected via SIGNING_KEY (armored private key) and SIGNING_PASSWORD.
// Locally, signing is skipped unless gradle.properties supplies these values.
signing {
    val signingKey = System.getenv("SIGNING_KEY")
    val signingPassword = System.getenv("SIGNING_PASSWORD")
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications["mavenJava"])
    }
}
