package uz.bdmgroup.ilmizlab.model

import java.io.Serializable

data class TeacherModel (
    val id: Int,
    val center_id: Int,
    val name: String,
    val info_link: String,
    val specialization: String,
    val experience: Int,
    val avatar: String,
    val created_at: String,
    val updated_at: String
    ): Serializable
