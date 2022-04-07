package uz.bdmgroup.ilmizlab.model

import java.io.Serializable

data class DistrictModel(
    val id :Int,
    val region_id:Int,
    val district_name:String
): Serializable
