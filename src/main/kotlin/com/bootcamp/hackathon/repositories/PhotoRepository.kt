package com.bootcamp.hackathon.repositories

import com.bootcamp.hackathon.entities.PhotoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PhotoRepository : JpaRepository<PhotoEntity, Int> {
}