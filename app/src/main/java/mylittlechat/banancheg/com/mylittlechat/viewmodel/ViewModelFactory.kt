package mylittlechat.banancheg.com.mylittlechat.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(UserMessageViewModel::class.java) -> {
            UserMessageViewModel(application) as T
        }
        else -> throw IllegalArgumentException()
    }
}