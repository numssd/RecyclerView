package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.toolbar.*

class ChatActivity : AppCompatActivity() {
    private lateinit var layoutMangerChat: LinearLayoutManager
    private lateinit var chatAdapter: ChatAdapter
    private var isLoadMore = false
    private val firstVisibleItemPosition: Int
        get() = layoutMangerChat.findFirstCompletelyVisibleItemPosition()

    private val dataMessage: ArrayList<String> = ArrayList()

    private lateinit var repositoryMessage: RepositoryMessage


    private var countMessage: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val nameUser = intent.getStringExtra(NAME_KEY)
        val photoUser = intent.getStringExtra(IMAGE_KEY)

        Glide.with(imageViewUser)
            .load(photoUser)
            .circleCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(imageViewUser)

        supportActionBar?.hide()

        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.let {
            it.title = ""
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }
        textViewToolbar.text = nameUser

        repositoryMessage = RepositoryMessage()

        countMessage = intent.getIntExtra(COUNT_MESSAGE_KEY, 0)

        initRecyclerView()
        initChtMessage()
    }

    private fun initRecyclerView() {
        layoutMangerChat = LinearLayoutManager(this)
        layoutMangerChat.stackFromEnd = true
        chatAdapter = ChatAdapter()
        recyclerViewMessage.apply {
            layoutManager = layoutMangerChat
            adapter = chatAdapter
        }
        initRecyclerViewScrollListener()
    }

    private fun initRecyclerViewScrollListener() {
        recyclerViewMessage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0 && firstVisibleItemPosition == 0 && !isLoadMore) {
                    loadNewPageMessage()
                }
            }
        })
    }

    private fun initChtMessage() {
        val newMessage = repositoryMessage.generateMessage()
        for (i in 0..10) {

            dataMessage.add(0, "$i) " + newMessage.random())
        }
        chatAdapter.updateMessage(dataMessage)
    }

    private fun loadNewPageMessage() {
        val newMessage = repositoryMessage.generateMessage()
        if (dataMessage.size <= countMessage) {
            val result = countMessage - 10
            if (!isLoadMore) {
                progressMessageLoader.visibility = View.VISIBLE
                isLoadMore = true

                for (i in dataMessage.size - 1..countMessage) {
                    dataMessage.add(0, "$i) " + newMessage.random())
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    recyclerViewMessage.post {
                        chatAdapter.updateMessage(ArrayList(dataMessage.subList(0, result)))
                    }
                    isLoadMore = false
                    progressMessageLoader.visibility = View.INVISIBLE
                }, 1000)
            }
        } else {
            Toast.makeText(this, getString(R.string.finish_message), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val COUNT_MESSAGE_KEY = "Count"
        const val NAME_KEY = "Name"
        const val IMAGE_KEY = "Photo"
    }
}
