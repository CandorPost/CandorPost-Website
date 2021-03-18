package io.github.candorpost.web.client

import io.github.candorpost.web.getApp
import io.github.candorpost.web.resource.ResourceLoader
import java.awt.Dimension
import java.awt.Font
import java.awt.Rectangle
import javax.swing.JButton
import javax.swing.JFrame
import kotlin.system.exitProcess

class ControlPanel : JFrame() {
	init {
		val stopButton = JButton()
		stopButton.text = "Stop"
		stopButton.font = Font("Arial", Font.BOLD, 24)
		stopButton.bounds = Rectangle(135,25,180,45)
		add(stopButton)
		val reloadButton = JButton()
		reloadButton.text = "Reload"
		reloadButton.font = Font("Arial", Font.BOLD, 24)
		reloadButton.bounds = Rectangle(135,75,180,45)
		add(reloadButton)
		title = "Control Panel"
		isResizable = false
		layout = null
		defaultCloseOperation = EXIT_ON_CLOSE
		isVisible = true
		size = Dimension(450, 300)
		stopButton.addActionListener {
			getApp().stop()
			stopButton.isEnabled = false
			exitProcess(0)
		}
		reloadButton.addActionListener {
			ResourceLoader.reload()
		}
	}
}
