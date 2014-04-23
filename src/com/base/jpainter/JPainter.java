package com.base.jpainter;

public class JPainter {
	public static void main(String[] args) {
		JPainterFrame mainFrame = new JPainterFrame(1024, 768);
		mainFrame.setDrawPad(new JPainterDrawPad(800, 600));
		mainFrame.setJMenuBar(new JPainterMenu());
		mainFrame.setTitle("JPainter - alpha");
	}
}
