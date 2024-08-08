package com.example.kotlin.service

import com.example.kotlin.dto.MemberRequest
import com.example.kotlin.entity.Member
import com.example.kotlin.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService (private val memberRepository: MemberRepository) {

    fun findAll(): List<Member> =
        memberRepository.findAll()

    fun findById(id: Long): Member =
        memberRepository.findById(id)
            .orElseThrow { RuntimeException() }

    fun register(memberRequest: MemberRequest): Member {

        if(memberRepository.existsByMemberName(memberRequest.memberName)) {
            throw RuntimeException()
        }

        return memberRepository.save(memberRequest.toEntity())
    }

    fun deleteById(id: Long): Long {
        val member: Member = memberRepository.findById(id)
            .orElseThrow { throw RuntimeException() }
        memberRepository.delete(member)
        return id
    }

}