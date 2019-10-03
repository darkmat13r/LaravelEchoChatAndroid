package com.darkmat13r.samplechatdemo.retrofit

import `in`.darkmatter.samplechatdemo.BuildConfig
import android.content.Context
import com.darkmat13r.samplechatdemo.MyApplication
import com.darkmat13r.samplechatdemo.data.User

import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalStateException

object RetrofitClient {


    private var mRetrofit: Retrofit? = null
    private var mLastToken: String? = null
    private var mCache: Cache? = null
    private val TAG = RetrofitClient::class.java.simpleName

    fun init(context: Context) {
        mCache?.let {
            throw IllegalStateException("Retrofit cache is already initialized")
        }
        mCache = Cache(context.cacheDir, 20 * 1024 * 1024)
    }

    fun <T> createService(serviceClass: Class<T>, user: User?): T {
        var currentToken: String? = user?.accessToken


        mLastToken = currentToken
        val httpClientBuilder = OkHttpClient.Builder()
        //Add Interceptor
        val httpLogging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            httpLogging.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addInterceptor(httpLogging)

        httpClientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer $currentToken")
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        httpClientBuilder.addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
        httpClientBuilder.addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
        httpClientBuilder.cache(mCache)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(ApiConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        mRetrofit = retrofitBuilder.client(httpClientBuilder.build()).build()

        return mRetrofit?.create(serviceClass)!!
    }
    private val REWRITE_RESPONSE_INTERCEPTOR = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")
        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + 3)
                    .build()
        } else {
            originalResponse
        }
    }

    private val REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = Interceptor { chain ->
        var request = chain.request()
        if (!MyApplication.getInstance().hasNetwork()) {
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                    .build()
        }
        chain.proceed(request)
    }
}