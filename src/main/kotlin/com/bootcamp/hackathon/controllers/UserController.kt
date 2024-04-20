package com.bootcamp.hackathon.controllers

import com.bootcamp.hackathon.*
import com.bootcamp.hackathon.dto.responses.ProfileResponse
import com.bootcamp.hackathon.dto.responses.RouteListResponse
import com.bootcamp.hackathon.dto.responses.UsersTopResponse
import com.bootcamp.hackathon.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserController(
    val userService: UserService
) {
    @Operation(summary = "Получение профиля пользователя по айди")
    @GetMapping("/{id}")
    @ApiResponses(value = [
        ApiResponse(responseCode = USER_DID_NOT_FOUND_code, description = USER_DID_NOT_FOUND_message),
        ApiResponse(responseCode = OK_code, description = OK_message,
            content = [Content(schema = Schema(implementation = ProfileResponse::class)) ])
    ])
    fun getUserProfile(@PathVariable id: Int) : Any {
        return userService.getUserProfile(id)
    }

    @Operation(summary = "Получение списка избранных и пройденых маршрутов")
    @GetMapping("/route-list")
    @ApiResponses(value = [
        ApiResponse(responseCode = USER_FROM_TOKEN_DID_NOT_FOUND_code, description = USER_FROM_TOKEN_DID_NOT_FOUND_message),
        ApiResponse(responseCode = OK_code, description = OK_message,
            content = [Content(schema = Schema(implementation = RouteListResponse::class))])
    ])
    fun getListRoutes() : Any {
        return userService.getRouteList()
    }

    @Operation(summary = "Получение топа пользователей")
    @GetMapping("/top")
    @ApiResponses(value = [
            ApiResponse(responseCode = OK_code, description = OK_message,
                content = [Content(schema = Schema(implementation = UsersTopResponse::class))])
    ])
    fun getUsersTop() : Any {
        return userService.getUsersTop()
    }
}