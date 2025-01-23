package com.example.reelstar

data class UserModel(
    val id:String="",
    val email:String="",
    val username:String="",
    val profilePic:String="",
    val followerList:MutableList<String> = mutableListOf(),
    val followingList: MutableList<String> = mutableListOf()
)
