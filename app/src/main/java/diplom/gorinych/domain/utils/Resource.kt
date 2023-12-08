package diplom.gorinych.domain.utils

import retrofit2.HttpException
import java.io.IOException


sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    companion object {
        suspend fun <T> handleResponse (data: suspend () -> T): Resource<T> {
            return try {
                Success(data())
            } catch (e: IOException) {
                e.printStackTrace()
                Error(e.message ?: "An unknown error")
            } catch (e: HttpException) {
                Error(e.message ?: "An unknown error")
            } catch (e: Exception) {
                Error(e.message ?: "An unknown error")
            }
        }
    }

}