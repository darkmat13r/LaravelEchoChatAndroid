package com.darkmat13r.samplechatdemo.repository

import com.darkmat13r.samplechatdemo.data.BaseRepository
import com.darkmat13r.samplechatdemo.data.Message
import com.darkmat13r.samplechatdemo.retrofit.RetrofitClient
import com.darkmat13r.samplechatdemo.retrofit.services.ChatService
import com.darkmat13r.samplechatdemo.utils.AccessTokenManager

class ChatRepository : BaseRepository() {
    private val mService =
        RetrofitClient.createService(ChatService::class.java, AccessTokenManager.authUser)

    fun fetchMessages(userId: Int, paginate: Boolean = true) =
        execute(mService.getMessages(userId, paginate))

    fun sendMessageTo(userId:Int, message:Message?) = execute(mService.sendMessage(userId, message))
}