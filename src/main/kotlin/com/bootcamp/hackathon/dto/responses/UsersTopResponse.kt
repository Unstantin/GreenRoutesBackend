package com.bootcamp.hackathon.dto.responses

class UsersTopResponse(
    val top: List<UserTopInfo>
)

class UserTopInfo(
    val nickname: String,
    val rating: Int,
    val distance: Float
)