plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-library`

    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.hibernate.build.maven-repo-auth") version "3.0.4"
}

group = "dev.slne.spawn"
version = "1.21.1-1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()

    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }

    maven("https://repo.slne.dev/repository/maven-external-developers/") {
        name = "maven-external-developers"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
//    compileOnly("dev.slne:surf-transaction-api:1.21+")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }

    withSourcesJar()
    withJavadocJar()
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
