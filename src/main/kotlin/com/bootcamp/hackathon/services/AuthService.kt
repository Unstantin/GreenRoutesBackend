package com.bootcamp.hackathon.services

import com.bootcamp.hackathon.dto.UserData
import com.bootcamp.hackathon.dto.responses.JwtResponse
import com.bootcamp.hackathon.dto.responses.RegistrationResponse
import com.bootcamp.hackathon.entities.UserEntity
import com.bootcamp.hackathon.jwt.JwtTools
import com.bootcamp.hackathon.repositories.AuthorityRepository
import com.bootcamp.hackathon.repositories.UserRepository
import com.bootcamp.hackathon.tools.TokenCreatingErrorException
import com.bootcamp.hackathon.tools.UsernameIsAlreadyTakenException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    val userRepository: UserRepository,
    val authenticationManager: AuthenticationManager,
    val authorityRepository: AuthorityRepository,
    val jwtTools: JwtTools,
    val encoder: BCryptPasswordEncoder
) {
    fun registration(userData: UserData): RegistrationResponse {
        if(userRepository.findByUsername(userData.username).isPresent) {
            throw UsernameIsAlreadyTakenException()
        }

        var user = UserEntity(
            username = userData.username,
            password = encoder.encode(userData.password),
            authorities = setOf(authorityRepository.findByAuthority("USER").get()),
            rating = 0,
            passed = listOf(),
            favorites = listOf()
        )
        user = userRepository.save(user).apply { nickname = "id" + user.id }
        userRepository.save(user)

        return RegistrationResponse(user.id!!)
    }

    fun authentication(userData: UserData): JwtResponse {
        val auth = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                userData.username,
                userData.password
            )
        )

        val token = jwtTools.generateToken(auth)
        return if(token.isNullOrEmpty()) {
            throw TokenCreatingErrorException()
        } else {
            JwtResponse(token)
        }
    }
}