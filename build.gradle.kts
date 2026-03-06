import dev.slne.surf.surfapi.gradle.util.registerRequired

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin") version "1.21.11+"
}

group = "dev.slne.spawn.trader"
version = findProperty("version")!!

dependencies {
    compileOnly("dev.slne.surf.npc:surf-npc-api:1.21.11-1.6.1-SNAPSHOT")
}

surfPaperPluginApi {
    mainClass("dev.slne.spawn.trader.PaperMain")
    generateLibraryLoader(false)

    authors.add("red")

    serverDependencies {
        registerRequired("surf-npc-paper")
    }
}
