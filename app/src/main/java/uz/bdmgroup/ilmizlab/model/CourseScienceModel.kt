package uz.bdmgroup.ilmizlab.model

import java.io.Serializable

data class CourseScienceModel(
    val id:Int,
    val category_id:Int,
    val title:String,
    val icon:String
):Serializable
