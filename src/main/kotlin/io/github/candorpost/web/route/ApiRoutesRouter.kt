package io.github.candorpost.web.route

import io.github.candorpost.web.util.API_ROUTES
import io.github.candorpost.web.util.apiRoutesList
import io.javalin.Javalin

object ApiRoutesRouter {
	@JvmStatic
	fun accept(app: Javalin) {
		app.get(API_ROUTES) {
			it.json(apiRoutesList)
		}
	}
}
