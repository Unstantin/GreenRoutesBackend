package com.bootcamp.hackathon.entities

import jakarta.persistence.*

@Entity
@Table(name = "_photo")
class PhotoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,

    @Column(name = "name_on_server")
    val name: String,

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: UserEntity
)