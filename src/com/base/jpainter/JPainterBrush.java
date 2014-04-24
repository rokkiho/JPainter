package com.base.jpainter;

import java.awt.BasicStroke;

public class JPainterBrush {
	/* brush dynamics */
	private float brushSize;
	private float flow;
	private float opacity;					/* not implemented yet */
	private BasicStroke stroke;
	
	private boolean pressureMode;

	public float getBrushSize() {
		return brushSize;
	}

	public void setBrushSize(float brushSize) {
		this.brushSize = brushSize;
	}

	public float getFlow() {
		return flow;
	}

	public void setFlow(float flow) {
		this.flow = flow;
	}

	public float getOpacity() {
		return opacity;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public BasicStroke getStroke() {
		return stroke;
	}

	public void setStroke(BasicStroke stroke) {
		this.stroke = stroke;
	}

	public boolean isPressureMode() {
		return pressureMode;
	}

	public void setPressureMode(boolean pressureMode) {
		this.pressureMode = pressureMode;
	}
	
}
