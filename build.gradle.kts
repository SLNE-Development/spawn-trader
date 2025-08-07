import dev.slne.surf.surfapi.gradle.util.registerRequired

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

group = "dev.slne.spawn.trader"
version = "1.21.7-1.2.0-SNAPSHOT"

dependencies {
    compileOnly("dev.slne.surf.npc:surf-npc-api:1.21.7-1.4.0-SNAPSHOT")
}

surfPaperPluginApi {
    mainClass("dev.slne.spawn.trader.BukkitMain")
    generateLibraryLoader(false)

    authors.add("red")

    serverDependencies {
        registerRequired("surf-npc-bukkit")
    }
}

tasks.shadowJar {
    relocate(
        "com.github.stefvanschie.inventoryframework",
        "dev.slne.spawn.trader.inventoryframework"
    )
}
