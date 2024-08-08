package com.example.kotlin.service

import com.example.kotlin.dto.MemberRequest
import com.example.kotlin.entity.Member
import com.example.kotlin.repository.MemberRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.test.assertEquals


internal class MemberServiceTest: BehaviorSpec ({
    isolationMode = IsolationMode.InstancePerTest

    val memberRepository = mockk<MemberRepository>()

    Given("전체 회원 조회 테스트") {
        val member1 = Member(1, "name", "123")
        val member2 = Member(2, "member", "456")

        val service = withContext(Dispatchers.IO) {
            MemberService(memberRepository)
        }

        When("회원 데이터 있음") {
            every { memberRepository.findAll() } returns listOf(member1, member2)

            Then("조회 성공") {
                val result = service.findAll()

                result shouldNotBe null

                verify(exactly = 1) {memberRepository.findAll()}

                assertEquals(1, result[0].memberId)
                assertEquals("name", result[0].memberName)
                assertEquals("123", result[0].password)
                assertEquals(2, result[1].memberId)
                assertEquals("member", result[1].memberName)
                assertEquals("456", result[1].password)
            }
        }

        When("회원 데이터 없음") {
            every { memberRepository.findAll() } returns listOf()

            Then("데이터 없음") {
                val result = service.findAll()

                result shouldNotBe null

                verify(exactly = 1) {memberRepository.findAll()}
            }
        }
    }


    Given("id로 회원조회 테스트") {
        val memberName = "memberName"
        val password = "1234"
        val member = Member(1, memberName, password)

        val service = withContext(Dispatchers.IO) {
            MemberService(memberRepository)
        }

        When("올바른 id 입력"){
            every { memberRepository.findById(any(Long::class)) } returns Optional.of(member)

            Then("조회 성공") {
                val result = service.findById(1L)

                result shouldNotBe null

                verify(exactly = 1) {memberRepository.findById(any(Long::class))}

                assertEquals(1, result.memberId)
                assertEquals("memberName", result.memberName)
                assertEquals("1234", result.password)
            }
        }

        When("올바르지 않은 id 입력"){
            every { memberRepository.findById(any(Long::class)) } throws RuntimeException()

            Then("조회 실패") {
                shouldThrow<RuntimeException> {
                    service.findById(9999L)
                }
            }
        }
    }

    Given("회원가입 테스트") {
        val memberName = "memberName"
        val password = "1234"
        val memberRequest = MemberRequest(memberName, password)
        val member = Member(1, memberName, password)

        val service = withContext(Dispatchers.IO) {
            MemberService(memberRepository)
        }

        When("올바른 계정 입력"){
            every { memberRepository.existsByMemberName(any(String::class)) } returns false
            every { memberRepository.save(any(Member::class)) } returns member

            Then("회원가입 성공"){
                val result = service.register(memberRequest)

                result shouldNotBe null

                verify(exactly = 1) {memberRepository.existsByMemberName(any(String::class))}
                verify(exactly = 1) {memberRepository.save(any(Member::class))}

                assertEquals(1, result.memberId)
                assertEquals("memberName", result.memberName)
                assertEquals("1234", result.password)
            }
        }

        When("올바르지 않은 계정 입력"){
            every { memberRepository.existsByMemberName(any(String::class)) } returns false
            every { memberRepository.save(any(Member::class)) } throws RuntimeException()

            Then("회원가입 실패"){
                shouldThrow<RuntimeException> {
                    service.register(memberRequest)
                }
            }
        }

        When("중복되는 계정 입력"){
            every { memberRepository.existsByMemberName(any(String::class)) } returns true

            Then("회원가입 실패"){
                shouldThrow<RuntimeException> {
                    service.register(memberRequest)
                }
            }
        }
    }

    Given("회원 삭제 테스트") {
        val memberId = 1L
        val member = Member(memberId, "name", "password")

        val service = withContext(Dispatchers.IO) {
            MemberService(memberRepository)
        }

        When("올바른 id 입력"){
            every { memberRepository.findById(any(Long::class)) } returns Optional.of(member)
            every { memberRepository.delete(any(Member::class)) } returns Unit

            Then("삭제 성공"){
                val result = service.deleteById(memberId)

                result shouldNotBe null

                verify(exactly = 1) {memberRepository.findById(any(Long::class))}
                verify(exactly = 1) {memberRepository.delete(any(Member::class))}

                assertEquals(1, result)
            }
        }

        When("올바르지 않은 id 입력"){
            every { memberRepository.findById(any(Long::class)) } returns Optional.empty()

            Then("삭제 실패"){
                shouldThrow<RuntimeException> {
                    service.deleteById(9999L)
                }
            }
        }
    }
})