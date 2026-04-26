import java.awt.*;

public class SquirrelSprite implements Sprite {
    private int x, y;

    public SquirrelSprite(int x, int y) { this.x = x; this.y = y; }

    // Moves strictly vertically and wraps, satisfying HW06 rules
    public void moveUp(int maxRows) {
        y -= 1;
        if (y < 0) y = maxRows - 1;
    }

    @Override public double getX() { return x; }
    @Override public double getY() { return y; }

    public void drawAnimated(Graphics2D g, double drawX, double drawY, int w, int h, int frame) {
        g.setColor(new Color(139, 69, 19)); // Brown
        g.fillRect((int)drawX + 10, (int)drawY + 10, w - 20, h - 20);

        // ANIMATION 2: Wagging Tail
        g.setColor(Color.DARK_GRAY);
        if (frame % 2 == 0) {
            g.fillOval((int)drawX, (int)drawY + 10, 15, 30);
        } else {
            g.fillOval((int)drawX - 5, (int)drawY + 15, 15, 30);
        }
    }

    @Override
    public void draw(Graphics2D g, double x, double y, int w, int h) { drawAnimated(g, x, y, w, h, 0); }
}