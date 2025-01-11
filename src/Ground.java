import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The ground in the flappy bird game, which scrolls across the screen as the game is being played.
 */
public class Ground {

    /**
     * The image for the ground
     */
    private BufferedImage image;

    /**
     * The current position of the first ground image.
     */
    private int pos1;

    /**
     * The current position of the second ground image.
     */
    private int pos2;

    /**
     * Constructs a new instance of a ground object.
     */
    public Ground() {
        try {
            this.image = ImageIO.read(new File("images/ground.png"));
        }
        catch (IOException e) {
            System.out.println("Error in opening ground image");
        }
        pos1 = 0;
        pos2 = Constants.GROUND_WIDTH;
    }

    /**
     * Moves the positions of the two ground images GROUND_SPEED to the left every frame for a
     * seamless scrolling effect.
     */
    public void moveGround() {
        this.pos1 -= Constants.GROUND_SPEED;
        this.pos2 -= Constants.GROUND_SPEED;

        if (pos1 <= -Constants.GROUND_WIDTH) {
            pos1 = pos2 + Constants.GROUND_WIDTH;
        }
        if (pos2 <= -Constants.GROUND_WIDTH) {
            pos2 = pos1 + Constants.GROUND_WIDTH;
        }
    }

    /**
     * Draws the ground images.
     */
    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(image, pos1, Constants.SCREEN_HEIGHT - Constants.GROUND_HEIGHT, observer);

        g.drawImage(image, pos2, Constants.SCREEN_HEIGHT - Constants.GROUND_HEIGHT, observer);

    }

}
