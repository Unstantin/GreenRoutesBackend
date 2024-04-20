package com.bootcamp.hackathon.dto.responses

import com.bootcamp.hackathon.models.RoutePoint

class SelectedRouteInfo(
    val name: String,
    val authorNickname: String,
    val distance: Float,
    val favoriteN: Int,
    val points: List<RoutePoint>,
    val passed: Boolean,
    val description: String?
)