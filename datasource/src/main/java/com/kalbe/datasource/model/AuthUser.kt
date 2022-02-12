package com.kalbe.datasource.model

import com.google.gson.annotations.SerializedName

data class AuthUser(

    @SerializedName("initialName")
    var initialName: String = "",

    @SerializedName("token")
    var token : String = "",

    @SerializedName("refresh_token")
    var refreshToken: String = ""
)