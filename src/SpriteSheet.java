import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage sprite;

    public SpriteSheet(BufferedImage image) {
        this.sprite = image;
    }

    public BufferedImage grabImage(int col, int row, int width, int height) {
        BufferedImage img = sprite.getSubimage((row * 25) - 25, (col * 25) - 25, width - 1000, height - 500);
        return img;
    }
}
