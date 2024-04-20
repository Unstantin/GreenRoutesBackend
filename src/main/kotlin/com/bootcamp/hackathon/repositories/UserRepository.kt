package com.bootcamp.hackathon.repositories

import com.bootcamp.hackathon.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, Int> {
    fun findByUsername(username: String): Optional<UserEntity>
}