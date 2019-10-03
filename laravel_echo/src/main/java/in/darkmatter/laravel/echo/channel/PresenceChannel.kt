package `in`.darkmatter.laravel.echo.channel

import `in`.darkmatter.laravel.echo.listener.EchoListener

interface PresenceChannel {
    fun <T> here(callback: EchoListener<T>):PresenceChannel
    fun <T>  joining(callback:EchoListener<T>):PresenceChannel
    fun <T>  leaving(callback:EchoListener<T>) :PresenceChannel
}