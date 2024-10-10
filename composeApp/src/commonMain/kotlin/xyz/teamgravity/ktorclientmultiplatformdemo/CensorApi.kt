package xyz.teamgravity.ktorclientmultiplatformdemo

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException

class CensorApi(
    private val client: HttpClient
) {

    ///////////////////////////////////////////////////////////////////////////
    // GET
    ///////////////////////////////////////////////////////////////////////////

    suspend fun getCensoredText(text: String): Result<String, NetworkError> {
        return withContext(Dispatchers.IO) {
            val response = try {
                client.get(
                    urlString = "https://www.purgomalum.com/service/json",
                    block = {
                        parameter(
                            key = "text",
                            value = text
                        )
                    }
                )
            } catch (e: UnresolvedAddressException) {
                return@withContext Result.Error(NetworkError.NoInternet)
            } catch (e: SerializationException) {
                return@withContext Result.Error(NetworkError.Serialization)
            }

            return@withContext when (response.status.value) {
                in 200..299 -> Result.Success(response.body<CensoredTextDto>().result)
                401 -> Result.Error(NetworkError.Unauthorized)
                408 -> Result.Error(NetworkError.RequestTimeout)
                409 -> Result.Error(NetworkError.Conflict)
                413 -> Result.Error(NetworkError.PayloadTooLarge)
                in 500..599 -> Result.Error(NetworkError.Server)
                else -> Result.Error(NetworkError.Unknown)
            }
        }
    }
}