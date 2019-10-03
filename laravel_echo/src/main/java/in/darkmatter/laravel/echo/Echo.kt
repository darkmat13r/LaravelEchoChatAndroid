package `in`.darkmatter.laravel.echo

import `in`.darkmatter.laravel.echo.channel.Channel
import `in`.darkmatter.laravel.echo.connector.Broadcaster
import `in`.darkmatter.laravel.echo.connector.Connector
import `in`.darkmatter.laravel.echo.connector.SocketioConnector
import `in`.darkmatter.laravel.echo.listener.EchoListener
import android.util.Log
import java.lang.Exception


class Echo(private val options: Connector.Options) {

    private var connector: Connector? = null

    companion object{
        private val TAG =  Echo::class.java.simpleName
    }

    init {
        connect()
    }


    fun connect() {
        Log.e(TAG, "Initiating echo")
        if (this.options.broadcaster == Broadcaster.SOCKET_IO) {
            this.connector = SocketioConnector(this.options)
        } else if (this.options.broadcaster == Broadcaster.PUSHER) {
            throw Exception("Not available.Under development")
        }
    }

    /**
     * Disconnect from the echo server
     */
    fun disconnect() {
        this.connector?.disconnect()
    }

    /**
     * Leave the given channelm as well as its private and presence variants
     */
    fun leave(channel: String) {
        this.connector?.leave(channel)
    }

    /**
     * Leave the given channel
     */
    fun leaveChannel(channel: String) {
        this.connector?.leaveChannel(channel)
    }

    /**
     * Listen for an event on a channel instance
     */
    fun <T>  listen(channel: String, event: String, callback: EchoListener<T>): Channel? {
        return this.connector?.listen(channel, event, callback)
    }


    /**
     * Get a public channel instance by name
     */
    fun channel(channel: String):Channel?{
        return this.connector?.channel(channel)
    }
    /**
     * Get a private channel instance by name
     */
    fun private(channel: String): Channel? {
        return this.connector?.privateChannel(channel)
    }

    /**
     * Get the Socket ID for the connection
     */
    fun socketId(): String? {
        return this.connector?.socketId()
    }

}