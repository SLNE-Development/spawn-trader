plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

group = "dev.slne.spawn.trader"
version = "1.21.7-1.2.0-SNAPSHOT"

dependencies {
    implementation("com.github.stefvanschie.inventoryframework:IF:0.10.19")
    compileOnly("dev.slne.surf.npc:surf-npc-api:1.21.7-1.3.1-SNAPSHOT")
}

surfPaperPluginApi {
    mainClass("dev.slne.spawn.trader.BukkitMain")
    generateLibraryLoader(false)

    authors.add("red")
}

tasks.shadowJar {
    relocate(
        "com.github.stefvanschie.inventoryframework",
        "dev.slne.spawn.trader.inventoryframework"
    )
}
