package uz.bdmgroup.ilmizlab.model

data class LCFilterModel(
    val region_id:Int,
    var district_id:Int,
    var category_id:Int,
    var science_id:Int,
    val keyword:String,
    var sort:String,
    val limit:Int,
    var latitude:Double,
    var longitude:Double,
    val is_subscriber: Boolean
)
