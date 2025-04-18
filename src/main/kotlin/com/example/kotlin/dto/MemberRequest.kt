package com.example.kotlin.dto

import com.example.kotlin.entity.Member

class MemberRequest (
    private val memberName: String,
    private val password: String
) {
    fun toEntity() : Member = Member(
        memberName = this.memberName,
        password = this.password
    )
}