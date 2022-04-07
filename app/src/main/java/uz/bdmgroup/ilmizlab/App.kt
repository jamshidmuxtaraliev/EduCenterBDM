package uz.bdmgroup.ilmizlab

import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.orhanobut.hawk.Hawk

class App: MultiDexApplication() {
    companion object {
        lateinit var app: App
    }
    override fun onCreate() {
        super.onCreate()
        app=this

        //multidex uchun
        MultiDex.install(this)

        //havwk keshga saqlovchi kutubxona uchun
        Hawk.init(this).build()
    }
}