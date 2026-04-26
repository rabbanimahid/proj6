import java.awt.*;

public class BreadcrumbSprite implements Sprite {
    private int x, y;

    public BreadcrumbSprite(int x, int y) { this.x = x; this.y = y; }
    @Override public double getX() { return x; }
    @Override public double getY() { return y; }

    public void drawAnimated(Graphics2D g, double drawX, double drawY, int w, int h, int frame) {
        // ANIMATION 4: Glowing/Pulsing Item
        if (frame % 2 == 0) {
            g.setColor(Color.WHITE);
            g.fillOval((int)drawX + w/2 - 6, (int)drawY + h/2 - 6, 12, 12);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.fillOval((int)drawX + w/2 - 4, (int)drawY + h/2 - 4, 8, 8);
        }
    }

    @Override
    public void draw(Graphics2D g, double x, double y, int w, int h) { drawAnimated(g, x, y, w, h, 0); }
}