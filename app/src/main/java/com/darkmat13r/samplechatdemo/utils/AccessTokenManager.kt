package com.darkmat13r.samplechatdemo.utils

import android.content.Context
import android.content.SharedPreferences
import com.darkmat13r.samplechatdemo.data.User
import com.google.gson.Gson
import org.jetbrains.anko.defaultSharedPreferences

object AccessTokenManager {

    var authUser: User? = null
    private var prefs : SharedPreferences? = null

    private val KEY_USER = "card_user"
    fun init(context: Context?) {
        prefs = context?.defaultSharedPreferences
        val userJson = prefs?.getString(KEY_USER, null)
        userJson?.let {
            val user = Gson().fromJson(it, User::class.java)
            authUser = user
        }
    }

    fun save(user: User) {
        authUser = user
        prefs?.edit()?.putString(KEY_USER, Gson().toJson(user))?.apply()
    }

    fun destroy() {
        authUser = null
        prefs?.edit()?.clear()?.apply()
    }

    fun setVerified(status  : Boolean){
        authUser?.isVerified =  status
        authUser?.let{
            save(it)
        }
    }
}