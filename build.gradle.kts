plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
}

group = "de.joesst.dev"
version = "0.1.0-SNAPSHOT"

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
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2")
    implementation("org.slf4j:slf4j-api:2.0.13")

    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.26.3")
    testImplementation("org.wiremock:wiremock:3.9.1")
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.13")
}

tasks.test {
    useJUnitPlatform()
}

tasks.javadoc {
    exclude("**/internal/**")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name = "fulfillmenttools Java SDK"
                description = "Java client library for the fulfillmenttools platform API"
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
