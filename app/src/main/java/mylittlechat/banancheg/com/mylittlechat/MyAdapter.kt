package mylittlechat.banancheg.com.mylittlechat

import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.NullPointerException
import java.util.*

class MyAdapter (): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_FIRST_USER = 1
        const val TYPE_SECOND_USER = 2
        const val TYPE_EDIT = 3
    }

    private val messagesList: MutableList<UserMessage> = ArrayList()
    private var editPosition: Int? = null

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
             TYPE_EDIT ->{
                return EditViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.edit_message, parent, false)
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
        else if (position == editPosition?.let { it + 1 }) {
            return TYPE_EDIT
        }
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
            is EditViewHolder -> {
                holder.bind(messagesList[position - 1])
            }

        }
    }

    fun addUserMessage(message: UserMessage) {
        messagesList.add(message)
        notifyItemChanged(0)
        notifyItemInserted(messagesList.size)
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

    fun startEdit(position: Int) {
        val prevEditPosition = editPosition
        editPosition = position
        prevEditPosition?.let { notifyItemChanged(it + 1) }
        notifyItemChanged(position + 1)
    }

    fun endEdit(newText: String) {
        editPosition?.let{
            messagesList[it].text = newText
            notifyItemChanged(it + 1)
        }
        editPosition = null
    }

    open inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        val messageText: TextView


        private val onEditMenu = MenuItem.OnMenuItemClickListener { menuItem ->
            val position = adapterPosition

            when (menuItem.itemId) {
                1 -> {startEdit(position-1)}
                    2 -> deleteItem(position-1)
                3 -> {
                }
            }
            true
        }

        init {
            messageText = itemView.findViewById(R.id.messageTextView)

            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            contextMenu: ContextMenu,
            view: View,
            contextMenuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val Edit = contextMenu.add(Menu.NONE, 1, 1, "Edit")
            val Delete = contextMenu.add(Menu.NONE, 2, 2, "Delete")
            val Close = contextMenu.add(Menu.NONE, 3, 3, "Close")
            Edit.setOnMenuItemClickListener(onEditMenu)
            Delete.setOnMenuItemClickListener(onEditMenu)
            Close.setOnMenuItemClickListener(onEditMenu)
        }

        private fun deleteItem(position: Int) {
            messagesList.removeAt(position)
            notifyItemChanged(0)
            notifyItemRemoved(position+1)
        }







        private val txtMessage: TextView = view.findViewById(R.id.messageTextView)


        fun bind(message: UserMessage) {
            txtMessage.text = message.text

            //txtMessage.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryDark))
        }

    }
    inner class UserOneViewHolder(view: View) : MyAdapter.MyViewHolder(view)
    inner class UserTwoViewHolder(view: View) : MyAdapter.MyViewHolder(view)

    inner class EditViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val editMessage: EditText = view.findViewById(R.id.edit_message)
        private val buttonOk: Button = view.findViewById(R.id.btn_ok)

        init {
            buttonOk.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            endEdit(editMessage.text.toString())
        }

        fun bind(message: UserMessage) {
            editMessage.setText(message.text)
        }

    }

}