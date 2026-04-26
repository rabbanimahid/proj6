import java.awt.*;

public class WallSprite implements Sprite {
    private int x, y;

    public WallSprite(int x, int y) { this.x = x; this.y = y; }
    @Override public double getX() { return x; }
    @Override public double getY() { return y; }

    @Override
    public void draw(Graphics2D g, double drawX, double drawY, int w, int h) {
        g.setColor(Color.GRAY);
        g.fillRect((int)drawX, (int)drawY, w, h);
        g.setColor(Color.BLACK);
        g.drawRect((int)drawX, (int)drawY, w, h);
    }
}