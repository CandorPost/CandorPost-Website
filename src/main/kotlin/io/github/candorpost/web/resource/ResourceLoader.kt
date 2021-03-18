package io.github.candorpost.web.resource

import java.nio.file.Paths
import java.util.*

object ResourceLoader {
	private val listeners: MutableList<ReloadListener> = ArrayList()

	fun addListener(listener: ReloadListener) {
		listeners.add(listener)
	}

	@JvmStatic
	fun reload() {
		val resourceDir = Paths.get(".").resolve("Pages")
		listeners.forEach { it.reload(resourceDir) }
	}
}
