package com.bootcamp.hackathon.entities

import com.bootcamp.hackathon.models.RoutePoint
import jakarta.persistence.*

@Entity
@Table(name = "_routes")
class RouteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column(name = "name")
    var name: String,

    @Column(name = "points")
    var points: ArrayList<RoutePoint>,

    @ManyToOne
    @JoinColumn(name = "author_id")
    var author: UserEntity,

    @Column(name = "description")
    var description: String?,

    @Column(name = "distance")
    var distance: Float,

    @OneToMany
    var photos: List<PhotoEntity>
)