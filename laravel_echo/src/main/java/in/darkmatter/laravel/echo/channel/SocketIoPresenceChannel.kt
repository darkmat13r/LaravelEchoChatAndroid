package `in`.darkmatter.laravel.echo.channel

import `in`.darkmatter.laravel.echo.connector.Connector
import `in`.darkmatter.laravel.echo.listener.EchoListener
import com.github.nkzawa.socketio.client.Socket

class SocketIoPresenceChannel(private val socket: Socket,
                              private val name: String,
                              private val options: Connector.Options) :SocketIoPrivateChannel(socket, name, options),PresenceChannel{

    /**
     * Register a callback to be called anytime the member list changes.
     */
    override fun <T> here(callback: EchoListener<T>): SocketIoPresenceChannel {
        this.on("presence:subscribed", callback)
        return this
    }

    /**
     * Listen for someone joining the channel.
     */
    override fun <T> joining(callback: EchoListener<T>): SocketIoPresenceChannel {
        this.on("presence:joining", callback)
        return this
    }

    /**
     * Listen for someone leaving the channel.
     */
    override fun  <T> leaving(callback: EchoListener<T>): SocketIoPresenceChannel {
        this.on("presence:leaving", callback)
        return this
    }

}