package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UserAdapter.ListItemClickListener {
    private var userChat = mutableListOf<User>()
    private lateinit var userAdapter: UserAdapter
    private var isLoading: Boolean = false
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var repositoryChat: RepositoryChat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repositoryChat = RepositoryChat()

        setupRecyclerView()

        refreshApp()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter(this)
        layoutManager = LinearLayoutManager(this)
        recyclerViewChat.layoutManager = layoutManager
        recyclerViewChat.itemAnimator = DefaultItemAnimator()
        recyclerViewChat.adapter = userAdapter
        loadUserChat(isInitLoad = true)



        recyclerViewChat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == userChat.size - 1) {
                        loadUserChat()
                        isLoading = true
                    }
                }
            }
        })

        userAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                layoutManager.scrollToPositionWithOffset(positionStart, 0)
            }
        })
    }

    fun loadUserChat(isInitLoad: Boolean = false) {
        progressChatLoader.visibility = View.VISIBLE
        if (isInitLoad) {
            userAdapter.submitList(repositoryChat.initChat(userChat))
            progressChatLoader.visibility = View.GONE
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                val newUserChat = ArrayList<User>()
                repositoryChat.generateChat(newUserChat, userChat as ArrayList<User>)
                updateUserChat(newUserChat)
                isLoading = false
                progressChatLoader.visibility = View.GONE
            }, 2000)
        }
    }

    private fun updateUserChat(newList: List<User>) {
        val tempList = userChat.toMutableList()
        tempList.addAll(newList)
        userAdapter.submitList(tempList)
        userChat = tempList
    }


    override fun onItemClick(item: User, position: Int) {
        val intent = Intent(this, ChatActivity::class.java)
        val count = item.countMessage
        val name = item.name
        val photo = item.photo
        intent.putExtra(COUNT_MESSAGE_KEY, count)
        intent.putExtra(NAME_KEY, name)
        intent.putExtra(IMAGE_KEY, photo)
        startActivity(intent)
    }

    private fun refreshApp() {
        swipeToRefresh.setOnRefreshListener {
            Toast.makeText(this, getString(R.string.app_refresh_text), Toast.LENGTH_SHORT).show()
            swipeToRefresh.isRefreshing = false

            loadUserChat()
        }
    }

    companion object{
        const val COUNT_MESSAGE_KEY = "Count"
        const val NAME_KEY = "Name"
        const val IMAGE_KEY = "Photo"
    }
}