package io.github.candorpost.web.resource

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.tautua.markdownpapers.Markdown
import java.nio.file.Path

interface ReloadListener {
	fun reload(resourceDir: Path)
}

val markdown: Markdown = Markdown()
val objectMapper: ObjectMapper = ObjectMapper().registerKotlinModule()
