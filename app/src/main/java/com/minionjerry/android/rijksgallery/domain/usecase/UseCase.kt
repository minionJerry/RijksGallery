package com.minionjerry.android.rijksgallery.domain.usecase

import com.minionjerry.android.rijksgallery.domain.entity.Result
import com.minionjerry.android.rijksgallery.domain.entity.UseCaseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class UseCase<I: UseCase.Request, O: UseCase.Response>(private val configuration: Configuration) {

    fun execute(request: I) = process(request)
        .map {
            Result.Success(it) as Result<O>
        }
        .flowOn(configuration.dispatcher)
        .catch {
            emit(Result.Error(UseCaseException.createFromThrowable(it)))
        }


    abstract fun process(request: I): Flow<Response>

    interface Request
    interface Response
    class Configuration(val dispatcher: CoroutineDispatcher)

}