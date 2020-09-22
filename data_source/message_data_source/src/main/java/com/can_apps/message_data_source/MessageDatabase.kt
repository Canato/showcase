package com.can_apps.message_data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MessageEntity::class], version = 1)
internal abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao
}
