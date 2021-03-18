package io.github.candorpost.web.util

data class Error internal constructor(val errorCode: Int, val message: String)

fun createError(errorCode: Int, message: String): Error {
	return Error(errorCode, message)
}
