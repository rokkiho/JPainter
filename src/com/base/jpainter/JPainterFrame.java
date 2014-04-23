package com.base.jpainter;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

public class JPainterFrame extends JFrame implements ComponentListener {
	private static final long serialVersionUID = -8970816856149396437L;
	
	private JPainterDrawPad drawPad;
	
	public JPainterFrame(int width, int height) {
		super();
		
		addComponentListener(this);
		
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

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		if(drawPad != null) {
			drawPad.repaint();
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		if(drawPad != null) {
			drawPad.repaint();
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		if(drawPad != null) {
			drawPad.repaint();
		}
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		if(drawPad != null) {
			drawPad.calculateShadow();
			drawPad.repaint();
		}
	}
}
