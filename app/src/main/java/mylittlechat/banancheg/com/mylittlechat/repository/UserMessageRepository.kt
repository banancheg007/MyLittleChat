package mylittlechat.banancheg.com.mylittlechat.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import mylittlechat.banancheg.com.mylittlechat.UserMessage
import mylittlechat.banancheg.com.mylittlechat.dao.UserMessageDao
import mylittlechat.banancheg.com.mylittlechat.database.ChatDatabase

class UserMessageRepository(application: Application){
    private val messageDao: UserMessageDao
    private val allMessages: LiveData<List<UserMessage>>

    init {
        val database = ChatDatabase.getInstance(application)
        messageDao = database.userMessageDao()
        allMessages = messageDao.allMessages
    }

    fun insert(message: UserMessage) {
        InsertMesageAsyncTask(messageDao).execute(message)
    }

    fun update(message: UserMessage) {
        UpdateMesageAsyncTask(messageDao).execute(message)
    }

    fun delete(message: UserMessage) {
        DeleteMesageAsyncTask(messageDao).execute(message)
    }

    fun getAllMessages(): LiveData<List<UserMessage>> {
        return allMessages
    }

    private class InsertMesageAsyncTask internal constructor(private val noteDao: UserMessageDao) : AsyncTask<UserMessage, Void, Void>() {

        override fun doInBackground(vararg messages: UserMessage): Void? {
            noteDao.insert(messages[0])
            return null
        }
    }

    private class UpdateMesageAsyncTask internal constructor(private val noteDao: UserMessageDao) : AsyncTask<UserMessage, Void, Void>() {

        override fun doInBackground(vararg messages: UserMessage): Void? {
            noteDao.update(messages[0])
            return null
        }
    }

    private class DeleteMesageAsyncTask internal constructor(private val noteDao: UserMessageDao) : AsyncTask<UserMessage, Void, Void>() {

        override fun doInBackground(vararg messages: UserMessage): Void? {
            noteDao.delete(messages[0])
            return null
        }
    }
}