package com.example.kotlin.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Member (
    @Id
    @GeneratedValue
    var memberId: Long = 0,
    var memberName: String,
    var password: String,
)