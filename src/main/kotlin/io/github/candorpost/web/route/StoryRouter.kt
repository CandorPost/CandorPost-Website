package io.github.candorpost.web.route

import io.github.candorpost.web.resource.md.StoryLoader
import io.github.candorpost.web.util.*
import io.javalin.Javalin
import java.util.*

object StoryRouter {
	@JvmStatic
	fun accept(app: Javalin) {
		app.get(API_CATEGORIES_STORIES) {
			it.json(StoryLoader.getCategories())
		}
		app.get(API_STORIES_NAME) {
			val name = it.pathParam("name")
			val entry = StoryLoader.name2Entry[name]
			it.contentType(JSON)
			Optional.ofNullable(entry).ifPresentOrElse( { e -> it.json(e) }, {
				it.json(createError(NOT_FOUND_404, "Story $name not found"))
			})
		}
		app.get(API_STORIES) {
			it.json(StoryLoader.name2Entry.keys)
		}
		app.get("/stories/:name") {
			val name = it.pathParam("name")
			val entry = StoryLoader.name2Entry[name]
			Optional.ofNullable(entry).ifPresentOrElse( { e -> it.html(e.body) }, { it.status(NOT_FOUND_404) })
		}
	}
}
