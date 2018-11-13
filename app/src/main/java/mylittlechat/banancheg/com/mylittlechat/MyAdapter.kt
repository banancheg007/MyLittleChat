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


class MyAdapter (): RecyclerView.Adapter<RecyclerView.ViewHolder>(){



    val TYPE_HEADER = 0
    val TYPE_FIRST_USER_MESSAGE = 1
    val TYPE_SECOND_USER_MESSAGE = 2

    private val messagesList: MutableList<UserMessage> = ArrayList()
    private var firstUserMessageCounter: Int =0
    private var secondUserMessageCounter: Int = 0


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout: Int = getItemLayout(viewType)
        val view: View = LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)
        when(viewType){
            TYPE_HEADER -> return HeaderViewHolder(view)
            TYPE_SECOND_USER_MESSAGE or TYPE_FIRST_USER_MESSAGE -> return MessageViewHolder(view)
            else -> throw IllegalArgumentException()
        }

    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when(viewHolder.itemViewType){
            TYPE_HEADER ->{
                var viewHolder0: HeaderViewHolder = viewHolder  as HeaderViewHolder
                viewHolder0.firstUserMessageCounter.setText(R.string.user_one_count)
                viewHolder0.secondUserMessageCounter.setText(R.string.user_two_count)
            }
            TYPE_SECOND_USER_MESSAGE or TYPE_FIRST_USER_MESSAGE -> {
                var viewHolder0: MessageViewHolder = viewHolder  as MessageViewHolder
                viewHolder0.messageTextView.setText(messagesList.get(position).message)
            }

        }
    }




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

    /*inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(message: String?) {
            itemView.findViewById<TextView>(R.id.messageTextView).text = message
        }

        fun bindHeader(firstUserMessageCounter: Int, secondUserMessageCounter: Int) {
            itemView.findViewById<TextView>(R.id.count_user_one).text = firstUserMessageCounter.toString()
            itemView.findViewById<TextView>(R.id.count_user_two).text = secondUserMessageCounter.toString()
        }
    }*/
    inner class MessageViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var messageTextView: TextView

        init {
            messageTextView = v.findViewById(R.id.messageTextView)
        }
    }

    inner class HeaderViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var firstUserMessageCounter: TextView
        var secondUserMessageCounter: TextView

        init {
            firstUserMessageCounter = v.findViewById(R.id.count_user_one)
            secondUserMessageCounter = v.findViewById(R.id.count_user_two)
        }
    }

    fun addView(message: UserMessage) {
        messagesList.add(message)
        notifyItemChanged(0)
        notifyItemInserted(messagesList.size)
    }




}