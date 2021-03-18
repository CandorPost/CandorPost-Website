package io.github.candorpost.web.resource

import org.tautua.markdownpapers.Markdown
import java.nio.file.Path

interface ReloadListener {
	fun reload(resourceDir: Path)
}

val markdown: Markdown = Markdown()
