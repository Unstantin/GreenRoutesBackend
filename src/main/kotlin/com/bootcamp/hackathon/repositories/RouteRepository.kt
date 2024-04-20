package com.bootcamp.hackathon.repositories

import com.bootcamp.hackathon.entities.RouteEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RouteRepository : JpaRepository<RouteEntity, Int> {
    fun findAllByAuthorId(id: Int?): ArrayList<RouteEntity>
}