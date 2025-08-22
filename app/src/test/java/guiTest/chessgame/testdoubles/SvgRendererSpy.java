package guitest.chessgame.testdoubles;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gui.pieces.SvgRenderer;

public class SvgRendererSpy implements SvgRenderer {
    
    private record Call(String svgFilePath, float width, float height) {}
    private final List<Call> calls = new ArrayList<>();
    
    private Optional<BufferedImage> imageToReturn = Optional.of(
        new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB)
    );

    @Override
    public Optional<BufferedImage> render(String svgFilePath, float width, float height) {
        calls.add(new Call(svgFilePath, width, height));
        return imageToReturn;
    }

    public boolean wasCalledWith(String path, float width, float height) {
        return calls.contains(new Call(path, width, height));
    }

    public int callsSize() {
        return calls.size();
    }

    public void setImageToReturn(Optional<BufferedImage> image) {
        this.imageToReturn = image;
    }
}