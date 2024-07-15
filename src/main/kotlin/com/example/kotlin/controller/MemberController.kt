package com.example.kotlin.controller

import com.example.kotlin.dto.MemberRequest
import com.example.kotlin.dto.MemberResponse
import com.example.kotlin.entity.Member
import com.example.kotlin.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberController(private val memberService: MemberService) {

    @GetMapping
    fun getAll(): ResponseEntity<List<MemberResponse>> =
        ResponseEntity.ok(memberService.findAll().map(MemberResponse::fromEntity))

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<MemberResponse> =
        ResponseEntity.ok(MemberResponse.fromEntity(memberService.findById(id)))

    @PostMapping
    fun register(@RequestBody memberRequest: MemberRequest): ResponseEntity<MemberResponse> =
        ResponseEntity.ok(MemberResponse.fromEntity(memberService.register(memberRequest)))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Long> =
        ResponseEntity.ok(memberService.deleteById(id))
}