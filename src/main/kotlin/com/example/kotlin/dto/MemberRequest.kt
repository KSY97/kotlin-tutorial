package com.example.kotlin.dto

import com.example.kotlin.entity.Member

data class MemberRequest (
    val memberName: String,
    val password: String
) {
    fun toEntity() : Member = Member(
        memberName = this.memberName,
        password = this.password
    )
}