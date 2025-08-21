package com.sdm.units.chessgame.gui.pieces;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Optional;

public class BatikSvgRenderer implements SvgRenderer {

    @Override
    public Optional<BufferedImage> render(String svgFilePath, float width, float height) {
        final BufferedImage[] imageHolder = new BufferedImage[1];

        try (InputStream svgStream = BatikSvgRenderer.class.getClassLoader().getResourceAsStream(svgFilePath)) {
            if (svgStream == null) {
                throw new IllegalArgumentException("SVG file not found: " + svgFilePath);
            }

            TranscoderInput input = new TranscoderInput(svgStream);
            ImageTranscoder transcoder = new ImageTranscoder() {
                @Override
                public BufferedImage createImage(int width, int height) {
                    return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                }

                @Override
                public void writeImage(BufferedImage image, TranscoderOutput output) {
                    imageHolder[0] = image;
                }
            };

            transcoder.addTranscodingHint(ImageTranscoder.KEY_WIDTH, width);
            transcoder.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, height);
            transcoder.transcode(input, null);

        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.ofNullable(imageHolder[0]);
    }
}