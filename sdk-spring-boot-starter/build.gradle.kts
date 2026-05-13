plugins {
    id("java-library")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
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
