package com.test.application.core.view

interface Interactor<T> {
    suspend fun getData() : T
}