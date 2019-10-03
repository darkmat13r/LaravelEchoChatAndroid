package com.darkmat13r.samplechatdemo.ui.chat

import `in`.darkmatter.laravel.echo.Echo
import `in`.darkmatter.laravel.echo.connector.Broadcaster
import `in`.darkmatter.laravel.echo.connector.Connector
import `in`.darkmatter.laravel.echo.listener.EchoListener
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.darkmatter.samplechatdemo.databinding.FragmentChatBinding
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.darkmat13r.samplechatdemo.data.Message
import com.darkmat13r.samplechatdemo.data.Page
import com.darkmat13r.samplechatdemo.data.Resource
import com.darkmat13r.samplechatdemo.data.Status
import com.darkmat13r.samplechatdemo.retrofit.ApiConstant
import com.darkmat13r.samplechatdemo.utils.AccessTokenManager
import com.google.gson.internal.LinkedTreeMap
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.toast
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class ChatActivityFragment : Fragment() {

    companion object {
        private val TAG = ChatActivityFragment::class.java.simpleName
    }

    private lateinit var binding: FragmentChatBinding
    private lateinit var mViewModel: ChatMessagesViewModel

    private lateinit var mChatMessagesAdapter: ChatMessagesAdapter
    private lateinit var echo: Echo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = ViewModelProvider(this).get(ChatMessagesViewModel::class.java)
        binding = FragmentChatBinding.inflate(inflater, container, false)
        binding.viewModel =mViewModel
        binding.lifecycleOwner = this@ChatActivityFragment

        mChatMessagesAdapter = ChatMessagesAdapter(this@ChatActivityFragment)
        initEcho()
        binding.chatMessages.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
            adapter = mChatMessagesAdapter
        }
        mViewModel.messages.observe(this@ChatActivityFragment, messageObserver)
        mViewModel.messageSent.observe(this@ChatActivityFragment, Observer {
            Log.e(TAG, "ASDA S D  ${it}")
            it.data?.let{
                val list = mChatMessagesAdapter.messages
                val arrayList = arrayListOf<Message>()
                arrayList.add(it)
                arrayList.addAll(list)
                mChatMessagesAdapter.notifyDataSetChanged()
            }

        })

        initListen()
        return binding.root
    }

    private fun initListen() {
        Log.e(TAG, "User ${AccessTokenManager.authUser}")
        echo.private("chat.${1}")
            ?.listen("chat", object:EchoListener<Message>(){
                override fun onDataReceived(data: Message?) {
                    Log.e(TAG, "On data received  ${data}")
                    requireActivity().runOnUiThread {

                    }

                }
            })
        echo.channel("public_channel_name")
            ?.listen("post", object :EchoListener<LinkedTreeMap<String, Any>>(){
                override fun onDataReceived(data: LinkedTreeMap<String, Any>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        mViewModel.setUserId(2)

    }

    private val messageObserver =
        Observer<Resource<Page<Message>>> {
            binding.resource = it
            it?.let {
                if (it.status == Status.SUCCESS) {
                    it.data?.data?.let {
                        mChatMessagesAdapter.messages = it
                        mChatMessagesAdapter.notifyDataSetChanged()
                    }
                }
                if (it.status == Status.ERROR) {
                    toast(it.message.toString())
                }
            }
        }

    private fun initEcho() {
        val options = Connector.Options()
        options.auth.headers["authorization"] = "Bearer ${AccessTokenManager.authUser?.accessToken}"
        options.broadcaster = Broadcaster.SOCKET_IO
        options.host = ApiConstant.SOCKET_URL
        echo = Echo(options)
    }

    override fun onDestroy() {
        super.onDestroy()
        echo.disconnect()
    }
}
