rootProject.buildFileName = "build.gradle.kts"
rootProject.name = "Showcase"

include(":app", ":common")

include(
    ":features",
    ":features:rank_board",
    ":features:home_list"
)

include(
    ":data_source",
    ":data_source:bad_char_data_source",
    ":data_source:rank_data_source"
)