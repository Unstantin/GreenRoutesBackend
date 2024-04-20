package com.bootcamp.hackathon.services

import com.bootcamp.hackathon.dto.requests.AuthorityCreateRequest
import com.bootcamp.hackathon.dto.responses.AuthorityCreateResponse
import com.bootcamp.hackathon.entities.AuthorityEntity
import com.bootcamp.hackathon.repositories.AuthorityRepository
import com.bootcamp.hackathon.tools.AuthorityIsAlreadyExistException
import org.springframework.stereotype.Service

@Service
class AuthorityService(
    val authorityRepository: AuthorityRepository
) {
    fun createAuthority(request: AuthorityCreateRequest): AuthorityCreateResponse {
        if(authorityRepository.findByAuthority(request.authority).isPresent) {
            throw AuthorityIsAlreadyExistException()
        }

        val authority = authorityRepository.save(AuthorityEntity(authority = request.authority))
        return AuthorityCreateResponse(authorityId = authority.id!!)
    }
}