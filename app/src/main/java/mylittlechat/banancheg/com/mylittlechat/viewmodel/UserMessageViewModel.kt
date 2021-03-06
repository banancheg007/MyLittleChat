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
import androidx.lifecycle.LiveData
import mylittlechat.banancheg.com.mylittlechat.MyAdapter
import mylittlechat.banancheg.com.mylittlechat.R
import mylittlechat.banancheg.com.mylittlechat.UserMessage

import mylittlechat.banancheg.com.mylittlechat.repository.UserMessageRepository
import android.view.View.OnFocusChangeListener





class UserMessageViewModel(application: Application) : BaseViewModel(application), MyAdapter.OnMessageClickListener {

    private val repository: UserMessageRepository = UserMessageRepository(application)
    private var allMessages: LiveData<List<UserMessage>>
    val myAdapter: MyAdapter by lazy { MyAdapter(this) }
    private lateinit var mTextView: TextView
    private lateinit var mEditText: EditText


    lateinit var context: Context

    init {
        allMessages = repository.getAllMessages()
        context = application.baseContext
    }

    override fun onMessageClicked(message: UserMessage, view: View) {
        view.requestFocus()
        showPopupMenu(view, message)
    }

    private fun showPopupMenu(view: View, message: UserMessage) {
        val popupMenu = PopupMenu(getApplication(), view)
        popupMenu.inflate(R.menu.popupmenu)
        popupMenu
            .setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.delete -> {
                           repository.delete(message)
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
                            mEditText.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                                if (!hasFocus) {
                                    endEditMessage(mTextView, mEditText, message)
                                }
                            }
                            mEditText.setOnEditorActionListener { v, actionId, event ->
                                return@setOnEditorActionListener when (actionId) {
                                    EditorInfo.IME_ACTION_DONE -> {
                                       endEditMessage(mTextView, mEditText, message)
                                        true
                                    }
                                    else -> false
                                }

                            }
                            if (!mEditText.hasFocus()){
                                endEditMessage(mTextView, mEditText, message)
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




    fun getAllMessages(): LiveData<List<UserMessage>> {
        return allMessages
    }

    fun endEditMessage(mTextView: TextView, mEditText: EditText,message: UserMessage){
        var text: String = mTextView.text.toString()
        text=mEditText.text.toString()
        mTextView.text=mEditText.text
        mEditText.visibility=View.GONE
        mTextView.visibility = View.VISIBLE
        mEditText.setText("")
        val upd_message = UserMessage(message.userId, text)
        upd_message.id= message.id
        repository.update(upd_message)
        closeKeyboard()
    }

    fun addNewMessage(message: String, userId: Int){
        if(message.isEmpty()){
            Toast.makeText(context, "Введите сообщение", Toast.LENGTH_LONG).show()
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
            repository.insert(userMessage)
        }
    }

    fun updateMessageCount() {
        var messagesUser1 = 0
        var messagesUser2 = 0
        myAdapter.messagesList.forEach { i ->
            when (i.userId) {
                1 -> messagesUser1++
                2-> messagesUser2++
            }
        }
        myAdapter.userOneCount = messagesUser1
        myAdapter.userTwoCount = messagesUser2
    }
}