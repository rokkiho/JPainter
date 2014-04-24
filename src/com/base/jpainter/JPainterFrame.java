package com.base.jpainter;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class JPainterFrame extends JFrame {
	private static final long serialVersionUID = -8970816856149396437L;
	
	private JPainterDrawPad drawPad;
	
	public JPainterFrame(int width, int height) {
		super();
		
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
	}

	public JPainterDrawPad getDrawPad() {
		return drawPad;
	}

	public void setDrawPad(JPainterDrawPad drawPad) {
		this.drawPad = drawPad;
		
		Container content = getContentPane();
		content.removeAll();
		content.setLayout(new BorderLayout());
		content.add(drawPad, BorderLayout.CENTER);
		
		drawPad.calculateShadow();
		drawPad.repaint();
	}
}
