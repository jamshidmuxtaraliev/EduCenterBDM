package uz.bdmgroup.ilmizlab.model

import java.io.Serializable

data class RegionSearchModel(
    val id:Int,
    val name_uz:String,
    val districts:List<DistrictSearchModel>,
    var checked:Boolean = false
):Serializable
