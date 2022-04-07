package uz.bdmgroup.ilmizlab.model.request

import java.io.Serializable

data class UpdateRequest(
    var fullname:String,
    var avatar:String
):Serializable
