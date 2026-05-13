group = "io.github.joessst-dev"
version = System.getenv("RELEASE_VERSION") ?: "0.1.0-SNAPSHOT"

subprojects {
    group = rootProject.group.toString()
    version = rootProject.version
}
