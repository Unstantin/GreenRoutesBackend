package com.bootcamp.hackathon.tools

import com.bootcamp.hackathon.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

class UsernameIsAlreadyTakenException : RuntimeException(USERNAME_IS_ALREADY_TAKEN_message)
class TokenCreatingErrorException : RuntimeException(TOKEN_CREATING_ERROR_message)
class AuthorityIsAlreadyExistException : RuntimeException(AUTHORITY_IS_ALREDY_EXIST_message)
class UserDidNotFoundException : RuntimeException(USER_DID_NOT_FOUND_message)
class RouteDidNotFoundException : RuntimeException(ROUTE_DID_NOT_FOUND_message)
class UserFromTokenDidNotFoundException : RuntimeException(USER_FROM_TOKEN_DID_NOT_FOUND_message)
class PhotoDidNotFoundException : RuntimeException(PHOTO_DID_NOT_FOUND_message)
class AccessIsDeniedException : RuntimeException(ACCESS_IS_DENIED_message)

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(value = [UsernameIsAlreadyTakenException::class])
    fun handleUserAlreadyExistsException(ex: UsernameIsAlreadyTakenException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(USERNAME_IS_ALREADY_TAKEN_code.toInt()).body(ex.message)
    }

    @ExceptionHandler(value = [TokenCreatingErrorException::class])
    fun handleTokenCreatingErrorException(ex: TokenCreatingErrorException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(TOKEN_CREATING_ERROR_code.toInt()).body(ex.message)
    }

    @ExceptionHandler(value = [AuthorityIsAlreadyExistException::class])
    fun handleAuthorityIsAlreadyExistException(ex: AuthorityIsAlreadyExistException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(AUTHORITY_IS_ALREDY_EXIST_code.toInt()).body(ex.message)
    }

    @ExceptionHandler(value = [UserDidNotFoundException::class])
    fun handleUserDidNotFoundException(ex: UserDidNotFoundException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(USER_DID_NOT_FOUND_code.toInt()).body(ex.message)
    }

    @ExceptionHandler(value = [RouteDidNotFoundException::class])
    fun handleRouteDidNotFoundException(ex: RouteDidNotFoundException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(ROUTE_DID_NOT_FOUND_code.toInt()).body(ex.message)
    }

    @ExceptionHandler(value = [UserFromTokenDidNotFoundException::class])
    fun handleUserFromTokenDidNotFoundException(ex: UserFromTokenDidNotFoundException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(USER_FROM_TOKEN_DID_NOT_FOUND_code.toInt()).body(ex.message)
    }

    @ExceptionHandler(value = [PhotoDidNotFoundException::class])
    fun handlePhotoDidNotFoundException(ex: PhotoDidNotFoundException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(PHOTO_DID_NOT_FOUND_code.toInt()).body(ex.message)
    }

    @ExceptionHandler(value = [AccessIsDeniedException::class])
    fun handleAccessIsDeniedException(ex: AccessIsDeniedException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(ACCESS_IS_DENIED_code.toInt()).body(ex.message)
    }
}