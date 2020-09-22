package com.can_apps.message_data_source

import android.content.Context
import androidx.room.Room
import com.can_apps.common.singleton.CommonSingletonDatabase

const val MESSAGE_DB_NAME = "message.db"

class MessageDatabaseProvider private constructor(context: Context) {

    private var database: MessageDatabase =
        Room.databaseBuilder(context, MessageDatabase::class.java, MESSAGE_DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    companion object : CommonSingletonDatabase<MessageDatabaseProvider, Context>
        (::MessageDatabaseProvider)

    fun getMessageDatabaseDataSource(): MessageDatabaseDataSource =
        MessageDatabaseDataSourceDefault(database.messageDao(), MessageDaoMapperDefault())
}