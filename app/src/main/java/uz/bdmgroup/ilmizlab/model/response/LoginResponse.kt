package uz.bdmgroup.ilmizlab.model.response

data class LoginResponse(
    val token: String,
    val fullname: String,
    var avatar:String,
    val phone: String,
    val status: String
)
