package com.can_apps.common.wrappers

interface CommonTimestampWrapper {

    val currentTimeStampMillis: Long
}

class CommonTimestampWrapperDefault: CommonTimestampWrapper {

    override val currentTimeStampMillis: Long
        get() = System.currentTimeMillis()
}