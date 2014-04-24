package com.base.jpainter;

public class JPainter {
	public static void main(String[] args) {
		JPainterFrame mainFrame = new JPainterFrame(1024, 768);
		mainFrame.setJMenuBar(new JPainterMenu());
		mainFrame.setTitle("JPainter - alpha");
		mainFrame.setDrawPad(new JPainterDrawPad(800, 600));
	}
}
