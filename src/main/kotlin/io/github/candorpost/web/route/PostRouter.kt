package io.github.candorpost.web.route

import io.github.candorpost.web.resource.md.PostLoader
import io.github.candorpost.web.util.*
import io.javalin.Javalin
import java.util.*

object PostRouter {
	@JvmStatic
	fun accept(app: Javalin) {
		app.get(API_CATEGORIES_POSTS) {
			it.json(PostLoader.getCategories())
		}
		app.get(API_POSTS_NAME) {
			val name = it.pathParam("name")
			val entry = PostLoader.name2Entry[name]
			it.contentType(JSON)
			Optional.ofNullable(entry).ifPresentOrElse( { e -> it.json(e) }, {
				it.json(createError(NOT_FOUND_404, "Post $name not found"))
				it.status(NOT_FOUND_404)
			})
		}
		app.get(API_POSTS) {
			it.json(PostLoader.name2Entry.keys)
		}
		app.get("/posts/:name") {
			val name = it.pathParam("name")
			val entry = PostLoader.name2Entry[name]
			Optional.ofNullable(entry).ifPresentOrElse( { e -> it.html(e.body) }, { it.status(NOT_FOUND_404) })
		}
	}
}
