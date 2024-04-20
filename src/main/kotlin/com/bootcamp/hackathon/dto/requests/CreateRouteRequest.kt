package com.bootcamp.hackathon.dto.requests

import com.bootcamp.hackathon.models.RoutePoint

class CreateRouteRequest(
    val name: String,
    val points: ArrayList<RoutePoint>,
    val description: String?,
    val photosId : List<Int>
)