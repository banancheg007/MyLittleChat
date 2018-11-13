package mylittlechat.banancheg.com.mylittlechat

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.support.v4.app.*
import mylittlechat.banancheg.com.mylittlechat.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.widget.BaseAdapter
import android.widget.ListView
import mylittlechat.banancheg.com.mylittlechat.MyAdapter

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
        //val dividerDrawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.divider_default)
       // messagesView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply { setDrawable(dividerDrawable!!) })
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


}
