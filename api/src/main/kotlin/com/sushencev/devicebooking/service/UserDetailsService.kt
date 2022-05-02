package com.sushencev.devicebooking.service

import com.sushencev.devicebooking.entity.repository.UserRepo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService(
    private val userRepo: UserRepo,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepo.findByName(username) ?: throw UsernameNotFoundException("User not found")
    }
}
