package com.base.jpainter;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class JPainterMenu extends JMenuBar {
	private static final long serialVersionUID = 8656846764409321643L;
	
	public JPainterMenu() {
		super();
		
		initFileMenu();
		initEditMenu();
	}
	
	private void initFileMenu() {
		JMenu file = new JMenu("File");
		
		JMenuItem newCanvas = new JMenuItem("New Canvas...");
		newCanvas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		// add action listener //
		file.add(newCanvas);
		
		JMenuItem openCanvas = new JMenuItem("Open Canvas...");
		openCanvas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		// add action listener //
		file.add(openCanvas);
		
		JMenuItem saveCanvas = new JMenuItem("Save Canvas...");
		saveCanvas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		// add action listener //
		file.add(saveCanvas);
		
		JMenuItem saveAsCanvas = new JMenuItem("Save Canvas As...");
		saveAsCanvas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		// add action listener //
		file.add(saveAsCanvas);
		
		file.addSeparator();
		
		JMenuItem exit = new JMenuItem("Exit");
		// add action listener //
		file.add(exit);
		
		add(file);
	}
	
	private void initEditMenu() {
		JMenu edit = new JMenu("Edit");
		
		add(edit);
	}
}
