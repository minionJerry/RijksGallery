package com.minionjerry.android.rijksgallery.domain.entity

sealed class UseCaseException(cause: Throwable): Throwable(cause){
    class ArtObjectException(cause: Throwable): UseCaseException(cause)

    class UnknownException(cause: Throwable): UseCaseException(cause)

    companion object{
        fun createFromThrowable(throwable: Throwable): UseCaseException{
            return if (throwable is UseCaseException) throwable
            else UnknownException(throwable)
        }
    }
}
