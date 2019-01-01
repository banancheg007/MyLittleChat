package mylittlechat.banancheg.com.mylittlechat

import android.view.*
import android.widget.EditText
import android.widget.TextView
import java.lang.NullPointerException
import java.util.*
import androidx.recyclerview.widget.RecyclerView


class MyAdapter (private val callback: OnMessageClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_FIRST_USER = 1
        const val TYPE_SECOND_USER = 2
    }
    interface OnMessageClickListener {
        fun onMessageClicked(message: UserMessage, view: View)
    }

    var messagesList: MutableList<UserMessage> = ArrayList()
    var userOneCount: Int = 0
    var userTwoCount: Int = 0

    override fun onCreateViewHolder(parent  : ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        when(viewType){
            TYPE_HEADER ->{
                return HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header, parent, false)
                )
            }
            TYPE_FIRST_USER ->{
                return UserMessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_user_one, parent, false)
                )
            }
             TYPE_SECOND_USER ->{
                 return UserMessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_user_two, parent, false)
                 )
             }
            else -> throw NullPointerException()
        }

    }

    override fun getItemCount(): Int {
        return messagesList.size + 1
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {TYPE_HEADER}
    else when(messagesList[position - 1].userId) {
        1 -> TYPE_FIRST_USER
        2 -> TYPE_SECOND_USER
            else -> 5
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HeaderViewHolder -> {
                holder.bind()
            }
            is UserMessageViewHolder -> {
                holder.bind(messagesList[position - 1])
            }
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtMessage: TextView = view.findViewById(R.id.messageTextView)
        fun bind() {
            txtMessage.text = "User1 : $userOneCount   User2 : $userTwoCount"
        }
    }


    open inner class UserMessageViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnLongClickListener{
        private val messageText: TextView



        init {
            messageText = itemView.findViewById(R.id.messageTextView)
            view.setOnLongClickListener(this)
        }


        override fun onLongClick(view: View): Boolean {
            callback.onMessageClicked(messagesList[position-1], view)
            return true
        }


        private val txtMessage: TextView = view.findViewById(R.id.messageTextView)
        private val editMessage: EditText = view.findViewById(R.id.edit_message)

        fun bind(message: UserMessage) {
            txtMessage.text = message.text


        }

    }
    fun setAdapter(messages: List<UserMessage>) {
        this.messagesList = messages as ArrayList<UserMessage>
        notifyDataSetChanged()
    }

}