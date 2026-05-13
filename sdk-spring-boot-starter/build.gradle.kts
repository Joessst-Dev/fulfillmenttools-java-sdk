plugins {
    id("java-library")
    id("maven-publish")
    id("signing")
    id("com.gradleup.nmcp") version "0.0.8"
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

tasks.javadoc {
    (options as StandardJavadocDocletOptions).apply {
        addStringOption("Xdoclint:all,-missing", "-quiet")
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "fulfillmenttools-java-sdk-springboot-starter"
            from(components["java"])
            pom {
                name = "fulfillmenttools Spring Boot Starter"
                description = "Spring Boot auto-configuration for the fulfillmenttools Java SDK"
                url = "https://github.com/Joessst-Dev/fulfillmenttools-java-sdk"
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
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

// Sign all publications when the signing key is present.
signing {
    val signingKey = System.getenv("SIGNING_KEY")
    val signingPassword = System.getenv("SIGNING_PASSWORD")
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications["mavenJava"])
    }
}

// Publishes to Sonatype Central Portal via bundle upload API.
nmcp {
    publish("mavenJava") {
        username = System.getenv("SONATYPE_USERNAME") ?: ""
        password = System.getenv("SONATYPE_TOKEN") ?: ""
        publicationType = "USER_MANAGED"
    }
}
