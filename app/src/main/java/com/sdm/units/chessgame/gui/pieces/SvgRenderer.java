package com.sdm.units.chessgame.gui.pieces;

import java.awt.image.BufferedImage;
import java.util.Optional;

@FunctionalInterface
public interface SvgRenderer {

    Optional<BufferedImage> render(String svgFilePath, float width, float height);
}