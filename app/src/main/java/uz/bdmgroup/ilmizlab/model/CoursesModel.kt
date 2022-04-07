package uz.bdmgroup.ilmizlab.model

import java.io.Serializable

data class CoursesModel(
    val id:Int,
    val center_id:Int,
    val science_id:Int,
    val name:String,
    val monthly_payment:String,
    val science:CourseScienceModel
):Serializable
