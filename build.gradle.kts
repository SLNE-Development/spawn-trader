import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-library`

    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    id("com.gradleup.shadow") version "8.3.0"
    id("org.hibernate.build.maven-repo-auth") version "3.0.4"
    id ("io.freefair.lombok") version "8.10"
}

group = "dev.slne.spawn"
version = "1.21.4-1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()

    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }

    maven("https://repo.codemc.org/repository/maven-public/")

    maven ("https://repo.pyr.lol/snapshots") {
        name = "pyrSnapshots"
    }


}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly ("dev.jorel:commandapi-bukkit-core:9.7.0")
    compileOnly("lol.pyr:znpcsplus-api:2.1.0-SNAPSHOT")

    implementation ("com.github.stefvanschie.inventoryframework:IF:0.10.19")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}

paper {
    name = "spawn-trader"
    main = "dev.slne.spawn.trader.SpawnTrader"
    apiVersion = "1.21"
    authors = listOf("red")
    prefix = "SpawnTrader"
    version = "1.21.4-1.0.0-SNAPSHOT"


    serverDependencies{
        register("CommandAPI") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = true
        }

        register("ZNPCsPlus") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = false
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }

    withSourcesJar()
    withJavadocJar()
}

tasks.compileJava{
    options.encoding = Charsets.UTF_8.name()
    options.compilerArgs.add("-parameters")
}

tasks.javadoc {
    options.encoding = Charsets.UTF_8.name()
}

tasks.shadowJar {
    relocate("com.github.stefvanschie.inventoryframework", "dev.slne.spawn.trader.inventoryframework")
}

tasks.build {
    dependsOn("shadowJar")
}
