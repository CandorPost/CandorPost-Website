package io.github.candorpost.web.resource

import io.github.candorpost.web.debugMode
import java.io.StringWriter
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

object StoryLoader: ReloadListener {
	val name2html: MutableMap<String, String> = HashMap()

	override fun reload(resourceDir: Path) {
		val storiesDir = resourceDir.resolve("stories")
		if (!Files.exists(storiesDir)) {
			return
		}
		if (!Files.isDirectory(storiesDir)) {
			Files.delete(storiesDir)
			Files.createDirectory(storiesDir)
			return
		}
		Files.list(storiesDir)
			.filter { it.toString().endsWith("md") }
			.forEach {
				val writer = StringWriter()
				markdown.transform(Files.newBufferedReader(it), writer)
				val str = it.toFile().nameWithoutExtension
				name2html.put(str, writer.toString())
				if (debugMode) {
					println("Loaded story $str in $it")
				}
			}
	}
}
