package mylittlechat.banancheg.com.mylittlechat


import android.os.Bundle
import android.util.Log
import android.view.View
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
