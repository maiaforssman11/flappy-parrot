import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The bird representing the player of the game.
 */
public class Bird {

    /**
     * Images representing the bird, cycles through these to make the flapping animation.
     */
    BufferedImage[] images;

    /**
     * Current image representing the bird.
     */
    private BufferedImage image;

    /**
     * A polygon representing the collision zone of the bird.
     */
    Ellipse2D collisionZone;

    /**
     * The vertical position of the bird.
     */
    private int position;

    /**
     * The velocity of the bird.
     */
    private double velocity;

    /**
     * The rotation angle of the bird.
     */
    public double angle;

    /**
     * The sprite counter, manages animations of the bird.
     */
    private int spriteCounter;

    /**
     * Constructs a new instance of a Bird.
     */
    public Bird() {

        images = new BufferedImage[3];

        try {
            images[0] = ImageIO.read(new File("images/parrotup.png"));
            images[1] = ImageIO.read(new File("images/parrotmid.png"));
            images[2] = ImageIO.read(new File("images/parrotdown.png"));
        }
        catch (IOException e) {
            System.out.println("Error in opening bird images");
        }

        image = images[0];

        position = Constants.START_POSITION;

        int radius = 21;
        collisionZone = new Ellipse2D.Double(-radius, -radius, 2 * radius, 2 * radius);

        velocity = 0;

        angle = 0;

        spriteCounter = 0;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int newPos) { this.position = newPos; }

    public void setVelocity(int newVelocity) { this.velocity = newVelocity; }

    public void setAngle(double newAngle) { this.angle = newAngle; }

    /**
     * Updates the speed and angle of the bird each frame based on the effect of gravity
     * (FALL_POWER) on the bird.
     */
    public void updateSpeedAndAngle() {
        position += (int) velocity;
        velocity += Constants.FALL_POWER;
        double newAngle;
        if (velocity > 10) {
            newAngle = 90;
        }
        else {
            newAngle = velocity * 8.25;
        }
        angle = newAngle;
    }

    /**
     * Cycles through the three bird images at a rate of flapRate to simulate its wings flapping.
     */
    public void animate(int flapRate) {
        spriteCounter++;
        if (spriteCounter > flapRate) {
            if (image == images[0]) {
                image = images[1];
            }
            else if (image == images[1]) {
                image = images[2];
            }
            else if (image == images[2]) {
                image = images[0];
            }
            spriteCounter = 0;
        }
    }

    /**
     * Moves the circular collision zone of the bird based on the bird's position (vertical).
     */
    public void updateCollisionZone() {

        collisionZone = new Ellipse2D.Double(80, position - 20, 40, 40);
    }

    /**
     * Draws the bird image to the screen.
     */
    public void draw(Graphics2D g2d, ImageObserver observer) {
        AffineTransform originalTransform = g2d.getTransform();
        g2d.translate(100, position);
        g2d.rotate(Math.toRadians(angle));
        g2d.drawImage(image, -image.getWidth()/2, -image.getHeight()/2, null);
        g2d.setTransform(originalTransform);
        g2d.dispose();
    }

    /**
     * Responds to e by resetting the bird's velocity to JUMP_POWER, causing the bird to flap
     * upwards.
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            velocity = Constants.JUMP_POWER;
        }
    }

}
