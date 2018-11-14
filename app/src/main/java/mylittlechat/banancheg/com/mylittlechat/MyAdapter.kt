package mylittlechat.banancheg.com.mylittlechat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.lang.NullPointerException
import java.util.*
import android.view.MenuItem

class MyAdapter (): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_FIRST_USER = 1
        const val TYPE_SECOND_USER = 2
    }
    lateinit var messageTextView : TextView
    lateinit var context: Context
    private val messagesList: MutableList<UserMessage> = ArrayList()
     var currentAdapterPosition: Int = 0

    override fun onCreateViewHolder(parent  : ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        when(viewType){
            TYPE_HEADER ->{
                return HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header, parent, false)
                )
            }
            TYPE_FIRST_USER ->{
                return UserOneViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_user_one, parent, false)
                )
            }
             TYPE_SECOND_USER ->{
                 return UserTwoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_user_two, parent, false)
                 )
             }
            else -> throw NullPointerException()
        }

    }

    override fun getItemCount(): Int {
        return messagesList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER
    else when(messagesList[position - 1].userName) {
        "first" -> TYPE_FIRST_USER
        "second" -> TYPE_SECOND_USER
            else -> 5
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HeaderViewHolder -> {
                holder.bind()
            }
            is UserOneViewHolder -> {
                holder.bind(messagesList[position - 1])
            }
            is UserTwoViewHolder -> {
                holder.bind(messagesList[position - 1])
            }

        }
    }

    fun addUserMessage(message: UserMessage) {
        messagesList.add(message)
        notifyItemChanged(0)
        notifyItemInserted(messagesList.size)
    }

    fun editMessage(position: Int){
       
    }

    fun deleteMessage(position: Int) {
        messagesList.removeAt(position)
        notifyItemChanged(0)
        notifyItemRemoved(position + 1)
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtMessage: TextView = view.findViewById(R.id.messageTextView)
        fun bind() {
            var messagesUser1 = 0
            var messagesUser2 = 0
            messagesList.forEach { i ->
                when (i.userName) {
                    "first" -> messagesUser1++
                    "second" -> messagesUser2++
                }
            }
            txtMessage.text = "User1 : $messagesUser1   User2 : $messagesUser2"
        }
    }

    open inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnCreateContextMenuListener {
        override fun onCreateContextMenu(myMenu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            myMenu!!.setHeaderTitle(R.string.choose_option);
            myMenu.add(0, 1, 0, "edit");
            myMenu.add(0, 2, 0, "delete");
            myMenu.add(0, 3, 0, "close");
            currentAdapterPosition = adapterPosition
            context = txtMessage.context

        }




         val txtMessage: TextView = view.findViewById(R.id.messageTextView)


        fun bind(message: UserMessage) {
            txtMessage.text = message.text
            txtMessage.setOnCreateContextMenuListener(this)
            //txtMessage.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryDark))
        }

    }
    inner class UserOneViewHolder(view: View) : MyAdapter.MyViewHolder(view)
    inner class UserTwoViewHolder(view: View) : MyAdapter.MyViewHolder(view)

}