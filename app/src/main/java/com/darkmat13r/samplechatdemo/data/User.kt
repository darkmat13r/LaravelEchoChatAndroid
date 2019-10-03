package com.darkmat13r.samplechatdemo.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

	@field:SerializedName("role")
	var role: String? = null,

	@field:SerializedName("city")
	var city: String? = null,

	@field:SerializedName("timezone")
	var timezone: String? = null,

	@field:SerializedName("created_at")
	var createdAt: String? = null,

	@field:SerializedName("email_verified_at")
	var emailVerifiedAt: String? = null,

	@field:SerializedName("deleted_at")
	var deletedAt: String? = null,

	@field:SerializedName("is_verified")
	var isVerified: Boolean? = null,

	@field:SerializedName("access_token")
	var accessToken: String? = null,


	@field:SerializedName("updated_at")
	var updatedAt: String? = null,


	@field:SerializedName("contact")
	var contact: String? = null,

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("id")
	var id: Int? = null,

	@field:SerializedName("email")
	var email: String? = null



):Parcelable