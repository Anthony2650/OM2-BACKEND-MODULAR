
// Versiones de Ktor y Kotlin
val kotlin_version: String by project
val logback_version: String by project
val ktor_version = "3.2.3"
// Nuevas declaraciones de versiones para Exposed, Hikari y PostgreSQL
val exposed_version = "0.51.0"
val hikari_version = "5.1.0"
val postgresql_driver_version = "42.7.3"

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
    implementation("org.openfolder:kotlin-asyncapi-ktor:3.1.1")
    implementation("io.ktor:ktor-server-cors")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // Módulos de la librería "Exposed":
    implementation("org.jetbrains.exposed:exposed-core:0.51.0")
    implementation("org.jetbrains.exposed:exposed-dao:${exposed_version}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${exposed_version}")
    implementation("org.jetbrains.exposed:exposed-java-time:${exposed_version}")

    // Módulo para manejar tiempo y fechas:
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.51.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.51.0")

    //DATABASE:
    implementation("com.zaxxer:HikariCP:${hikari_version}")
    implementation("org.postgresql:postgresql:${postgresql_driver_version}")

    // Dependencia para JWT Authentication
    implementation("io.ktor:ktor-server-auth-jwt:${ktor_version}")
}
