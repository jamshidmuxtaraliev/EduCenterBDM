package uz.bdmgroup.ilmizlab.model

import java.io.Serializable

data class GradeModel(
    val id : Int,
    val user_id:Int,
    val rating:Int,
    val comment:String,
    val date:String,
    val user_fullname:String,
    val user_avatar:String
):Serializable
