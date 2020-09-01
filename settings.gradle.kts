rootProject.buildFileName = "build.gradle.kts"
rootProject.name = "Showcase"

include(":app")

include(
    ":data_source",
    ":data_source:bad_char_data_source"
)
include(":app")

include(
    ":data_source",
    ":data_source:bad_char_data_source",
    ":data_source:rank_data_source"
)