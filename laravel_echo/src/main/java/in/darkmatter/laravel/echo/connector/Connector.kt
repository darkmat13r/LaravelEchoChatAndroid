package `in`.darkmatter.laravel.echo.connector

import `in`.darkmatter.laravel.echo.Config
import `in`.darkmatter.laravel.echo.channel.Channel
import `in`.darkmatter.laravel.echo.channel.SocketIOChannel
import `in`.darkmatter.laravel.echo.listener.EchoListener

abstract class Connector(private var options: Options) {

    fun setOptions(options: Options): Options {
        this.options = options
        return this.options
    }

    abstract fun <T> listen(name: String, event: String, callback: EchoListener<T>): Channel
    /**
     * Create a fresh connection.
     */
    abstract fun connect()

    /**
     * Get a channel instance by name
     */
    abstract fun channel(channel: String): Channel

    /**
     * Get a private channel instance by name
     */
    abstract fun privateChannel(channel: String): Channel

    /**
     * Leave the given channel, as well as its private and presence variants
     */
    abstract fun leave(channel: String)

    /**
     * Leave the given channel
     */
    abstract fun leaveChannel(channel: String)

    /**
     * Get the socket_id  of the connection
     */
    abstract fun socketId(): String

    /**
     * Disconnect from the echo server
     */
    abstract fun disconnect()


    data class Options(
        var event: String? = null,
        var auth: Auth = Auth(),
        var authEndPoint: String = "/broadcasting/auth",
        var broadcaster: Broadcaster = Broadcaster.SOCKET_IO,
        var token: String? = null,
        var host: String? = null,
        var key: String? = null,
        var namespace: String = Config.DEFAULT_NAMESPACE
    )

    data class Auth(
        var headers: HashMap<String, String> = HashMap<String, String>()
    )
}