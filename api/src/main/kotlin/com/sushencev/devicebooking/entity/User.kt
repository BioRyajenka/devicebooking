package com.sushencev.devicebooking.entity

import org.hibernate.annotations.GenericGenerator
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    var name: String,
    private var password: String,
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID? = null,
) : UserDetails {
    override fun getAuthorities() = listOf(SimpleGrantedAuthority("user"))

    override fun getPassword() = password
    override fun getUsername() = name

    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}
