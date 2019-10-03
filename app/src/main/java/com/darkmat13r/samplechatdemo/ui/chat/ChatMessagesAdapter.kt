package com.darkmat13r.samplechatdemo.ui.chat

import `in`.darkmatter.samplechatdemo.databinding.ItemReceivedMessageBinding
import `in`.darkmatter.samplechatdemo.databinding.ItemSentMessageBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.darkmat13r.samplechatdemo.data.Message
import java.util.*

class ChatMessagesAdapter(private val lifecycleOwner: LifecycleOwner)  : RecyclerView.Adapter<ChatMessagesAdapter.ViewHolder>(){
    companion object{
        const val VIEW_TYPE_SENT = 0
        const val VIEW_TYPE_RECEIVED  =  1
    }

    private val DiffCallback = object: DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id  == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return  oldItem.id == newItem.id
        }

    }
    var messages: List<Message> = Collections.emptyList()
        set(value) {
            field = value
            differ.submitList(value)
        }

    private val differ = AsyncListDiffer<Message>(this, DiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if(viewType == VIEW_TYPE_RECEIVED){
            val binding = ItemReceivedMessageBinding.inflate(inflater, parent, false)
            binding.lifecycleOwner  =  lifecycleOwner
            return ReceivedMessageViewHolder(binding)
        }
        val binding = ItemSentMessageBinding.inflate(inflater, parent, false)
        binding.lifecycleOwner  =  lifecycleOwner
        return SentMessageViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val item = differ.currentList[position]
        if(item.toId == 1){
            return VIEW_TYPE_RECEIVED
        }
        return VIEW_TYPE_SENT
    }



    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item =  differ.currentList[position]
        if(getItemViewType(position) ==  VIEW_TYPE_RECEIVED){
            (holder as ReceivedMessageViewHolder).bind.apply {
                message = item
            }
        }else{
           (holder as SentMessageViewHolder).bind.apply {
                message = item
            }
        }
    }


    class ReceivedMessageViewHolder(binding:ItemReceivedMessageBinding)  : ViewHolder(binding.root){
        val bind =  binding
    }
    class SentMessageViewHolder(binding:ItemSentMessageBinding)  : ViewHolder(binding.root){
        val bind =  binding
    }

    abstract class ViewHolder (view:View):RecyclerView.ViewHolder(view){

    }

}