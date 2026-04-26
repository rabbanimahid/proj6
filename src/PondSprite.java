import java.awt.*;

public class PondSprite implements Sprite {
    private int x, y;

    public PondSprite(int x, int y) { this.x = x; this.y = y; }
    @Override public double getX() { return x; }
    @Override public double getY() { return y; }

    public void drawAnimated(Graphics2D g, double drawX, double drawY, int w, int h, int frame) {
        g.setColor(Color.BLUE);
        g.fillOval((int)drawX, (int)drawY, w, h);

        // ANIMATION 3: Rippling Water
        g.setColor(Color.CYAN);
        int offset = (frame % 3) * 2;
        g.drawOval((int)drawX + 10 - offset, (int)drawY + 10 - offset, w - 20 + offset*2, h - 20 + offset*2);
    }

    @Override
    public void draw(Graphics2D g, double x, double y, int w, int h) { drawAnimated(g, x, y, w, h, 0); }
}