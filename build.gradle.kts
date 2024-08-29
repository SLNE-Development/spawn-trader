plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-library`

    id("com.gradleup.shadow") version "8.3.0"
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

    maven("https://repo.codemc.org/repository/maven-public/")

    maven ("https://maven.citizensnpcs.co/repo") {
        name = "citizens-repo"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly ("dev.jorel:commandapi-bukkit-core:9.5.2")
    compileOnly("org.projectlombok:lombok:1.18.30")
    compileOnly("net.citizensnpcs:citizens-main:2.0.35-SNAPSHOT"){
        exclude( "*", "*")
    }

    annotationProcessor("org.projectlombok:lombok:1.18.30");
    implementation ("com.github.stefvanschie.inventoryframework:IF:0.10.17")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")

//    compileOnly("dev.slne:surf-transaction-api:1.21+")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }

    withSourcesJar()
    withJavadocJar()
}

tasks.processResources {
    val props = mapOf("version" to version)

    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.shadowJar {
    relocate("com.github.stefvanschie.inventoryframework", "dev.slne.spawn.trader.inventoryframework")
    relocate("com.github.ben-manes.caffeine", "dev.slne.spawn.trader.caffeine")
}

tasks.build {
    dependsOn("shadowJar")
}
