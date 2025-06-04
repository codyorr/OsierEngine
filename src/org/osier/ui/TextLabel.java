package org.osier.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

public class TextLabel extends GUIObject {
    private String text, fontName;
    private Color textColor, textStrokeColor;
    private Font font;
    private BasicStroke textStroke;
    private int textSize, minTextSize, maxTextSize;
    private double alignX, alignY;
    private float textStrokeSize;
    private boolean textScaled, textWrapped, updateAlignment, updateFont, hasTextStroke;
    private Shape textOutline;
    private String[] lines;
    private FontMetrics metrics;
    private AffineTransform textTransform;

    public TextLabel() {
        super();
        name = "TextLabel";
        text = "Text Label";
        lines = text.split(" ");
        fontName = "Arial";
        textColor = Color.black;
        textStrokeColor = new Color(255,0,0);
        textSize = 16;
        font = new Font(fontName, Font.BOLD, textSize);
        alignX = 0.5;
        alignY = 0.5;
        textScaled = true;
        textWrapped = false;
        textOutline = null;
        updateAlignment = true;
        updateFont = true;
        minTextSize = 0;
        maxTextSize = 50;
        textStroke = new BasicStroke(0.1f);
        textStrokeSize = 0.1f;
        hasTextStroke = true;
        textTransform = new AffineTransform();
    }

    @Override
    public void render(Graphics2D g) {
		if(!visible || parent==null)return;
    	//g.translate(-g.getTransform().getTranslateX(), -g.getTransform().getTranslateY());
        g.rotate(rotationAngle, x + width / 2, y + height / 2);
        
        if(clipDescendants) {
        	g.setClip(clipShape);
        }
        
        
        
        g.setColor(backgroundColor);
		if(cornerRadius<1) {
			g.fillRect(x, y, width, height);
			g.setColor(borderColor);
			g.setStroke(borderStroke);
			g.drawRect(borderX, borderY, borderWidth, borderHeight);

		}else {
			g.fillRoundRect(x, y, width, height, cornerRadius, cornerRadius);
			g.setColor(borderColor);
			g.setStroke(borderStroke);
			g.drawRoundRect(borderX, borderY, borderWidth, borderHeight, cornerRadius, cornerRadius);

		}
		
        if(updateFont) {
            scaleFont(g);
            alignText(g);
        }
        else if(updateAlignment) {
            alignText(g);
        }

        g.setColor(textColor);
        g.fill(textOutline);
       
        if (hasTextStroke && textStrokeSize > 0) {
            g.setColor(textStrokeColor);
            g.setStroke(textStroke);
            g.draw(textOutline);
        }
        
        g.rotate(-rotationAngle, x + width / 2, y + height / 2);
        
		children.render(g);
		
		g.setClip(null);
    }

    @Override
    public void setSize(int ox, float sx, int oy, float sy) {
        super.setSize(ox, sx, oy, sy);
        updateFont = textScaled;
    }

    @Override
    public void setPosition(int ox, float sx, int oy, float sy) {
        super.setPosition(ox, sx, oy, sy);
        updateAlignment = true;
    }
    
    @Override
    public void setParent(BaseGUIObject p) {
    	super.setParent(p);
    	updateFont = true;
    }

    public void scaleFont(Graphics2D g) {
        metrics = g.getFontMetrics(font.deriveFont(Font.BOLD, maxTextSize));
        textSize = Math.max(minTextSize, Math.min(maxTextSize, (int) (maxTextSize *  Math.min((width - width*0.02) / metrics.stringWidth(text), (double) height / metrics.getHeight()))));
        font = font.deriveFont(Font.BOLD, textSize);
    }

    public void alignText(Graphics2D g) {
    	metrics = g.getFontMetrics(font);
        textTransform.setToTranslation(Math.floor(((x+(width*alignX)-originX-1)-( metrics.stringWidth(text)/2))/2+0.5)*2  , Math.floor(((y+(height*alignY)-originY-1)+(metrics.getAscent()/2))/2+0.5)*2);
        textOutline = new TextLayout(text, font, metrics.getFontRenderContext()).getOutline(textTransform);
    }
    
	/*textX = x+(width*alignX)-originX-1;
	textY = y+(height*alignY)-originY-1;
	textWidth = metrics.stringWidth(text)/2;
	textHeight = metrics.getAscent()/2;*/
    

    public void setAlignment(double x, double y) {
        alignX = x;
        alignY = y;
        if (textScaled) {
            updateFont = true;
        }
        updateAlignment = true;
    }

    public void setFont(String name) {
        fontName = name;
        font = new Font(fontName, Font.BOLD, textSize);
        updateFont = textScaled;
        updateAlignment = !textScaled;
    }

    public String getFont() {
        return fontName;
    }

    public void setText(String newText) {
        text = newText;
        updateFont = textScaled;
        updateAlignment = !textScaled;
    }

    public String getText() {
        return text;
    }

    public void setTextSize(int size) {
        if (!textScaled) {
            textSize = size;
            font = new Font(fontName, Font.BOLD, textSize);
            updateAlignment = true;
        }
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextColor(Color c) {
        textColor = c;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void enableTextStroke(boolean enable) {
        hasTextStroke = enable;
    }

    public void setTextStrokeColor(Color c) {
        textStrokeColor = c;
    }

    public Color getTextStrokeColor() {
        return textStrokeColor;
    }

    public void setTextStrokeSize(float size) {
        if (size <= 0) {
            textStroke = null;
            textStrokeSize = 0;
        } else {
            textStroke = new BasicStroke(size);
            textStrokeSize = size + 1;
        }
    }

    public float getTextStrokeSize() {
        return textStrokeSize - 1;
    }

    public void setTextWrapped(boolean wrapped) {
        textWrapped = wrapped;
        if (wrapped) {
            textScaled = true;
        }
    }

    public boolean isTextWrapped() {
        return textWrapped;
    }

    public void setTextScaled(boolean scaled) {
        if (textWrapped) return;
        textScaled = scaled;
        font = new Font(fontName, Font.BOLD, textSize);
        updateFont = scaled;
    }

    public boolean isTextScaled() {
        return textScaled;
    }

    public void setTextScaleRange(int min, int max) {
        minTextSize = min < 1 ? 1 : min;
        maxTextSize = max > 150 ? 150 : max;
    }

    public String[] getLines() {
        return lines;
    }
}