package com.farroos.githubapp.data.model

data class User(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val name: String,
    val public_repos: Int,
)
