package com.example.kotlin.dto

import com.example.kotlin.entity.Member

class MemberResponse(
    val id: Long,
    val memberName: String,
) {

    companion object {
        fun fromEntity(member: Member) : MemberResponse = MemberResponse(
            id = member.memberId,
            memberName = member.memberName,
        )
    }
}