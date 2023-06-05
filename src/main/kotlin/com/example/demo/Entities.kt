package com.example.demo

import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
class Article(
		val title: String,
		val headline: String,
		val content: String,
		@ManyToOne val author: User,
		val slug: String = title.toSlug(),
		val addedAt: LocalDateTime = LocalDateTime.now(),
		@Id @GeneratedValue val id: Long? = null)

@Entity
class User(
		val login: String,
		val firstname: String,
		val lastname: String,
		val description: String? = null,
		@Id @GeneratedValue val id: Long? = null)
