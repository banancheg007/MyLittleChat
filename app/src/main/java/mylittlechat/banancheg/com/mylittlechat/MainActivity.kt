package mylittlechat.banancheg.com.mylittlechat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.message_user_one.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private val myAdapter: MyAdapter = MyAdapter();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = myAdapter
        recyclerView.addItemDecoration(MyItemDecorator())
        buttonOk.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.buttonOk ->{
                if(radioGroup.checkedRadioButtonId == R.id.radioBtnUserOne){
                    val userMessage = UserOneMessage(editText.text.toString())
                    myAdapter.addView(userMessage)
                }else if(radioGroup.checkedRadioButtonId == R.id.radioBtnUserTwo){
                    val userMessage = UserTwoMessage(editText.text.toString())
                    myAdapter.addView(userMessage)
                }
            }
        }
    }


}
