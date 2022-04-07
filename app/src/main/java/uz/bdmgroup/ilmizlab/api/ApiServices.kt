package uz.bdmgroup.ilmizlab.api

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.bdmgroup.ilmizlab.App
import uz.bdmgroup.ilmizlab.utils.Constants
import uz.bdmgroup.ilmizlab.utils.Constants.BASE_URL
import uz.bdmgroup.ilmizlab.utils.Constants.KEY
import uz.bdmgroup.ilmizlab.utils.Constants.KEY_API
import uz.bdmgroup.ilmizlab.utils.PrefUtils
import kotlin.jvm.internal.MagicApiIntrinsics

object ApiServices{
    var retrofit:Retrofit?=null
    fun ApiCreator():Api{
        if (retrofit==null){
            val client = OkHttpClient().newBuilder()
                .addInterceptor(
                    ChuckerInterceptor.Builder(App.app)
                        .collector(ChuckerCollector(App.app))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                )
                .addInterceptor {
                    val request =
                        it.request().newBuilder().addHeader(KEY, KEY_API).addHeader("token", PrefUtils.getToken())
                            .build()
                    return@addInterceptor it.proceed(request)
                }
                .build()
            retrofit=Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        }
        return retrofit!!.create(Api::class.java)
    }
}