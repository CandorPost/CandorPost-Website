package io.github.candorpost.web.route

import io.github.candorpost.web.resource.StoryLoader
import io.github.candorpost.web.util.JSON
import io.github.candorpost.web.util.NOT_FOUND_404
import io.github.candorpost.web.util.createError
import io.javalin.Javalin
import java.util.*

object StoryRouter {
	@JvmStatic
	fun accept(app: Javalin) {
		app.get("/api/stories/:name") {
			val name = it.pathParam("name")
			val entry = StoryLoader.name2Entry[name]
			it.contentType(JSON)
			Optional.ofNullable(entry).ifPresentOrElse( { e -> it.json(e) }, {
				it.json(createError(NOT_FOUND_404, "Story $name not found"))
			})
		}
		app.get("/stories/:name") {
			val name = it.pathParam("name")
			val entry = StoryLoader.name2Entry[name]
			Optional.ofNullable(entry).ifPresentOrElse( { e -> it.html(e.body) }, { it.status(NOT_FOUND_404) })
		}
	}
}
