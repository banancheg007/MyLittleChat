package mylittlechat.banancheg.com.mylittlechat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {


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

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonOk -> {
                Log.d(TAG, "on ok clicked")
                val editTextMes = editText.text.toString()
                val checkedId = radioGroup.checkedRadioButtonId
                if (checkedId == R.id.radioBtnUserOne) {
                    val userMessage = UserMessage("first", editTextMes)

                    myAdapter.addUserMessage(userMessage)

                }
                if (checkedId == R.id.radioBtnUserTwo) {
                    val userMessage = UserMessage("second", editTextMes)
                    myAdapter.addUserMessage(userMessage)


                }
            }
        }
    }



    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            1 ->{
                Log.d(TAG, "edit")

            }
            2 ->{
                Log.d(TAG, "delete")
                myAdapter.deleteMessage(myAdapter.currentAdapterPosition - 1)
            }
            3 ->{
                this.closeContextMenu()
                Log.d(TAG, "close")
            }
        }
        return true
    }


}
