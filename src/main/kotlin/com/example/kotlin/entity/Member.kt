package com.example.kotlin.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
class Member (
    @Id
    @GeneratedValue
    var memberId: Long,
    var memberName: String,
    var password: String,
)