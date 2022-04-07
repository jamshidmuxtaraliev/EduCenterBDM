package uz.bdmgroup.ilmizlab.utils

import com.orhanobut.hawk.Hawk
import uz.bdmgroup.ilmizlab.model.LocationModel
import uz.bdmgroup.ilmizlab.model.response.LoginResponse
import uz.bdmgroup.ilmizlab.model.response.RegistrationModel
import uz.bdmgroup.ilmizlab.model.response.UserModel

object PrefUtils {
    const val KEY_IMAGE="key_image"
    const val KEY_TOKEN= "pref_token"
    const val KEY_LOCATION= "pref_location"
    const val KEY_USER= "pref_user"
    const val KEY_SUBSCRIBER= "pref_subscriber"

    fun setToken(value: String){
        Hawk.put(KEY_TOKEN, value)
    }

    fun getToken(): String{
        return Hawk.get(KEY_TOKEN, "")
    }

    fun deleteToken(){
        Hawk.deleteAll()
    }

    fun setLocation(value: LocationModel){
        Hawk.put(KEY_LOCATION,value)
    }

    fun getLocation():LocationModel?{
        return Hawk.get(KEY_LOCATION, )
    }

    fun setUser(value:LoginResponse){
        Hawk.put(KEY_USER, value)
    }

    fun getUser():LoginResponse?{
        return Hawk.get(KEY_USER, )
    }

}