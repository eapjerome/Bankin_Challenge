package com.eap.bankin_challenge_jerome_eap

data class ResourceModel(
    val id: Int,
    val resource_uri: String,
    val resource_type: String,
    val name: String,
    val parent: ResourceParent?,
    val custom: Boolean,
    val other: Boolean,
    val is_deleted: Boolean
)

data class ResourceParent(
    val id: Int,
    val resource_uri: String,
    val resource_type: String
)