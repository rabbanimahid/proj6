import java.awt.*;

public class BushSprite implements Sprite {
    private int x, y;

    public BushSprite(int x, int y) { this.x = x; this.y = y; }
    @Override public double getX() { return x; }
    @Override public double getY() { return y; }

    @Override
    public void draw(Graphics2D g, double drawX, double drawY, int w, int h) {
        g.setColor(new Color(0, 100, 0)); // Dark green
        g.fillOval((int)drawX+5, (int)drawY+5, w-10, h-10);
        g.fillOval((int)drawX, (int)drawY+15, w-20, h-20);
    }
}