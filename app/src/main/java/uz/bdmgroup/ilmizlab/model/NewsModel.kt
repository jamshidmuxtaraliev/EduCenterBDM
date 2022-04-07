package uz.bdmgroup.ilmizlab.model

import java.io.Serializable

data class NewsModel(
    val id:Int,
    val center_id:Int,
    val title:String,
    val image:String,
    val content:String,
    val status:String,
    val center_image:String,
    val center_name:String,
    val district_name:String,
    val created_at:String
):Serializable
