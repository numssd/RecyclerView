package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

data class User(
    val id: Long,
    val name: String,
    val company: String,
    val photo: String,
    var countMessage: Int
)

class UserAdapter(private val listItemClickListener: ListItemClickListener) :
    ListAdapter<User, RecyclerView.ViewHolder>(ListItemCallback()) {

    interface ListItemClickListener {
        fun onItemClick(item: User, position: Int)
    }

    class ListItemCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as ListenItemViewHolder).bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_chat, parent, false)
        return ListenItemViewHolder(view)
    }

    inner class ListenItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.findViewById(R.id.textViewUserName)
        private var company: TextView = itemView.findViewById(R.id.textViewUserCompany)
        private var id: TextView = itemView.findViewById(R.id.textViewUserId)
        private var imageUser: ImageView = itemView.findViewById(R.id.imageViewUserPhoto)
        private var message: TextView = itemView.findViewById(R.id.textViewMessage)


        fun bind(user: User) {
            title.text = user.name
            company.text = user.company
            id.text = user.id.toString()
            message.text = user.countMessage.toString()

            Glide.with(imageUser)
                .load(user.photo)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageUser)



            itemView.setOnClickListener {
                listItemClickListener.onItemClick(user, adapterPosition)
            }
        }
    }
}