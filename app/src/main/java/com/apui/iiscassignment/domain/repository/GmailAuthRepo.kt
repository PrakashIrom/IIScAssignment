package com.apui.iiscassignment.domain.repository

interface GmailAuthRepo {
    fun signUpUserAccount(email:String, password:String)
    fun signInUserAccount(email:String, password:String)
}