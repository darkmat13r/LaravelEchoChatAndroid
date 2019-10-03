package com.darkmat13r.samplechatdemo

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.multidex.MultiDexApplication
import com.darkmat13r.samplechatdemo.data.User
import com.darkmat13r.samplechatdemo.retrofit.RetrofitClient
import com.darkmat13r.samplechatdemo.utils.AccessTokenManager

class MyApplication : MultiDexApplication(){

    companion object {
        private var instance : MyApplication?  = null
        fun getInstance()= instance!!
    }

    override fun onCreate() {
        super.onCreate()
        if(instance  == null){
            instance  =  this
        }

        AccessTokenManager.init(this)
        createDemoUser()
        RetrofitClient.init(this)
    }

    private fun createDemoUser() {
        val user = User()
        user.id  =  1
        user.accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImY1ZjNiZGJhNzBlNmNlMmY2MTMyMmZjZjNmYTEyMmQwYjJmMTcxOWUxYmE2ZTVmMDUzYTJkNDVjM2I4MTk3ZmI3Yzg3N2IxM2U0ZWVlODI0In0.eyJhdWQiOiIxIiwianRpIjoiZjVmM2JkYmE3MGU2Y2UyZjYxMzIyZmNmM2ZhMTIyZDBiMmYxNzE5ZTFiYTZlNWYwNTNhMmQ0NWMzYjgxOTdmYjdjODc3YjEzZTRlZWU4MjQiLCJpYXQiOjE1Njk0OTU2NzAsIm5iZiI6MTU2OTQ5NTY3MCwiZXhwIjoxNjAxMTE4MDcwLCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.HaeYUslqtw6bQWwLvVlNJ-hPTyZZLxMKLIO1sKJAjQrGR_Vi9eb-0xxH_WEmjYknzU5nbP8AMkq6iFGNTb3izPSvOe2ormapFAwGe3Cy9qq9C5Z5A0F3W4DtWyOMnGMOi3rhhO9QCJkiZlKxMRG1-7cmWjo3G81ibUo71HB3Gl8wEkwdapEWElNg4kFMC5uW7fxed3PPDI3SX81M-npqYx-SlfMAC6mCD7MMMFPv-2_z4QzuGB5ChxeLxLIQvNXGVrrd44LfTXBgImpym-MXWUPTJDeOKn3Tg__SYwygoahE5NfR37dfk2Sp7h44l9MzjEbSaDG_ggIzt__yae_y9wKJ8KAZyYyWrZ4NtkamqoB6i-lTaOELP2cUQr5s7bTgqBl6wEoDwzBFvM00kKbndah8RAr_zZu9aX049Iqk7YqGxkQrhp-FfDbfMeXZVTh7jmrQVMyLNNE9R5AhyhNxS4cQh47TaFwHblIOuthnDGgmbo-o0e8kHcRkBHMkFHspGqED1clDC_VvkcE23Zbr9qx5OxoGfLJtwoZws0KCGoq37Mmo-sr-oAcgEB1sq4yW7MTsCpQwU_w87SBkDPzSvrLbAvqsQ_TbVEz7PtGuD3-AcvZrC9xHQERmDTxoMlr4s0ku9SRNrnPL4n12Vi-_NXIK88BU86-y4_vWiwqbYko"
        AccessTokenManager.save(user)
    }

    fun hasNetwork(): Boolean {
        var isConnected = false // Initial Value
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}