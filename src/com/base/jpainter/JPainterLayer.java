package com.base.jpainter;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class JPainterLayer {
	private BufferedImage image;
	private Graphics2D graphics;
	
	private String name = "Layer";
	
	// layer settings //
	private float opacity = 1f;

	public JPainterLayer(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		graphics = image.createGraphics();
		JPainterDrawPad.applyQualitySettings(graphics);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Graphics2D getGraphics() {
		return graphics;
	}

	public void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
	
	public void drawLayer(Graphics2D g) {
		Composite originalComposite = g.getComposite();
		g.setComposite(makeComposite(opacity));
		g.drawImage(image, 0, 0, null);
		g.setComposite(originalComposite);
	}
	
	private AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return AlphaComposite.getInstance(type, alpha);
	}
}
