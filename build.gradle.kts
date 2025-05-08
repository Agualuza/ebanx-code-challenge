import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.3.4")
    implementation("io.ktor:ktor-server-netty:2.3.4")
    implementation("io.ktor:ktor-server-html-builder:2.3.4")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.4")
    implementation("io.ktor:ktor-serialization-jackson:2.3.4")
    implementation("org.slf4j:slf4j-api:2.0.0")
    implementation("org.slf4j:slf4j-simple:2.0.0")
    implementation("io.insert-koin:koin-core:3.5.3")
    implementation("io.insert-koin:koin-ktor:3.5.3")
    implementation("io.insert-koin:koin-logger-slf4j:3.5.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}

tasks.withType<Jar> {
    manifest {
        attributes(
            "Main-Class" to "MainKt"
        )
    }
}

kotlin {
    jvmToolchain(17)
}

tasks.register("stage") {
    dependsOn("fatJar")
}

tasks.jar {
    archiveBaseName.set("ebanx-code-challenge")
    archiveVersion.set("1.0-SNAPSHOT")
}

tasks.register<Jar>("fatJar") {
    archiveBaseName.set("ebanx-code-challenge")
    archiveVersion.set("1.0-SNAPSHOT")

    manifest {
        attributes["Main-Class"] = "MainKt"
    }

    val dependencies = configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }

    from(sourceSets.main.get().output)
    from(dependencies)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}