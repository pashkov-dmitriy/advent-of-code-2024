plugins {
    kotlin("jvm") version "2.0.20"
}

group = "ru.myproj"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(15)
    jvmToolchain(8)
}