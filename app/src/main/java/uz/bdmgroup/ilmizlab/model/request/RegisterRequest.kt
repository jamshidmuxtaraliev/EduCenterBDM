package uz.bdmgroup.ilmizlab.model.request

data class RegisterRequest(
    val token:String,
    val fullname: String,
    val phone: String,
    val sms_code:String
)
