package com.robingebert.stuggihaushalt.common.base

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.util.network.UnresolvedAddressException

abstract class BaseRepository {
    protected suspend fun <T> safeCall(action: suspend () -> T): Result<T> {
        return try {
            Result.success(action())
        } catch (e: UnresolvedAddressException) {
            Result.failure(Exception("Kann den Server nicht finden. Hast du Internet? Ist Tailscale an?"))
        } catch (e: ConnectTimeoutException){
            Result.failure(Exception("Wo Internet?"))
        }
        catch (e: Exception){
            println(e.message)
            Result.failure(Exception("Uaaa, sag mal Rö bescheid"))
        }
    }
}