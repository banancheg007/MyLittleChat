package mylittlechat.banancheg.com.mylittlechat.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import mylittlechat.banancheg.com.mylittlechat.UserMessage
import mylittlechat.banancheg.com.mylittlechat.repository.MessageRepository


class UserMessageViewModel(application: Application) : BaseViewModel(application) {

    private val repository: MessageRepository = MessageRepository(application)
    private var allMessages: LiveData<List<UserMessage>>

    init {
        allMessages = repository.getAllMessages()
    }

    fun insert(note: UserMessage) {
        repository.insert(note)
    }

    fun update(note: UserMessage) {
        repository.update(note)
    }

    fun delete(note: UserMessage) {
        repository.delete(note)
    }

    fun getAllMessages(): LiveData<List<UserMessage>> {
        return allMessages
    }
}