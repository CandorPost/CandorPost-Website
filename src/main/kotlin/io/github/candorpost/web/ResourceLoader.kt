package io.github.candorpost.web

import org.tautua.markdownpapers.Markdown
import java.io.StringWriter
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object ResourceLoader {
	private val markdown: Markdown = Markdown()
	val name2html: MutableMap<String, String> = HashMap()

	@JvmStatic
	fun reload() {
		val storiesDir = Paths.get(".").resolve("stories")
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
