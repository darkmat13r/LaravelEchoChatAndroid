package com.darkmat13r.samplechatdemo.ui.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.darkmat13r.samplechatdemo.data.Message
import com.darkmat13r.samplechatdemo.data.Page
import com.darkmat13r.samplechatdemo.data.Resource
import com.darkmat13r.samplechatdemo.repository.ChatRepository

class ChatMessagesViewModel :ViewModel(){

    companion object{
        private  val TAG  = ChatMessagesViewModel::class.java.simpleName
    }
    private val mChatRepo  =  ChatRepository()

    val _messageContent =  MutableLiveData<String>()
     val _sendMessageAction = MutableLiveData<Message>()
    val messageSent : LiveData<Resource<Message>> = Transformations.switchMap(_sendMessageAction){
        _messageContent.value =  ""
        mChatRepo.sendMessageTo(_userId.value  ?: -1, it )
    }
    private val _userId=MutableLiveData<Int>()
    val messages :LiveData<Resource<Page<Message>>> =Transformations.switchMap(_userId) {
        mChatRepo.fetchMessages(it)
    }

    init {

    }

    fun setUserId(userId:Int?){
        Log.e(TAG, "UserId :${userId}")
        _userId.value  = userId
    }

    fun onSendMessage(){
        val messageContent = _messageContent.value
        Log.e(TAG,"Text ${!messageContent.isNullOrEmpty()}")
        if(!messageContent.isNullOrEmpty()){
            val message = Message()
            message.content  = messageContent
            _sendMessageAction.value  = message
        }
    }
}