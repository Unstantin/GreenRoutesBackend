package com.bootcamp.hackathon.entities

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name = "_authorities")
class AuthorityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column(name = "authority")
    private var authority: String
) : GrantedAuthority {
    override fun getAuthority(): String {
        return authority
    }
}