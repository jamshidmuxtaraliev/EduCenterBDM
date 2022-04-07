package uz.bdmgroup.ilmizlab.model

import java.io.Serializable

data class CategoryModel(
    val id: Int,
    val title:String,
    val icon:String,
    val sciences: List<SciencesModel>,
    var checked:Boolean=false
):Serializable
