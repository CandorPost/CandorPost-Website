package io.github.candorpost.web.route

import io.github.candorpost.web.resource.SiteLoader
import io.github.candorpost.web.util.*
import io.javalin.Javalin

object MainRouter {
	@JvmStatic
	fun accept(app: Javalin) {
		app.get("/") {
			it.html(SiteLoader[MAIN_HTML]!!)
		}
		app.get("/site/**") {
			when {
				it.url().endsWith("css") -> {
					it.contentType(CSS)
				}
				it.url().endsWith("js") -> {
					it.contentType(JAVASCRIPT)
				}
				it.url().endsWith("html") -> {
					it.contentType(HTML)
				}
				else -> {
					it.contentType(PLAIN_TEXT)
				}
			}
			SiteLoader[it.req.pathInfo]?.let { str -> it.result(str) } ?: it.status(NOT_FOUND_404)
		}
	}
}
