package io.github.candorpost.web

import io.github.candorpost.web.client.ControlPanel
import io.github.candorpost.web.resource.ResourceLoader
import io.github.candorpost.web.resource.StoryLoader
import io.github.candorpost.web.route.DebugRouter
import io.github.candorpost.web.route.ErrorRouter
import io.github.candorpost.web.route.StoryRouter
import io.github.candorpost.web.util.NOT_FOUND_404
import io.javalin.Javalin
import joptsimple.OptionParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.GraphicsEnvironment
import java.util.*

private lateinit var app: Javalin
val logger: Logger = LoggerFactory.getLogger("News")
var debugMode: Boolean = false

@Synchronized
fun getApp(): Javalin {
	return app
}

fun main(args: Array<String>) {
	val parser = OptionParser()
	val helpSpec = parser.accepts("help", "Shows this menu").forHelp()
	val guiSpec = parser.accepts("gui", "Shows the control panel")
	val portSpec = parser.accepts("port", "Sets the server port").withRequiredArg().defaultsTo("7000")
	val debugSpec = parser.accepts("debug", "Enabled verbose debug mode")
	val optionSet = parser.parse(*args)
	if (optionSet.has(helpSpec)) {
		parser.printHelpOn(System.out)
		return
	}
	if (optionSet.has(debugSpec)) {
		debugMode = true
	}
	if (optionSet.has(guiSpec) && !GraphicsEnvironment.isHeadless()) {
		Thread {
			ControlPanel()
		}.run()
	}
	val port = optionSet.valueOf(portSpec).toInt()
	ResourceLoader.addListener(StoryLoader)
	ResourceLoader.reload()
	app = Javalin.create()
	DebugRouter.accept(app)
	ErrorRouter.accept(app)
	StoryRouter.accept(app)
	app.start(port)
}
