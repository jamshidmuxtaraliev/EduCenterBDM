package uz.bdmgroup.ilmizlab.model

import java.io.Serializable

data class DistrictSearchModel(
    val id:Int,
    val region_id:Int,
    val name_uz:String,
    var checked:Boolean
):Serializable
