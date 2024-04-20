package com.bootcamp.hackathon.dto.responses

class ProfileResponse(
    val id: Int,
    val personalInfo: PersonalInfo,
    val statistics: ProfileStatistics,
    val photoId: Int?
)

class PersonalInfo(
    val nickname: String,
    val city: String?,
    val status: String?
)

class ProfileStatistics(
    val rating: Int,
    val routesPassedN: Int,
    val routesDistanceKm: Float
)
