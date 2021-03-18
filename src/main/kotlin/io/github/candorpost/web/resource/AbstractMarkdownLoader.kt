package io.github.candorpost.web.resource

import com.fasterxml.jackson.databind.ObjectReader
import io.github.candorpost.web.debugMode
import java.io.StringWriter
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists

abstract class AbstractMarkdownLoader(private val dirName: String, private val thingName: String): ReloadListener {
	val name2Entry: MutableMap<String, Entry> = HashMap()
	private val objectReader: ObjectReader = objectMapper.readerFor(Config::class.java)

	override fun reload(resourceDir: Path) {
		val storiesDir = resourceDir.resolve(dirName)
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
				val str = it.toFile().nameWithoutExtension
				val configPath = it.parent.resolve("$str.json")
				if (Files.exists(configPath)) {
					val config = objectReader.readValue<Config>(configPath.toFile())
					markdown.transform(Files.newBufferedReader(it), writer)
					name2Entry.put(str, Entry(str, config, writer.toString()))
					if (debugMode) {
						println("Loaded $thingName $str in $it")
					}
				} else {
					if (debugMode) {
						println("Found $thingName $str without config json in $it")
					}
				}
			}
	}

	data class Config(val category: String, val tags: List<String>, val heading: String)

	data class Entry(val name: String, val config: Config, val body: String)
}
