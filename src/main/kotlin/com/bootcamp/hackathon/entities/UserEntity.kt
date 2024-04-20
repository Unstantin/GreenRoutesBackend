package com.bootcamp.hackathon.entities

import jakarta.persistence.*
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "_users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Int? = null,

    @Column(name = "username")
    private var username: String,

    @Column(name = "password")
    private var password: String,

    @Column(name = "nickname")
    var nickname: String = "",

    @Column(name = "rating")
    var rating: Int,

    @Column(name = "status")
    var status: String? = null,

    @Column(name = "city")
    var city: String? = null,

    @Column(name = "photo_id")
    var photoId: Int? = null,

    @ManyToMany
    @JoinTable(name = "_users_to_passed")
    var passed: List<PassedEntity>,

    @ManyToMany
    @JoinTable(name = "_users_to_favorites")
    var favorites: List<FavoriteEntity>,

    @ManyToMany
    @JoinTable(name = "_users_to_authorities")
    private var authorities: Set<AuthorityEntity>

) : UserDetails {
    override fun getAuthorities(): Set<AuthorityEntity> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}