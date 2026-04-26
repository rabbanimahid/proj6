import java.awt.*;

/**
 * Interface Sprite
 * Default methods to implement 2d sprite objects
 *
 * @author Purdue CS
 * @version April 10, 2026
 */

public interface Sprite {

    // Draw method -> allows inheritors to call draw
    void draw(Graphics2D g, double x, double y, int frameW, int frameH);

    // Allows inheritors to have getX and Y methods
    double getX();

    double getY();
}
