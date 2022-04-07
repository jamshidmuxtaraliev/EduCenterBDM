package uz.bdmgroup.ilmizlab.model

import java.io.Serializable

data class EduCenterModel(
    val id: Int,
    val region_id: Int,
    val district_id: Int,
    val name: String,
    val phone: String,
    val address: String,
    val comment: String,
    val monthly_payment_min: Int,
    val monthly_payment_max: Int,
    val main_image: String,
    val latitude: Double,
    val longitude: Double,
    val rating: Double,
    val rating_count: Int,
    val subsribers_count: Int,
    val district: DistrictModel,
    val region: RegionModel,
    val courses:List<CoursesModel>
): Serializable
