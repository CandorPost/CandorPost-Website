package io.github.candorpost.web.route

import io.github.candorpost.web.debugMode
import io.github.candorpost.web.logger
import io.javalin.Javalin

object DebugRouter {
	@JvmStatic
	fun accept(app: Javalin) {
		if (debugMode) {
			app.before {
				logger.info("Received request to ${it.fullUrl()}")
			}
		}
	}
}
