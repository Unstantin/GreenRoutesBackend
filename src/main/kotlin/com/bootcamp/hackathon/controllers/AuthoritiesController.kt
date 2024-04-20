package com.bootcamp.hackathon.controllers

import com.bootcamp.hackathon.dto.requests.AuthorityCreateRequest
import com.bootcamp.hackathon.dto.responses.AuthorityCreateResponse
import com.bootcamp.hackathon.services.AuthorityService
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@Hidden
@RequestMapping("/v1/authorities")
class AuthoritiesController(
    val authorityService: AuthorityService
) {
    @PostMapping
    fun createAuthority(@RequestBody request: AuthorityCreateRequest): AuthorityCreateResponse {
        return authorityService.createAuthority(request)
    }
}