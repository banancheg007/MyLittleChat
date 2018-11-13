package mylittlechat.banancheg.com.mylittlechat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.Manifest.permission_group.SMS
import android.telecom.Call


class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>(){
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layout: Int = getItemLayout(viewType)
        val view: View = LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if(position == TYPE_HEADER){
            viewHolder.bindHeader(firstUserMessageCounter, secondUserMessageCounter)
        }else{
            val message = messagesList.get(position).message
            viewHolder.bind(message)
        }
    }

     private var firstUserMessageCounter: Int =0
     private var secondUserMessageCounter: Int = 0

    val TYPE_HEADER = 0
    val TYPE_FIRST_USER_MESSAGE = 1
    val TYPE_SECOND_USER_MESSAGE = 2

    var messagesList: ArrayList<UserMessage> = ArrayList()


    fun getItemLayout(viewType: Int): Int {
        when (viewType) {
            TYPE_HEADER -> return R.layout.header
            TYPE_FIRST_USER_MESSAGE -> return R.layout.message_user_one
            TYPE_SECOND_USER_MESSAGE -> return R.layout.message_user_two
        }
        return R.layout.message_user_one
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0){
            return TYPE_HEADER
        }
        else if (messagesList.get(position) is UserOneMessage) {
            return TYPE_FIRST_USER_MESSAGE
        } else if (messagesList.get(position) is UserTwoMessage) {
            return TYPE_SECOND_USER_MESSAGE
        }
        return -1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(message: String?) {
            itemView.findViewById<TextView>(R.id.messageTextView).text = message
        }

        fun bindHeader(firstUserMessageCounter: Int, secondUserMessageCounter: Int) {
            itemView.findViewById<TextView>(R.id.count_user_one).text = firstUserMessageCounter.toString()
            itemView.findViewById<TextView>(R.id.count_user_two).text = secondUserMessageCounter.toString()
        }
    }




}