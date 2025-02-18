package com.sdm.units.chessgame.gui;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;

import java.awt.image.BufferedImage;
import java.io.InputStream;

public class SvgImageUtils {

    public static BufferedImage renderSvgToImage(String svgFilePath, float width, float height) {
        final BufferedImage[] imageHolder = new BufferedImage[1]; // To hold the resulting image

        try {
            // Load the SVG file as an InputStream
            InputStream svgStream = SvgImageUtils.class.getClassLoader().getResourceAsStream(svgFilePath);
            if (svgStream == null) {
                throw new RuntimeException("SVG file not found: " + svgFilePath);
            }

            // Transcode the SVG to a BufferedImage
            TranscoderInput input = new TranscoderInput(svgStream);
            ImageTranscoder transcoder = new ImageTranscoder() {
                @Override
                public BufferedImage createImage(int width, int height) {
                    return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                }

                @Override
                public void writeImage(BufferedImage image, TranscoderOutput output) {
                    imageHolder[0] = image; // Store the transcoded image
                }
            };

            // Set transcoding hints (optional: adjust width/height as needed)
            transcoder.addTranscodingHint(ImageTranscoder.KEY_WIDTH, width);
            transcoder.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, height);

            // Perform the transcoding
            transcoder.transcode(input, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageHolder[0]; // Return the resulting BufferedImage
    }
}