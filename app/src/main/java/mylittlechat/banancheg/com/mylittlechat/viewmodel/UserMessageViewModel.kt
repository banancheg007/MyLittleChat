package mylittlechat.banancheg.com.mylittlechat.viewmodel

import android.app.Application
import android.content.Context
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import kotlinx.android.synthetic.main.activity_main.*
import mylittlechat.banancheg.com.mylittlechat.MyAdapter
import mylittlechat.banancheg.com.mylittlechat.R
import mylittlechat.banancheg.com.mylittlechat.R.id.editText
import mylittlechat.banancheg.com.mylittlechat.UserMessage

import mylittlechat.banancheg.com.mylittlechat.repository.UserMessageRepository



class UserMessageViewModel(application: Application) : BaseViewModel(application) {

    private val repository: UserMessageRepository = UserMessageRepository(application)
    private var allMessages: LiveData<List<UserMessage>>
    var myAdapter: MyAdapter = MyAdapter();


    lateinit var context: Context

    init {
        allMessages = repository.getAllMessages()
        myAdapter.setOnItenClickListener(object : MyAdapter.OnItemClickListener {
            override fun onItemClick(message: UserMessage, view: View) {
                showPopupMenu(view, message)
            }
        })
        context = application.baseContext
    }

    private fun showPopupMenu(view: View, message: UserMessage) {
        val popupMenu = PopupMenu(getApplication(), view)
        popupMenu.inflate(R.menu.popupmenu)

        popupMenu
            .setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.delete -> {
                           delete(message)
                            return true
                        }
                        R.id.edit -> {
                            val mTextView: TextView = view.findViewById(R.id.messageTextView)
                            val mEditText: EditText = view.findViewById(R.id.edit_message)
                            var text: String = mTextView.text.toString()
                            mEditText.setText(text)

                            mTextView.visibility = View.GONE
                            mEditText.visibility = View.VISIBLE
                            mEditText.requestFocus()
                            showKeyboard()
                            mEditText.imeOptions = EditorInfo.IME_ACTION_DONE
                            mEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
                            mEditText.setOnEditorActionListener { v, actionId, event ->
                                return@setOnEditorActionListener when (actionId) {
                                    EditorInfo.IME_ACTION_DONE -> {
                                        text=mEditText.text.toString()
                                        mTextView.text=mEditText.text
                                        mEditText.visibility=View.GONE
                                        mTextView.visibility = View.VISIBLE
                                        mEditText.setText("")
                                        val upd_message = UserMessage(message.userId, text)
                                        upd_message.id= message.id
                                        update(upd_message)
                                        closeKeyboard()
                                        true
                                    }
                                    else -> false
                                }
                            }

                            return true
                        }
                        R.id.close -> {
                            return true
                        }
                        else -> return false
                    }
                }
            })
        popupMenu.setOnDismissListener {
        }
        popupMenu.show()
    }



    fun showKeyboard() {

        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun closeKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
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

    fun addNewMessage(message: String, userId: Int){
        if(message.isEmpty()){
            Toast.makeText(context, "Введите сообщение", Toast.LENGTH_LONG).show();
            return
        }else {
            var userMessage = UserMessage(userId, message)
            when(userId){
                R.id.radioBtnUserOne -> {
                    userMessage.userId = 1
                }
                R.id.radioBtnUserTwo -> {
                    userMessage.userId = 2
                }
            }
            insert(userMessage)
        }
    }
}