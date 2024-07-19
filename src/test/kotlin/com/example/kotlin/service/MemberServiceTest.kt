package com.example.kotlin.service

import com.example.kotlin.dto.MemberRequest
import com.example.kotlin.entity.Member
import com.example.kotlin.repository.MemberRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


internal class MemberServiceTest: BehaviorSpec ({
    val memberRepository = mockk<MemberRepository>()

    var memberName: String
    var password: String
    var memberRequest: MemberRequest
    var member: Member

    Given("회원가입 테스트") {
        memberName = "membername"
        password = "1234"
        memberRequest = MemberRequest(memberName, password)
        member = Member(0, memberName, password)

        val service = withContext(Dispatchers.IO) {
            MemberService(memberRepository)
        }

        When("올바른 계정 입력"){
            every { memberRepository.existsByMemberName(any()) } returns false
            every { memberRepository.save(any()) } returns member

            Then("회원가입 성공"){
                val result = service.register(memberRequest)

                result shouldNotBe null
            }
        }
    }

})