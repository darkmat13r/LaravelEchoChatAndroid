package `in`.darkmatter.laravel.echo.channel

import `in`.darkmatter.laravel.echo.connector.Connector
import com.github.nkzawa.socketio.client.Socket

open class SocketIoPrivateChannel(
    private val socket: Socket,
    private val name: String,
    private val options: Connector.Options
) : SocketIOChannel(socket, name, options) {

    fun whisper(eventName: String, data: Any): SocketIOChannel {
        options.event = eventName
        this.socket.emit("client event", options)
        return this
    }

}