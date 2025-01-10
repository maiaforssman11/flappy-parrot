import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The main obstacle in the flappy bird game.
 */
public class Pipe {

    /**
     * Image that represents a pipe obstacle.
     */
    private BufferedImage image;

    /**
     * A rectangle representing the collision zone of the top of the pipe.
     */
    Polygon collisionZoneTop;

    /**
     * A rectangle representing the collision zone of the bottom of the pipe.
     */
    Polygon collisionZoneBottom;

    /**
     * The speed of the pipe.
     */
    private int SPEED = 3;

    /**
     * The vertical position of the pipe, determined using Math.random() to generate a random
     * position between a MIN and MAX value.
     */
    private int gapPosition;

    /**
     * The horizontal position of the pipe.
     */
    int horizontalPosition;

    /**
     * Constructs a new instance of a pipe.
     */
    public Pipe() {
        try {
            image = ImageIO.read(new File("images/pipe.png"));
        }
        catch (IOException e) {
            System.out.println("Error in opening pipe image");
        }

        int MIN = -400;
        int MAX = -200;
        gapPosition = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
        horizontalPosition = Constants.SCREEN_WIDTH + Constants.PIPE_WIDTH;

        collisionZoneTop = new Polygon();
        collisionZoneTop.addPoint(horizontalPosition + Constants.PIPE_WIDTH, Constants.PIPE_HEIGHT/2 + gapPosition - 75);
        collisionZoneTop.addPoint(horizontalPosition, Constants.PIPE_HEIGHT/2 + gapPosition - 75);
        collisionZoneTop.addPoint(horizontalPosition, -Constants.PIPE_HEIGHT/2 + gapPosition - 75);
        collisionZoneTop.addPoint(horizontalPosition + Constants.PIPE_WIDTH, -Constants.PIPE_HEIGHT/2 + gapPosition - 75);

        collisionZoneBottom = new Polygon();
        collisionZoneBottom.addPoint(horizontalPosition + Constants.PIPE_WIDTH, Constants.PIPE_HEIGHT/2 + gapPosition + 73); //// top right
        collisionZoneBottom.addPoint(horizontalPosition, Constants.PIPE_HEIGHT/2 + gapPosition + 73); //// top left
        collisionZoneBottom.addPoint(horizontalPosition, -(-Constants.PIPE_HEIGHT/2 + gapPosition + 73));
        collisionZoneBottom.addPoint(horizontalPosition + Constants.PIPE_WIDTH, -(-Constants.PIPE_HEIGHT/2 + gapPosition + 73));
    }

    public void movePipe() {
        this.horizontalPosition -= SPEED;
    }

    public void updateCollisionZones() {
        collisionZoneTop.reset();
        collisionZoneTop.addPoint(horizontalPosition + Constants.PIPE_WIDTH, Constants.PIPE_HEIGHT/2 + gapPosition - 75);
        collisionZoneTop.addPoint(horizontalPosition, Constants.PIPE_HEIGHT/2 + gapPosition - 75);
        collisionZoneTop.addPoint(horizontalPosition, -Constants.PIPE_HEIGHT/2 + gapPosition - 75);
        collisionZoneTop.addPoint(horizontalPosition + Constants.PIPE_WIDTH, -Constants.PIPE_HEIGHT/2 + gapPosition - 75);

        collisionZoneBottom.reset();
        collisionZoneBottom.addPoint(horizontalPosition + Constants.PIPE_WIDTH, Constants.PIPE_HEIGHT/2 + gapPosition + 73); //// top right
        collisionZoneBottom.addPoint(horizontalPosition, Constants.PIPE_HEIGHT/2 + gapPosition + 73); //// top left
        collisionZoneBottom.addPoint(horizontalPosition, -(-Constants.PIPE_HEIGHT/2 + gapPosition + 73));
        collisionZoneBottom.addPoint(horizontalPosition + Constants.PIPE_WIDTH, -(-Constants.PIPE_HEIGHT/2 + gapPosition + 73));
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(image, horizontalPosition, gapPosition, Constants.PIPE_WIDTH, Constants.PIPE_HEIGHT, observer);
        g.drawPolygon(collisionZoneTop);
        g.drawPolygon(collisionZoneBottom);
    }

}
