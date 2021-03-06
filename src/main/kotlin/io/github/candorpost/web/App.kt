package io.github.candorpost.web

import io.github.candorpost.web.client.ControlPanel
import io.github.candorpost.web.resource.SiteLoader
import io.github.candorpost.web.resource.md.PostLoader
import io.github.candorpost.web.resource.ResourceLoader
import io.github.candorpost.web.resource.md.StoryLoader
import io.github.candorpost.web.resource.objectMapper
import io.github.candorpost.web.route.*
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import joptsimple.OptionParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.GraphicsEnvironment
import java.net.HttpURLConnection
import java.net.URL

private lateinit var app: Javalin
val logger: Logger = LoggerFactory.getLogger("CandorPost")
var debugMode: Boolean = false

@Synchronized
fun getApp(): Javalin {
	return app
}

fun main(args: Array<String>) {
	val parser = OptionParser()
	val helpSpec = parser.accepts("help", "Shows this menu").forHelp()
	val guiSpec = parser.accepts("gui", "Shows the control panel")
	val portSpec = parser.accepts("port", "Sets the server port").withRequiredArg().ofType(Int::class.java).defaultsTo(7000)
	val debugSpec = parser.accepts("debug", "Enabled verbose debug mode")
	val keepAliveSpec = parser.accepts("keepAlive", "Thread that calls the api every 59 seconds")
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
	val port = optionSet.valueOf(portSpec)
	if (optionSet.has(keepAliveSpec)) {
		Thread {
			val url = URL("http://localhost:$port/api/routes")
			while (true) {
				Thread.sleep(59 * 1000)
				val conn = (url.openConnection() as HttpURLConnection)
				conn.connect()
				conn.disconnect()
			}
		}.run()
	}
	JavalinJackson.configure(objectMapper)
	ResourceLoader.addListener(StoryLoader)
	ResourceLoader.addListener(PostLoader)
	ResourceLoader.addListener(SiteLoader)
	ResourceLoader.reload()
	app = Javalin.create()
	ApiRoutesRouter.accept(app)
	DebugRouter.accept(app)
	ErrorRouter.accept(app)
	MainRouter.accept(app)
	PostRouter.accept(app)
	StoryRouter.accept(app)
	app.start(port)
}
