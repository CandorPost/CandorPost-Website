package io.github.candorpost.web.resource

import io.javalin.plugin.rendering.vue.readText
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

object SiteLoader: ReloadListener {
	private val name2html: MutableMap<String, String> = HashMap()

	override fun reload(resourceDir: Path) {
		name2html.clear()
		Files.walk(resourceDir.resolve("site"), Integer.MAX_VALUE)
			.filter { !Files.isDirectory(it) }
			.forEach {
				val str = it.toString().substring(resourceDir.toString().length)
				println(str)
				name2html.put(str, it.readText())
			}
	}

	operator fun get(string: String): String? {
		return name2html[string]
	}
}
