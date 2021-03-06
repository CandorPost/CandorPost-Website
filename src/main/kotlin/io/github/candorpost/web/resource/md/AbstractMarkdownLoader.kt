package io.github.candorpost.web.resource.md

import com.fasterxml.jackson.databind.ObjectReader
import io.github.candorpost.web.debugMode
import io.github.candorpost.web.logger
import io.github.candorpost.web.resource.ReloadListener
import io.github.candorpost.web.resource.markdown
import io.github.candorpost.web.resource.objectMapper
import java.io.StringWriter
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap
import kotlin.streams.toList

abstract class AbstractMarkdownLoader(private val dirName: String, private val thingName: String): ReloadListener {
	val name2Entry: MutableMap<String, Entry> = ConcurrentHashMap()
	private val objectReader: ObjectReader = objectMapper.readerFor(Config::class.java)

	override fun reload(resourceDir: Path) {
		name2Entry.clear()
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
			.filter { Files.isDirectory(it) }
			.forEach {
				val writer = StringWriter()
				val str = it.toFile().nameWithoutExtension
				val configPath = it.resolve("$str.json")
				val mdPath = it.resolve("$str.md")
				if (Files.exists(configPath) && Files.exists(mdPath)) {
					val config = objectReader.readValue<Config>(configPath.toFile())
					markdown.transform(Files.newBufferedReader(mdPath), writer)
					name2Entry.put(str, Entry(str, config, writer.toString()))
					if (debugMode) {
						logger.info("Loaded $thingName $str in $it")
					}
				} else {
					if (debugMode) {
						logger.warn("Missing files in $it")
					}
				}
			}
	}

	fun getCategories(): List<String> {
		return this.name2Entry.values.stream().map { it.config.category }.toList()
	}

	data class Config(val category: String, val tags: List<String>, val heading: String)

	data class Entry(val name: String, val config: Config, val body: String)
}
