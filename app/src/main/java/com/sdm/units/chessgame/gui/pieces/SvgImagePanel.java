package com.sdm.units.chessgame.gui.pieces;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class SvgImagePanel extends JComponent {
    
    private BufferedImage cachedImage;
    private final String svgPath;
    private final SvgRenderer renderer;
    private Dimension lastSize;

    public SvgImagePanel(String svgPath, SvgRenderer renderer) {
        this.svgPath = svgPath;
        this.renderer = renderer;
        setOpaque(false);
    }

    public String getSvgPath() {
        return svgPath;
    }

    public SvgRenderer getRenderer() {
        return renderer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension size = getSize();
        
        if (cachedImage == null || !size.equals(lastSize)) {
            cachedImage = renderer.render(svgPath, size.width, size.height).orElse(cachedImage);
            lastSize = size;
        }

        if (cachedImage != null) {
            g.drawImage(cachedImage, 0, 0, this);
        }
    }
}