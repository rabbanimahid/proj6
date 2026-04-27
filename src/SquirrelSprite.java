import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class SquirrelSprite implements Sprite {
    private int x, y;
    private BufferedImage image;

    public SquirrelSprite(int x, int y) {
        this.x = x;
        this.y = y;

        try {
            File file = new File("squirrel.png");
            System.out.println("Looking for squirrel at: " + file.getAbsolutePath());
            image = ImageIO.read(file);
        } catch (Exception e) {
            System.out.println("Could not load squirrel.png");
        }
    }

    public void moveUp(int maxRows) {
        y -= 1;
        if (y < 0) {
            y = maxRows - 1;
        }
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void drawAnimated(Graphics2D g, double drawX, double drawY, int w, int h, int frame) {
        if (image != null) {
            g.drawImage(image, (int) drawX, (int) drawY, w, h, null);
        } else {
            g.setColor(new Color(139, 69, 19));
            g.fillRect((int) drawX + 10, (int) drawY + 10, w - 20, h - 20);
        }
    }

    @Override
    public void draw(Graphics2D g, double x, double y, int w, int h) {
        drawAnimated(g, x, y, w, h, 0);
    }
}