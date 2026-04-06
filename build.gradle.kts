import dev.slne.surf.api.gradle.util.registerRequired

plugins {
    id("dev.slne.surf.api.gradle.paper-plugin") version "+"
}

group = "dev.slne.spawn.trader"
version = findProperty("version") as String

dependencies {
    compileOnly("dev.slne.surf.npc:surf-npc-api:+")
    compileOnly("dev.slne.surf.transaction:surf-transaction-api:+")
}

surfPaperPluginApi {
    mainClass("dev.slne.spawn.trader.PaperMain")
    generateLibraryLoader(false)
    foliaSupported(true)

    authors.add("red")

    serverDependencies {
        registerRequired("surf-npc-paper")
        registerRequired("surf-transaction-paper")
    }
}
