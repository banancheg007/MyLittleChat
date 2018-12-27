package mylittlechat.banancheg.com.mylittlechat.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import mylittlechat.banancheg.com.mylittlechat.User
import mylittlechat.banancheg.com.mylittlechat.UserMessage
import mylittlechat.banancheg.com.mylittlechat.dao.UserDao
import mylittlechat.banancheg.com.mylittlechat.dao.UserMessageDao


@Database(entities = [User::class, UserMessage::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun userMessageDao(): UserMessageDao

    companion object {

        private var instance: ChatDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ChatDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    ChatDatabase::class.java, "message_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance as ChatDatabase
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                ChatDatabase.PopulateDbAsyncTask(ChatDatabase.instance!!).execute()
            }
        }
    }

    private class PopulateDbAsyncTask internal constructor(db: ChatDatabase) : AsyncTask<Void, Void, Void>() {

        private val userDao: UserDao = db.userDao()

        override fun doInBackground(vararg voids: Void): Void? {
            userDao.insert(User("User 1"))
            userDao.insert(User("User 2"))
            return null
        }
    }
}