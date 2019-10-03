package `in`.darkmatter.laravel.echo.connector

import `in`.darkmatter.laravel.echo.channel.Channel
import `in`.darkmatter.laravel.echo.channel.SocketIOChannel
import `in`.darkmatter.laravel.echo.channel.SocketIoPrivateChannel
import `in`.darkmatter.laravel.echo.constants.Event
import `in`.darkmatter.laravel.echo.listener.EchoListener
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.lang.Exception
import java.net.URISyntaxException
import android.R.attr.name
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject


class SocketioConnector(private val options: Options) : Connector(options) {

    private var socket: Socket? = null

    private val channels = HashMap<String, SocketIOChannel>()
    companion object{
        val TAG = SocketioConnector::class.java.simpleName
    }
    init {
        connect()
    }
    /**
     * Create a fresh Socket.io connection
     */
    override fun connect() {
        try {
            socket = IO.socket(options.host)
            socket?.connect()
            socket?.on(Event.CONNECT_ERROR) {
                Log.e(TAG, "CONNECT_ERROR ${Gson().toJson(it)}")
            }
        } catch (exception: URISyntaxException) {
            exception.printStackTrace()
        }
    }

    /**
     * Listen for an event on a channel instance
     */
    override fun <T> listen(name: String, event: String, callback: EchoListener<T>): SocketIOChannel {
        return channel(name).listen(event, callback)
    }

    /**
     * Get a channel instance by name
     */
    override fun channel(channel: String): SocketIOChannel {
        if(this.socket == null) throw Exception("Client is not connected. ${channel}")
        if(!this.channels.contains(channel)){
            this.channels[channel] = SocketIOChannel(this.socket!!,channel, this.options)
        }
        return this.channels[channel]!!
    }

    /**
     * Get a private channel instance by name.
     */
    override fun privateChannel(channel: String): Channel {
        if(this.socket == null) throw Exception("Client is not connected.")
        if (!this.channels.contains("private-$channel")) {
            this.channels["private-$channel"] =
                SocketIoPrivateChannel(this.socket!!, "private-$channel", this.options)
        }
        return this.channels["private-$channel"]!!
    }

    override fun leave(channel: String) {
        val channels = listOf<String>(channel,"private-$channel", "presence-$channel")
        channels.forEach {
            leaveChannel(it)
        }
    }

    override fun leaveChannel(channel: String) {
        if(channels.contains(channel)){
            this.channels[channel]?.unsubscribe()
            this.channels.remove(channel)
        }
    }

    /**
     * Get the socket ID of the connection
     */
    override fun socketId(): String {
        return this.socket?.id().toString()
    }

    /**
     * Disconnect Socketio connect
     */
    override fun disconnect() {
        this.socket?.disconnect()
    }

}