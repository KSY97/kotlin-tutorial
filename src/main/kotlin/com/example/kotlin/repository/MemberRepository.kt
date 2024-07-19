package com.example.kotlin.repository

import com.example.kotlin.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: JpaRepository<Member, Long>{
    fun existsByMemberName(memberName: String): Boolean
}