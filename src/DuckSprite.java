import java.awt.*;

public class DuckSprite implements Sprite {
    private int x, y;

    public DuckSprite(int x, int y) { this.x = x; this.y = y; }
    public void setPosition(int x, int y) { this.x = x; this.y = y; }
    @Override public double getX() { return x; }
    @Override public double getY() { return y; }

    public void drawAnimated(Graphics2D g, double drawX, double drawY, int w, int h, int frame) {
        g.setColor(Color.YELLOW);
        g.fillOval((int)drawX + 5, (int)drawY + 5, w - 10, h - 10);

        // ANIMATION 1: Flapping Beak
        g.setColor(Color.ORANGE);
        if (frame % 2 == 0) {
            g.fillPolygon(new int[]{(int)drawX+w-15, (int)drawX+w, (int)drawX+w-15},
                    new int[]{(int)drawY+15, (int)drawY+20, (int)drawY+25}, 3);
        } else {
            g.fillPolygon(new int[]{(int)drawX+w-15, (int)drawX+w, (int)drawX+w-15},
                    new int[]{(int)drawY+20, (int)drawY+25, (int)drawY+30}, 3);
        }
    }

    @Override
    public void draw(Graphics2D g, double x, double y, int w, int h) { drawAnimated(g, x, y, w, h, 0); }
}