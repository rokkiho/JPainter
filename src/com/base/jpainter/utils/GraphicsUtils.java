package com.base.jpainter.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class GraphicsUtils {
	public static void applyQualitySettings(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	}
	
	public static AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return AlphaComposite.getInstance(type, alpha);
	}
}
