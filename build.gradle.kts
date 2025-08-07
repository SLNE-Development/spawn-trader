import dev.slne.surf.surfapi.gradle.util.registerRequired

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

group = "dev.slne.spawn.trader"
version = findProperty("version")!!

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
