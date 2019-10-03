package `in`.darkmatter.laravel.echo.channel

import `in`.darkmatter.laravel.echo.listener.EchoListener

abstract class Channel {
    abstract fun <T> listen(event: String, callback: EchoListener<T>): Channel
    abstract fun stopListening(event: String): Channel

}