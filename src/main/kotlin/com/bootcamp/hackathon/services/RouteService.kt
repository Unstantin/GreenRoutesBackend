package com.bootcamp.hackathon.services

import com.bootcamp.hackathon.dto.requests.CreateRouteRequest
import com.bootcamp.hackathon.dto.requests.CreateRouteResponse
import com.bootcamp.hackathon.dto.requests.TripEndReport
import com.bootcamp.hackathon.dto.responses.RouteStartInfo
import com.bootcamp.hackathon.dto.responses.SelectedRouteInfo
import com.bootcamp.hackathon.dto.responses.TripEndResponse
import com.bootcamp.hackathon.entities.RouteEntity
import com.bootcamp.hackathon.models.RoutePoint
import com.bootcamp.hackathon.repositories.FavoriteRepository
import com.bootcamp.hackathon.repositories.PhotoRepository
import com.bootcamp.hackathon.repositories.RouteRepository
import com.bootcamp.hackathon.repositories.UserRepository
import com.bootcamp.hackathon.tools.RouteDidNotFoundException
import com.bootcamp.hackathon.tools.UserFromTokenDidNotFoundException
import io.jsonwebtoken.Claims
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import kotlin.math.abs
import kotlin.math.pow

@Service
class RouteService(
    val routeRepository: RouteRepository,
    val userRepository: UserRepository,
    val favoriteRepository: FavoriteRepository,
    val photoRepository: PhotoRepository
) {
    fun getNearByRoutes(point: RoutePoint): List<RouteStartInfo> {
        val auth = SecurityContextHolder.getContext().authentication
        val tokenId = (auth.credentials as Claims)["userid"] as Int
        if(userRepository.findById(tokenId).isEmpty) throw UserFromTokenDidNotFoundException()
        val user = userRepository.findById(tokenId).get()

        val routes = routeRepository.findAll()
        val routesFiltered = routes.asSequence().filter {
            ((it.points[0].x - point.x).pow(2.0f) + (it.points[0].y - point.y).pow(2.0f)).pow(0.5f) < 5000
        }.toList()

        return routesFiltered.map {
                RouteStartInfo(
                    id = it.id!!,
                    name = it.name,
                    startCoordination = it.points[0],
                    passed = isRoutePassed(it.id!!, user.id!!)
                )
        }.toList()
    }

    fun isRoutePassed(routeId: Int, userId: Int) : Boolean {
        val user = userRepository.findById(userId).get()
        var searchResult = false
        for(passedRoute in user.passed) {
            if(passedRoute.id == routeId) {
                searchResult = true
                break
            }
        }
        return searchResult
    }

    fun getSelectedRouteInfoById(routeId: Int): SelectedRouteInfo {
        val routeOptional = routeRepository.findById(routeId)
        if(routeOptional.isEmpty) throw RouteDidNotFoundException()

        val auth = SecurityContextHolder.getContext().authentication
        val tokenId = (auth.credentials as Claims)["userid"] as Int
        if(userRepository.findById(tokenId).isEmpty) throw UserFromTokenDidNotFoundException()
        val user = userRepository.findById(tokenId).get()

        val route = routeOptional.get()
        return SelectedRouteInfo(
            name = route.name,
            authorNickname = route.author.nickname,
            passed = isRoutePassed(route.id!!, user.id!!),
            distance = route.distance,
            favoriteN = favoriteRepository.findAllByRouteId(route.id!!).size,
            points = route.points,
            description = route.description
        )
    }

    fun createRoute(request: CreateRouteRequest): CreateRouteResponse {
        val auth = SecurityContextHolder.getContext().authentication
        val tokenId = (auth.credentials as Claims)["userid"] as Int
        if(userRepository.findById(tokenId).isEmpty) throw UserFromTokenDidNotFoundException()
        val user = userRepository.findById(tokenId).get()

        val route = routeRepository.save(
            RouteEntity(
                name = request.name,
                description = request.description,
                author = user,
                points = request.points,
                distance = calculateDistance(request.points),
                photos = request.photosId.stream().map { photoRepository.findById(it).get() }.toList()
            )
        )

        return CreateRouteResponse(routeId = route.id!!)
    }

    fun calculateDistance(points: List<RoutePoint>) : Float {
        var sum = 0f
        var prevPoint = points[0]
        for(i in 1..points.size) {
            sum += (abs(points[i].x - prevPoint.x) +
            abs(points[i].y - prevPoint.y))
            prevPoint = points[i]
        }
        return sum
    }

    fun tripEnd(report: TripEndReport) : TripEndResponse {
        val auth = SecurityContextHolder.getContext().authentication
        val tokenId = (auth.credentials as Claims)["userid"] as Int
        if(userRepository.findById(tokenId).isEmpty) throw UserFromTokenDidNotFoundException()
        val user = userRepository.findById(tokenId).get()

        val routeOptional = routeRepository.findById(report.routeId)
        if(routeOptional.isEmpty) throw RouteDidNotFoundException()
        val route = routeOptional.get()

        val deltaRating = (route.distance * 100).toInt()
        user.rating += deltaRating
        userRepository.save(user)

        return TripEndResponse(
            deltaRating = deltaRating,
            newRating = user.rating
        )
    }

}