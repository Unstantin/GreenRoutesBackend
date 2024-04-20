package com.bootcamp.hackathon.entities

import jakarta.persistence.*

@Entity
@Table(name = "_favorite")
class FavoriteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    @ManyToOne
    @JoinColumn(name = "route_id")
    val route: RouteEntity
)