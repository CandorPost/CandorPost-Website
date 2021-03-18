package io.github.candorpost.web.client

import io.github.candorpost.web.getApp
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
		stopButton.bounds = Rectangle(135,65,180,90)
		add(stopButton)
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
	}
}
