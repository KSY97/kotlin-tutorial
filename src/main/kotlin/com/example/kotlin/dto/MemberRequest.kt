package com.example.kotlin.dto

import com.example.kotlin.entity.Member

class MemberRequest (
    private val memberName: String,
    private val password: String
) {
    fun toEntity() : Member = Member(
        memberId = 0,
        memberName = this.memberName,
        password = this.password
    )
}