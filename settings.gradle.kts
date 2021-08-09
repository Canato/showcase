import de.fayard.refreshVersions.bootstrapRefreshVersions
import de.fayard.refreshVersions.migrateRefreshVersionsIfNeeded

rootProject.buildFileName = "build.gradle.kts"
rootProject.name = "Showcase"

include(":app", ":common")

include(
    ":features",
    ":features:rank_board",
    ":features:home_list",
    ":features:properties",
    ":features:chat"
)

include(
    ":data_source",
    ":data_source:bad_char_data_source",
    ":data_source:rank_data_source",
    ":data_source:message_data_source",
    ":data_source:properties_data_source"
)

buildscript {
    repositories { gradlePluginPortal() }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.9.7")
////                                                      # available:0.10.0")
////                                                      # available:0.10.1")
////                                                      # available:0.11.0")
}

migrateRefreshVersionsIfNeeded("0.9.7") // Will be automatically removed by refreshVersions when upgraded to the latest version.

bootstrapRefreshVersions()