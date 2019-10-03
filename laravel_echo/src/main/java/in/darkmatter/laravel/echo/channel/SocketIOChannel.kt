package `in`.darkmatter.laravel.echo.channel

import `in`.darkmatter.laravel.echo.connector.Connector
import `in`.darkmatter.laravel.echo.constants.Event
import `in`.darkmatter.laravel.echo.listener.EchoListener
import `in`.darkmatter.laravel.echo.util.EventFormatter
import android.util.Log
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONObject


open class SocketIOChannel(
    private val socket: Socket,
    private val name: String,
    private val options: Connector.Options
) : Channel() {

    companion object {
        private val TAG = SocketIOChannel::class.java.simpleName
    }

    private var eventFormatter: EventFormatter = EventFormatter(options.namespace)
    var events = hashMapOf<String, Emitter.Listener>()

    init {
        this.subscribe()
        this.configureReconnector()
    }


    /**
     * Subscribe to a Socket.io channel
     */
    fun subscribe() {
        val options = JSONObject()
        try {
            options.put(EchoParam.CHANNEL, name)
            options.put(EchoParam.AUTH, this.options.auth)
            socket.emit(Event.SUBSCRIBE, options)
        } catch (e: Exception) {
            throw Exception("Cannot subscribe to channel '" + name + "' : " + e.message)
        }

    }

    /**
     * Unsubscribe to a Socket.io channel and unbind event callback
     */
    fun unsubscribe() {
        unbind()
        this.socket.disconnect()
        val options = JSONObject()
        try {
            options.put(EchoParam.CHANNEL, name)
            options.put(EchoParam.AUTH, this.options.auth)
            this.socket.emit(Event.UNSUBSCRIBE, options)
        } catch (e: Exception) {
            throw Exception("Cannot unsubscribe to channel '" + name + "' : " + e.message)
        }
    }

    /**
     * Listen for an event on the channel instance
     */
    override fun <T> listen(event: String, callback: EchoListener<T>): SocketIOChannel {
        this.on(event, callback)
        return this
    }

    /**
     * Stop listening for an event on the channel instance
     */
    override fun stopListening(event: String): SocketIOChannel {
        val name = this.eventFormatter.format(event)
        this.socket.off(name)
        this.events.remove(name)
        return this
    }

    /**
     * Bind the channel's socket to an event and store the callback
     */
    fun <T> on(event: String, callback: EchoListener<T>) {
        val listener = Emitter.Listener {
            Log.e(TAG, "Data ${it[1]}")
            if (it.size > 1)
                callback.submitData(it[1])
        }
        this.socket.on(event, listener)
        this.bind(event, listener)
    }

    /**
     * Attach a reconnect listener and bind the event
     */
    fun configureReconnector() {
        val listener = Emitter.Listener {
            this.subscribe()
        }
        this.socket.on(Event.RECONNECT, listener)
        this.bind(Event.RECONNECT, listener)
    }

    /**
     * Unbind the channel's socket from all stored event callback
     */
    fun unbind() {
        events.forEach { (event, listener) ->
            this.socket.off(event, listener)
            events.remove(event)
        }
    }

    /**
     * Bind the channel's  socket to an event and store the callback
     */
    fun bind(event: String, callback: Emitter.Listener) {
        this.events[event] = callback
    }


}