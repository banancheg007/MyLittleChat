package mylittlechat.banancheg.com.mylittlechat


import android.os.Bundle
import android.view.View
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userMessageViewModel = ViewModelProviders.of(this, ViewModelFactory(application)).get(UserMessageViewModel::class.java)
        messagesView.layoutManager = LinearLayoutManager(this)
        messagesView.adapter = userMessageViewModel!!.myAdapter
        messagesView.addItemDecoration(MyItemDecorator(20))
        buttonOk.setOnClickListener(this)
        editText.setOnFocusChangeListener(this)

        userMessageViewModel!!.getAllMessages().observe(this, Observer { message ->
            userMessageViewModel!!.myAdapter.setAdapter(message)
            userMessageViewModel!!.updateMessageCount()
        })
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonOk -> {
                userMessageViewModel!!. addNewMessage(editText.text.toString(), radioGroup.checkedRadioButtonId)
                editText.setText("")
                userMessageViewModel!!.closeKeyboard()
            }
        }
    }






}
