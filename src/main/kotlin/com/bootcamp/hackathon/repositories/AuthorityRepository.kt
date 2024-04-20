package com.bootcamp.hackathon.repositories

import com.bootcamp.hackathon.entities.AuthorityEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuthorityRepository : JpaRepository<AuthorityEntity, Int> {
    fun findByAuthority(authority: String): Optional<AuthorityEntity>
}