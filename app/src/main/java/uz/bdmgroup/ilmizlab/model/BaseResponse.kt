package uz.bdmgroup.ilmizlab.model

data class BaseResponse<T>(
    val success:Boolean,
    val data:T,
    val message:String,
    val error_code:Int
)

