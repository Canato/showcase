package com.can_apps.message_data_source

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

private const val MESSAGE_TABLE = "message_table"
private const val MESSAGE_ID = "message_id"
private const val MESSAGE_TEXT = "message_text"
private const val MESSAGE_TIMESTAMP = "message_timestamp"
private const val MESSAGE_HOLDER = "message_holder"
private const val MESSAGE_TAIL = "message_tail"

@Dao
internal interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(entity: MessageEntity): Long

    @Query("SELECT * FROM $MESSAGE_TABLE ORDER BY $MESSAGE_ID DESC")
    fun getAllMessages(): List<MessageEntity>

    @Query("SELECT * FROM $MESSAGE_TABLE ORDER BY $MESSAGE_ID DESC LIMIT 1")
    fun getLatestValue(): Flow<MessageEntity>
}

@Entity(
    tableName = MESSAGE_TABLE,
    indices = [Index(MESSAGE_ID, name = "newsIdIndex")]
)
internal data class MessageEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MESSAGE_ID)
    val id: Long,

    @ColumnInfo(name = MESSAGE_TEXT)
    val text: String,

    @ColumnInfo(name = MESSAGE_TIMESTAMP)
    val timestamp: Long,

    @ColumnInfo(name = MESSAGE_HOLDER)
    val holder: String,

    // SQLite don't have boolean type. 0 = false, 1 = true
    @ColumnInfo(name = MESSAGE_TAIL)
    val hasTail: Int
)
