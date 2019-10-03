package com.darkmat13r.samplechatdemo.retrofit.services

import com.darkmat13r.samplechatdemo.data.ApiResponse
import com.darkmat13r.samplechatdemo.data.Message
import com.darkmat13r.samplechatdemo.data.Page
import io.reactivex.Observable
import retrofit2.http.*

interface ChatService {


    @GET("chats/{userId}")
    fun getMessages(@Path("userId") userId:Int, @Query("paginate") paginate:Boolean):Observable<ApiResponse<Page<Message>>>


    @POST("chats/{userId}")
    fun sendMessage(@Path("userId") userId :Int, @Body message: Message?):Observable<ApiResponse<Message>>
}