package mylittlechat.banancheg.com.mylittlechat


import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import mylittlechat.banancheg.com.mylittlechat.viewmodel.UserMessageViewModel
import mylittlechat.banancheg.com.mylittlechat.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity(), View.OnClickListener,View.OnFocusChangeListener {
    private var userMessageViewModel: UserMessageViewModel? = null
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if(!v!!.hasFocus()){
            editText.setText("")
        }
    }


    var myAdapter: MyAdapter = MyAdapter();

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        messagesView.layoutManager = LinearLayoutManager(this)
        messagesView.adapter = myAdapter
        messagesView.addItemDecoration(MyItemDecorator(20))
        buttonOk.setOnClickListener(this)
        editText.setOnFocusChangeListener(this)
        userMessageViewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(UserMessageViewModel::class.java)

        userMessageViewModel!!.getAllMessages().observe(this, Observer { message ->
            myAdapter.setAdapter(message)
        })
        myAdapter.setOnItenClickListener(object : MyAdapter.OnItemClickListener {
            override fun onItemClick(message: UserMessage, view: View) {
                showPopupMenu(view, message)
            }
        })
    }
    private fun showPopupMenu(view: View, message: UserMessage) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.popupmenu)

        popupMenu
            .setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.delete -> {
                            userMessageViewModel!!.delete(message)
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
                            showKeyboard(mEditText)
                            mEditText.imeOptions = EditorInfo.IME_ACTION_DONE
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
                                        userMessageViewModel!!.update(upd_message)
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

    private fun showKeyboard(mEditText: EditText){
        mEditText.postDelayed({
            val keyboard = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            keyboard!!.showSoftInput(mEditText, 0)
        }, 100)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonOk -> {
                Log.d(TAG, "on ok clicked")
                if(editText.text.isEmpty()){
                    Toast.makeText(this, "Введите сообщение", Toast.LENGTH_LONG).show();
                    return
                }
                val editTextMes = editText.text.toString()
                val checkedId = radioGroup.checkedRadioButtonId
                if (checkedId == R.id.radioBtnUserOne) {
                    val userMessage = UserMessage(0, editTextMes)

                    //myAdapter.addUserMessage(userMessage)
                    userMessageViewModel!!.insert(userMessage)

                }
                if (checkedId == R.id.radioBtnUserTwo) {
                    val userMessage = UserMessage(1, editTextMes)
                    //myAdapter.addUserMessage(userMessage)
                    userMessageViewModel!!.insert(userMessage)

                }
                editText.setText("")
            }
        }
    }




}
