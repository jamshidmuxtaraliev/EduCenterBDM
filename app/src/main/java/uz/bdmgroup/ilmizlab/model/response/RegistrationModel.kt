package uz.bdmgroup.ilmizlab.model.response

import java.io.Serializable

data class RegistrationModel(
    val token: String,
    val phone: String,
    val password: String,
    val sms_code: String,
    val fullname: String,
    val status:String
):Serializable
