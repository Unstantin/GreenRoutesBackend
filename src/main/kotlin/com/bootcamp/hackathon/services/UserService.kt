package com.bootcamp.hackathon.services

import com.bootcamp.hackathon.dto.responses.*
import com.bootcamp.hackathon.repositories.FavoriteRepository
import com.bootcamp.hackathon.repositories.PassedRepository
import com.bootcamp.hackathon.repositories.RouteRepository
import com.bootcamp.hackathon.repositories.UserRepository
import com.bootcamp.hackathon.tools.UserDidNotFoundException
import com.bootcamp.hackathon.tools.UserFromTokenDidNotFoundException
import io.jsonwebtoken.Claims
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import kotlin.streams.asSequence

@Service
class UserService(
    val userRepository: UserRepository,
    val routeRepository: RouteRepository,
    val routeService: RouteService,
    val favoriteRepository: FavoriteRepository,
    val passedRepository: PassedRepository
) {
    fun getUserProfile(id: Int): ProfileResponse {
        val userOptional = userRepository.findById(id)
        if(userOptional.isEmpty) {
            throw UserDidNotFoundException()
        } else {
            val user = userOptional.get()
            return ProfileResponse(
                id = id,
                personalInfo = PersonalInfo(
                    nickname = user.nickname,
                    city = user.city,
                    status = user.status
                ),
                statistics = ProfileStatistics(
                    rating = user.rating,
                    routesDistanceKm = user.passed.asSequence().map { it.route.distance }.sum(),
                    routesPassedN = user.passed.size
                ),
                photoId = user.photoId
            )
        }
    }

    fun getRouteList(): RouteListResponse {
        val auth = SecurityContextHolder.getContext().authentication
        val tokenId = (auth.credentials as Claims)["userid"] as Int
        if(userRepository.findById(tokenId).isEmpty) throw UserFromTokenDidNotFoundException()
        val user = userRepository.findById(tokenId).get()

        return RouteListResponse(
            created = routeRepository.findAllByAuthorId(user.id).asSequence().map {
                RouteInfo(
                    id = it.id!!,
                    name = it.name,
                    passed = routeService.isRoutePassed(it.id!!, user.id!!),
                    distance = it.distance
                )
            }.toList(),
            favorite = favoriteRepository.findAllByUserId(user.id).asSequence().map {
                RouteInfo(
                    id = it.id!!,
                    name = it.route.name,
                    passed = routeService.isRoutePassed(it.id, user.id!!),
                    distance = it.route.distance
                )
            }.toList()
        )
    }

    fun getUsersTop(): UsersTopResponse {
        val users = userRepository.findAll().asSequence().filter { it.rating > 0 }.sortedBy { it.rating }.toList()

        return UsersTopResponse(top = users.asSequence().map {
            UserTopInfo(
                nickname = it.nickname,
                rating = it.rating,
                distance = passedRepository.findAllByUserId(it.id!!).sumOf { it.route.distance.toDouble() }.toFloat()
            )
        }.toList())
    }
}