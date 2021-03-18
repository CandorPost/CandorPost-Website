package io.github.candorpost.web.route

import io.javalin.Javalin

object ErrorRouter {
	@JvmStatic
	fun accept(app: Javalin) {
		app.error(401) {
			it.html("<h1>401 Unauthorized</h1>")
		}
		app.error(403) {
			it.html("<h1>403 Forbidden</h1>")
		}
		app.error(404) {
			it.html("<h1>404 Not Found</h1>")
		}
	}
}
