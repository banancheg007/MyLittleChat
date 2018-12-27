package mylittlechat.banancheg.com.mylittlechat

import android.view.*
import android.widget.EditText
import android.widget.TextView
import java.lang.NullPointerException
import java.util.*
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView


class MyAdapter (): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var listener: OnItemClickListener? = null
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_FIRST_USER = 1
        const val TYPE_SECOND_USER = 2
        const val TYPE_EDIT = 3
    }

    private var messagesList: MutableList<UserMessage> = ArrayList()
    private var editPosition: Int? = null
    interface OnItemClickListener {
        fun onItemClick(message: UserMessage, view: View)
    }
    fun setOnItenClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

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
                return EditViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.edit_message, parent, false))
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
    else when(messagesList[position - 1].userId) {
        0 -> TYPE_FIRST_USER
        1 -> TYPE_SECOND_USER
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
                when (i.userId) {
                    0 -> messagesUser1++
                    1-> messagesUser2++
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
        private val messageText: TextView


        private val onEditMenu = MenuItem.OnMenuItemClickListener { menuItem ->
            val position = adapterPosition

            when (menuItem.itemId) {
                1 -> { startEdit(position-1)}
                    2 -> deleteItem(position-1)
                3 -> {
                }
            }
            true
        }

        init {
            messageText = itemView.findViewById(R.id.messageTextView)

            itemView.setOnCreateContextMenuListener(this)

            view.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(messagesList[position-1], view)
                }

            }
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


        }

    }
    inner class UserOneViewHolder(view: View) : MyAdapter.MyViewHolder(view)
    inner class UserTwoViewHolder(view: View) : MyAdapter.MyViewHolder(view)

    inner class EditViewHolder(view: View) : RecyclerView.ViewHolder(view){




        private val editMessage: EditText = view.findViewById(R.id.edit_message)

        init {
            editMessage.setOnEditorActionListener() { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    v.requestFocus()
                    endEdit(editMessage.text.toString())
                    true
                } else {
                    false
                }

            }
        }




        fun bind(message: UserMessage) {
            editMessage.setText(message.text)
            editMessage.requestFocus()
        }

    }

    fun setAdapter(messages: List<UserMessage>) {
        this.messagesList = messages as ArrayList<UserMessage>
        notifyDataSetChanged()
    }

}