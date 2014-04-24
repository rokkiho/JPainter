package com.base.jpainter;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import jpen.PKind;
import jpen.PLevel;
import jpen.PLevelEvent;
import jpen.PenManager;
import jpen.event.PenAdapter;
import jpen.owner.multiAwt.AwtPenToolkit;

import com.base.jpainter.utils.GaussianFilter;
import com.base.jpainter.utils.GraphicsUtils;

public class JPainterDrawPad extends JPanel {
	private static final long serialVersionUID = 826649619254544075L;
	
	private int width;
	private int height;
	
	private List<JPainterLayer> layers;
	private JPainterLayer activeLayer;
	
	private final Insets insets = new Insets(0, 0, 10, 10);
	
	private int shadowSize = 5;
	private Rectangle2D shape;
	private BufferedImage shadow;
	
	private Point2D.Float prevLoc = new Point2D.Float();
	private Point2D.Float loc = new Point2D.Float();
	
	// brush dynamics //
	private float brushSize;
	private float flow;
	private float opacity;
	private BasicStroke stroke;
	
	public JPainterDrawPad(int width, int height) {
		setBounds(0, 0, width, height);
		
		this.width = width + insets.right;
		this.height = height + insets.bottom;
		
		layers = new ArrayList<JPainterLayer>();
		addLayer(new JPainterLayer(width, height));
		activeLayer = layers.get(0);
		
		setDoubleBuffered(false);
		setOpaque(false);
		setBackground(Color.white);
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		
		AwtPenToolkit.addPenListener(this, new PenAdapter() {
			@Override
			public void penLevelEvent(PLevelEvent ev) {
				if(!ev.isMovement() || activeLayer == null)
					return;
				
				Graphics2D g2d = activeLayer.getGraphics();
				
				float pressure = ev.pen.getLevelValue(PLevel.Type.PRESSURE);
				brushSize = pressure * 10;
				flow = 255 - pressure * 255;
				opacity = pressure;
				
				loc.x = ev.pen.getLevelValue(PLevel.Type.X);
				loc.y = ev.pen.getLevelValue(PLevel.Type.Y);
				
				if(brushSize > 0) {
					if(ev.pen.getKind() == PKind.valueOf(PKind.Type.ERASER)) {
						g2d.setColor(Color.white);
						stroke = new BasicStroke(brushSize * 2);
					}
					else {
						g2d.setColor(new Color((int)flow, (int)flow, (int)flow, 255));
						stroke = new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
					}
					Composite orginalComposite = g2d.getComposite();
					g2d.setComposite(GraphicsUtils.makeComposite(opacity));
					g2d.setStroke(stroke);
					g2d.draw(new Line2D.Float(prevLoc, loc));
					g2d.setComposite(orginalComposite);
				}
				
				prevLoc.setLocation(loc);
				
				repaint();
			}
		});
		PenManager pm = AwtPenToolkit.getPenManager();
		pm.pen.levelEmulator.setPressureTriggerForLeftCursorButton(1f);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public Insets getInsets() {
		return insets;
	}
	
	public void addLayer(JPainterLayer layer) {
		layers.add(layer);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(shadow, shadowSize, shadowSize, this);
		
		g2d.setColor(getBackground());
		g2d.fill(shape);
		
		getUI().paint(g2d, this);
		
		g2d.setColor(Color.gray);
		g2d.draw(shape);
		
		// draw layers
		for(JPainterLayer layer : layers)
			layer.drawLayer(g2d);
		
		g.dispose();
	}
	
	public void calculateShadow() {
		int width = getWidth() - 1;
		int height = getHeight() - 1;
		
		Graphics g = getGraphics();
		
		if(g == null) {
			return;
		}
		
		Graphics2D g2d = (Graphics2D) g;
		GraphicsUtils.applyQualitySettings(g2d);
		Insets insets = getInsets();
		Rectangle bounds = getBounds();
		bounds.x = insets.left;
		bounds.y = insets.top;
		bounds.width = width - (insets.left + insets.right);
		bounds.height = height - (insets.top + insets.bottom);
		
		// draw shadow
		shape = new Rectangle2D.Float(bounds.x, bounds.y, bounds.width, bounds.height);
		BufferedImage img = createCompatibleImage(bounds.width, bounds.height);
		Graphics2D tg2d = img.createGraphics();
		GraphicsUtils.applyQualitySettings(tg2d);
		tg2d.setColor(Color.BLACK);
		tg2d.translate(-bounds.x, -bounds.y);
		tg2d.fill(shape);
		tg2d.dispose();
		shadow = generateShadow(img, shadowSize, Color.BLACK, 0.5f);
		
		repaint();
	}
	
	private BufferedImage createCompatibleImage(int width, int height) {
		return createCompatibleImage(width, height, Transparency.TRANSLUCENT);
	}
	
	private BufferedImage createCompatibleImage(int width, int height, int transparency) {
		BufferedImage image = getGraphicsConfiguration().createCompatibleImage(width, height, transparency);
		image.coerceData(true);
		return image;
	}
	
	private BufferedImage generateShadow(BufferedImage image, int size, Color color, float alpha) {
		int imageWidth = image.getWidth() + (size * 2);
		int imageHeight = image.getHeight() + (size * 2);
		
		BufferedImage imageMask = createCompatibleImage(imageWidth, imageHeight);
		Graphics2D g2d = imageMask.createGraphics();
		GraphicsUtils.applyQualitySettings(g2d);
		
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		
		BufferedImage imageShadow = generateBlur(imageMask, size, color, alpha);
		
		return imageShadow;
	}
	
	private BufferedImage generateBlur(BufferedImage image, int size, Color color, float alpha) {
		GaussianFilter filter = new GaussianFilter(size);
		
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		
		BufferedImage imageBlur = createCompatibleImage(imageWidth, imageHeight);
		Graphics2D g2d = imageBlur.createGraphics();
		GraphicsUtils.applyQualitySettings(g2d);
		
		g2d.drawImage(image, 0, 0, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
		g2d.setColor(color);
		
		g2d.fillRect(0, 0, imageWidth, imageHeight);
		g2d.dispose();
		
		imageBlur = filter.filter(imageBlur, null);
		
		return imageBlur;
	}
}
