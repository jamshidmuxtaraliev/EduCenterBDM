package uz.bdmgroup.ilmizlab.model.request

import java.io.Serializable

data class RatingRequest(
    var rating :Float,
    var comment:String,
    var center_id:Int
):Serializable
