package com.bootcamp.hackathon.controllers

import com.bootcamp.hackathon.*
import com.bootcamp.hackathon.dto.UserData
import com.bootcamp.hackathon.dto.responses.JwtResponse
import com.bootcamp.hackathon.dto.responses.RegistrationResponse
import com.bootcamp.hackathon.services.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(
   val authService: AuthService
) {
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/reg")
    @ApiResponses(value = [
        ApiResponse(responseCode = USERNAME_IS_ALREADY_TAKEN_code, description = USERNAME_IS_ALREADY_TAKEN_message),
        ApiResponse(responseCode = OK_code, description = OK_message,
            content = [Content(schema = Schema(implementation = RegistrationResponse::class)) ])
    ])
    fun registration(@RequestBody userData: UserData) : Any {
        return authService.registration(userData)
    }

    @Operation(summary = "Получение jwt токена")
    @PostMapping("/log")
    @ApiResponses(value = [
        ApiResponse(responseCode = TOKEN_CREATING_ERROR_code, description = TOKEN_CREATING_ERROR_message),
        ApiResponse(responseCode = OK_code, description = OK_message,
            content = [Content(schema = Schema(implementation = JwtResponse::class)) ])
    ])
    fun authentication(@RequestBody userData: UserData) : Any {
        return authService.authentication(userData)
    }
}