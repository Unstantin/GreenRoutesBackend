package com.bootcamp.hackathon.repositories

import com.bootcamp.hackathon.entities.PassedEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PassedRepository : JpaRepository<PassedEntity, Int> {
    fun findAllByUserId(id: Int): List<PassedEntity>
}