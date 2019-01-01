package mylittlechat.banancheg.com.mylittlechat.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import mylittlechat.banancheg.com.mylittlechat.User
import mylittlechat.banancheg.com.mylittlechat.dao.UserDao
import mylittlechat.banancheg.com.mylittlechat.database.ChatDatabase

class UserRepository(application: Application) {
    private val userDao: UserDao
    private val allUsers: LiveData<List<User>>

    init {
        val database = ChatDatabase.getInstance(application)
        userDao = database.userDao()
        allUsers = userDao.allUsers
    }

    fun insert(user: User) {
        InsertNoteAsyncTask(userDao).execute(user)
    }

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }

    private class InsertNoteAsyncTask internal constructor(private val userDao: UserDao) : AsyncTask<User, Void, Void>() {

        override fun doInBackground(vararg users: User): Void? {
            userDao.insert(users[0])
            return null
        }
    }
}