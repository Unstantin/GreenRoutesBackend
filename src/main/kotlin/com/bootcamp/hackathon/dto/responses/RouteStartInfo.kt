package com.bootcamp.hackathon.dto.responses

import com.bootcamp.hackathon.models.RoutePoint

class RouteStartInfo(
    val id: Int,
    val name: String,
    val startCoordination: RoutePoint,
    val passed: Boolean
)