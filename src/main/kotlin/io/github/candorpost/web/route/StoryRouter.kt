package io.github.candorpost.web.route

import io.github.candorpost.web.resource.StoryLoader
import io.github.candorpost.web.util.NOT_FOUND_404
import io.javalin.Javalin
import java.util.*

object StoryRouter {
	@JvmStatic
	fun accept(app: Javalin) {
		app.get("/api/stories/:name") {
			val name = it.pathParam("name")
			val html = StoryLoader.name2html[name]
			Optional.ofNullable(html).ifPresentOrElse( { str -> it.html(str) }, { it.status(404) })
			if (html != null) it.html(html)
			else it.status(NOT_FOUND_404)
		}
	}
}
