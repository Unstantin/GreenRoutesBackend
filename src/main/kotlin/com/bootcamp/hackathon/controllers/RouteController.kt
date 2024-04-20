package com.bootcamp.hackathon.controllers

import com.bootcamp.hackathon.*
import com.bootcamp.hackathon.dto.requests.CreateRouteRequest
import com.bootcamp.hackathon.dto.requests.GetNearbyRoutesRequest
import com.bootcamp.hackathon.dto.requests.TripEndReport
import com.bootcamp.hackathon.dto.responses.*
import com.bootcamp.hackathon.models.RoutePoint
import com.bootcamp.hackathon.services.RouteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/routes")
class RouteController(
    val routeService: RouteService
) {
    @Operation(summary = "Получение информации о ближайших маршрутах")
    @GetMapping
    @ApiResponses(value = [
        ApiResponse(responseCode = USER_FROM_TOKEN_DID_NOT_FOUND_code, description = USER_FROM_TOKEN_DID_NOT_FOUND_message),
        ApiResponse(responseCode = OK_code, description = OK_message,
            content = [ Content(array = ArraySchema(schema = Schema(implementation = RouteStartInfo::class))) ])
    ])
    fun getNearByRoutes(@RequestBody request: GetNearbyRoutesRequest) : Any {
        return routeService.getNearByRoutes(RoutePoint(request.coordinationX, request.coordinationY))
    }

    @Operation(summary = "Создание маршрута")
    @PostMapping
    @ApiResponses(value = [
        ApiResponse(responseCode = USER_FROM_TOKEN_DID_NOT_FOUND_code, description = USER_FROM_TOKEN_DID_NOT_FOUND_message),
        ApiResponse(responseCode = OK_code, description = OK_message)
    ])
    fun createRoute(@RequestBody request: CreateRouteRequest) : Any {
        val p: java.awt.Point = java.awt.Point()
        return routeService.createRoute(request)
    }

    @Operation(summary = "Получение информации о выбранном маршруте")
    @GetMapping("/{id}")
    @ApiResponses(value = [
        ApiResponse(responseCode = ROUTE_DID_NOT_FOUND_code, description = ROUTE_DID_NOT_FOUND_message),
        ApiResponse(responseCode = USER_FROM_TOKEN_DID_NOT_FOUND_code, description = USER_FROM_TOKEN_DID_NOT_FOUND_message),
        ApiResponse(responseCode = OK_code, description = OK_message,
            content = [Content(schema = Schema(implementation = SelectedRouteInfo::class)) ]),
    ])
    fun getSelectedRouteInfoById(@PathVariable id: Int) : Any {
        return routeService.getSelectedRouteInfoById(id)
    }

    @Operation(summary = "Передача отчета завершения маршрута", description = "Время в СЕКУНДАХ (однако пока отправляем просто 0)")
    @PostMapping("/report")
    @ApiResponses(value = [
        ApiResponse(responseCode = ROUTE_DID_NOT_FOUND_code, description = ROUTE_DID_NOT_FOUND_message),
        ApiResponse(responseCode = USER_FROM_TOKEN_DID_NOT_FOUND_code, description = USER_FROM_TOKEN_DID_NOT_FOUND_message),
        ApiResponse(responseCode = OK_code, description = OK_message,
            content = [Content(schema = Schema(implementation = SelectedRouteInfo::class)) ]),
    ])
    fun sendTripEndReport(@RequestBody report: TripEndReport) : Any {
        return routeService.tripEnd(report)
    }

}