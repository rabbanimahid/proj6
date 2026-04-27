import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class DuckSprite implements Sprite {
    private int x, y;
    private BufferedImage image;

    public DuckSprite(int x, int y) {
        this.x = x;
        this.y = y;

        try {
            File file = new File("duck.png");
            System.out.println("Looking for duck at: " + file.getAbsolutePath());
            image = ImageIO.read(file);
        } catch (Exception e) {
            System.out.println("Could not load duck.png");
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
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
            g.setColor(Color.YELLOW);
            g.fillOval((int) drawX + 5, (int) drawY + 5, w - 10, h - 10);
        }
    }

    @Override
    public void draw(Graphics2D g, double x, double y, int w, int h) {
        drawAnimated(g, x, y, w, h, 0);
    }
}