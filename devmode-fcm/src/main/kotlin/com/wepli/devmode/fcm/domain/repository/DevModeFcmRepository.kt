package com.wepli.devmode.fcm.domain.repository

interface DevModeFcmRepository {

    fun getFcmAccessToken(): String
}