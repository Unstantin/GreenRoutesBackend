package com.bootcamp.hackathon.controllers

import com.bootcamp.hackathon.*
import com.bootcamp.hackathon.dto.responses.PhotoCreateResponse
import com.bootcamp.hackathon.entities.PhotoEntity
import com.bootcamp.hackathon.repositories.PhotoRepository
import com.bootcamp.hackathon.repositories.UserRepository
import com.bootcamp.hackathon.tools.AccessIsDeniedException
import com.bootcamp.hackathon.tools.PhotoDidNotFoundException
import com.bootcamp.hackathon.tools.UserFromTokenDidNotFoundException
import io.jsonwebtoken.Claims
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*


@Controller
@RequestMapping("/v1/photo") //TODO протестировать модуль фото, сделать так чтобы в сваггер можно было загружать фото
class PhotoController(
    val photoRepository: PhotoRepository,
    val userRepository: UserRepository
) {
    @Operation(summary = "Загрузка фото на сервер")
    @PostMapping
    @ApiResponses(value = [
        ApiResponse(responseCode = USER_FROM_TOKEN_DID_NOT_FOUND_code, description = USER_FROM_TOKEN_DID_NOT_FOUND_message),
        ApiResponse(responseCode = OK_code, description = OK_message,
            content = [Content(schema = Schema(implementation = PhotoCreateResponse::class)) ])
    ])
    fun uploadPhoto(@RequestParam("file") file: MultipartFile) : Any {
        val uploadPath = "C:\\Users\\KONSTANTIN\\Desktop\\hackathon\\photo"

        val auth = SecurityContextHolder.getContext().authentication
        val tokenId = (auth.credentials as Claims)["userid"] as Int
        if(userRepository.findById(tokenId).isEmpty) throw UserFromTokenDidNotFoundException()
        val user = userRepository.findById(tokenId).get()

        val uuidFile = UUID.randomUUID().toString()
        val resultFileName = uuidFile + "." + file.originalFilename

        file.transferTo(File("$uploadPath/$resultFileName"))

        val photo = photoRepository.save(
            PhotoEntity(
                name = resultFileName,
                author = user
            )
        )

        return PhotoCreateResponse(photoId = photo.id!!)
    }

    @Operation(summary = "Получение фото с сервера")
    @GetMapping("/{id}")
    @ApiResponses(value = [
        ApiResponse(responseCode = PHOTO_DID_NOT_FOUND_code, description = PHOTO_DID_NOT_FOUND_message),
        ApiResponse(responseCode = USER_FROM_TOKEN_DID_NOT_FOUND_code, description = USER_FROM_TOKEN_DID_NOT_FOUND_message),
        ApiResponse(responseCode = OK_code, description = OK_message, content = [Content(mediaType = "multipart/form-data")])
    ])
    fun downloadPhoto(@RequestParam id: Int) : Any {
        val photoOptional = photoRepository.findById(id)
        if(photoOptional.isEmpty) throw PhotoDidNotFoundException()
        val photo = photoOptional.get()

        return File("C:\\Users\\KONSTANTIN\\Desktop\\hackathon\\photo\\${photo.name}").readBytes()
    }

    @Operation(summary = "Удаление фото с сервера")
    @DeleteMapping("/{id}")
    @ApiResponses(value = [
        ApiResponse(responseCode = PHOTO_DID_NOT_FOUND_code, description = PHOTO_DID_NOT_FOUND_message),
        ApiResponse(responseCode = USER_FROM_TOKEN_DID_NOT_FOUND_code, description = USER_FROM_TOKEN_DID_NOT_FOUND_message),
        ApiResponse(responseCode = OK_code, description = OK_message)
    ])
    fun deletePhoto(@RequestParam id: Int) : Any {
        val photoOptional = photoRepository.findById(id)
        if(photoOptional.isEmpty) throw PhotoDidNotFoundException()
        val photo = photoOptional.get()

        val auth = SecurityContextHolder.getContext().authentication
        val tokenId = (auth.credentials as Claims)["userid"] as Int
        if(userRepository.findById(tokenId).isEmpty) throw UserFromTokenDidNotFoundException()
        val user = userRepository.findById(tokenId).get()

        if(photo.author.id != user.id) throw AccessIsDeniedException()

        File("C:\\Users\\KONSTANTIN\\Desktop\\hackathon\\photo\\${photo.name}").delete()
        photoRepository.deleteById(id)

        return "OK"
    }
}