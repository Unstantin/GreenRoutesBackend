package com.bootcamp.hackathon.repositories

import com.bootcamp.hackathon.entities.FavoriteEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FavoriteRepository : JpaRepository<FavoriteEntity, Int> {
    fun findAllByRouteId(id: Int): List<FavoriteEntity>
    fun findAllByUserId(id: Int?): List<FavoriteEntity>
}