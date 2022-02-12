package com.kalbe.datasource.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.kalbe.core.utils.BroadcastReceivers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Response
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

@OptIn(KoinApiExtension::class)
abstract class BaseRepository: KoinComponent {
    private val broadcastReceiver: BroadcastReceivers by inject()

    suspend fun <T> safeApiCall(
        apiCall : suspend () -> T ): Resource<T> {

        return withContext(Dispatchers.IO){
            try {
                Resource.Success(apiCall.invoke())
            } catch(throwable : Throwable) {

                when(throwable){
                    is HttpException -> {

                        @Suppress("BlockingMethodInNonBlockingContext")
                        val response = parseError(throwable)
                        // Send log to crashlytic with url request
                        val responseRaw = throwable.response()?.raw() as Response

                        when (response.code) {
                            401 -> {
                                broadcastReceiver.postNotification(
                                    BroadcastReceivers.NotificationType.UNAUTHORIZED
                                )
                                Resource.Failure()
                            }

                            else -> {
                                Resource.Failure(response.message)
                            }
                        }
                    }

                    is SocketTimeoutException -> {
                        Resource.Failure("Request Time Out")
                    }

                    is IOException -> {
                        // TO DO : You can post broadcast receiver for implement it on BaseActivity
                        Resource.Failure("No Internet")
                    }

                    else -> {
                        Log.i("ExceptionNetwork", throwable.localizedMessage ?: "not handled")
                        Resource.Failure("Something Went Wrong")
                    }
                }
            }
        }
    }

    /**
     * Parse error from server
     * to ErrorResponse Model
     */
    private fun parseError(throwable: HttpException): ErrorResponse {
        return try {
            Gson().fromJson(throwable.response()?.errorBody()?.string(), ErrorResponse::class.java)
        } catch (jsonException: JsonSyntaxException) {
            ErrorResponse(code = throwable.code(), message = throwable.message())
        }
    }

}