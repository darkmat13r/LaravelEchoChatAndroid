package com.darkmat13r.samplechatdemo.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import `in`.darkmatter.samplechatdemo.R

import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(toolbar)

    }

}
