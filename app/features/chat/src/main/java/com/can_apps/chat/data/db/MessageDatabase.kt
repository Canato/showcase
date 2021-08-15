package com.can_apps.chat.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.can_apps.common.singleton.CommonSingletonDatabase
import com.can_apps.common.wrappers.CommonTimestampWrapperDefault

@Database(entities = [MessageEntity::class], version = 1)
internal abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao
}

const val MESSAGE_DB_NAME = "message.db"

internal class MessageDatabaseProvider private constructor(context: Context) {

    private var database: MessageDatabase =
        Room.databaseBuilder(context, MessageDatabase::class.java, MESSAGE_DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    companion object : CommonSingletonDatabase<MessageDatabaseProvider, Context>
    (::MessageDatabaseProvider)

    fun getMessageDatabaseHandler(): MessageDatabaseHandler =
        MessageDatabaseHandler(
            dao = database.messageDao(),
            mapper = MessageDaoMapperDefault(),
            timestamp = CommonTimestampWrapperDefault(),
        )
}
