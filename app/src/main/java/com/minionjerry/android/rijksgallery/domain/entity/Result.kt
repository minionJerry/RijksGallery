package com.minionjerry.android.rijksgallery.domain.entity

import kotlin.Result

sealed class Result<out T: Any> {
    data class Success<out T: Any>(val data: T): com.minionjerry.android.rijksgallery.domain.entity.Result<T>()
    data class Error(val exception: UseCaseException): com.minionjerry.android.rijksgallery.domain.entity.Result<Nothing>()
}
