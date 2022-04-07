package uz.bdmgroup.ilmizlab.model

import java.io.Serializable

data class SciencesModel(
    val id: Int,
    val category_id: String,
    val title: String,
    val icon: String,
    val created_at: String,
    var checked:Boolean=false
):Serializable
