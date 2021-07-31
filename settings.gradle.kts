
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

plugins {
    id("de.fayard.refreshVersions") version "0.10.1"
}
