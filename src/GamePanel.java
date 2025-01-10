import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * A panel where the flappy bird game will take place.
 */
public class GamePanel extends JPanel implements ActionListener, MouseListener {

    /**
     * The bird representing the player of the game.
     */
    Bird bird;

    /**
     * The ground that scrolls across the screen during gameplay.
     */
    Ground ground;

    /**
     * An array of Pipes representing the pipes currently in the game.
     */
    public static LinkedList<Pipe> pipes;

    /**
     * The player's score. Increases by one each time the bird successfully passes through a pipe.
     */
    private int score;

    /**
     * The background image.
     */
    private BufferedImage image;

    /**
     * The title image.
     */
    private BufferedImage titleImage;

    /**
     * An array of BufferedImages used to represent the player's score.
     */
    private BufferedImage[] numbers;

    /**
     * The state of the game.
     */
    private int state;

    public int getState() {
        return state;
    }

    public GamePanel() {
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setDoubleBuffered(true);

        bird = new Bird();

        ground = new Ground();

        state = Constants.STATE_INACTIVE;

        pipes = new LinkedList<>();

        score = 0;

        numbers = new BufferedImage[10];

        try {
            numbers[0] = ImageIO.read(new File("images/0.png"));
            numbers[1] = ImageIO.read(new File("images/1.png"));
            numbers[2] = ImageIO.read(new File("images/2.png"));
            numbers[3] = ImageIO.read(new File("images/3.png"));
            numbers[4] = ImageIO.read(new File("images/4.png"));
            numbers[5] = ImageIO.read(new File("images/5.png"));
            numbers[6] = ImageIO.read(new File("images/6.png"));
            numbers[7] = ImageIO.read(new File("images/7.png"));
            numbers[8] = ImageIO.read(new File("images/8.png"));
            numbers[9] = ImageIO.read(new File("images/9.png"));
        }
        catch (IOException e) {
            System.out.println("Error in opening number images");
        }
    }

    public void update() {
        if (state == Constants.STATE_INACTIVE) {
            bird.animate(Constants.FLAP_RATE_INACTIVE);
        }
        if (state == Constants.STATE_CREATE) {
            bird.animate(Constants.FLAP_RATE_INACTIVE);
            ground.moveGround();
            pipes.add(new Pipe());
            state = Constants.STATE_ACTIVE;
        }
        if (state == Constants.STATE_ACTIVE) {
            bird.animate(Constants.FLAP_RATE_ACTIVE);
            ground.moveGround();
            populatePipes();
            for (Pipe p : pipes) {
                p.movePipe();
                p.updateCollisionZones();
            }
            removePipes();
            bird.updateSpeedAndAngle();
            bird.updateCollisionZone();
            checkCollisions();
            countPoints();
        }
        if (state == Constants.STATE_PAUSED) {

        }
        if (state == Constants.STATE_OVER) {

        }
    }

    private void populatePipes() {
        if (pipes.getLast().horizontalPosition == Constants.NEW_PIPE_POS) {
            pipes.add(new Pipe());
        }
    }

    private void removePipes() {
        if (pipes.getFirst().horizontalPosition + Constants.PIPE_WIDTH <= 0) {
            pipes.poll();
        }
    }

    private void checkCollisions() {
        if (bird.getPosition() >= 584) {
            state = Constants.STATE_OVER;
        }
//        if (bird.collisionZone.getBounds().intersects(pipes.peekFirst().collisionZoneTop.getBounds())) {
//            state = Constants.STATE_OVER;
//        }
        if (bird.collisionZone.getBounds().intersects(pipes.peekFirst().collisionZoneBottom.getBounds())) {
            state = Constants.STATE_OVER;
        }
    }

    /**
     *
     */
    public void countPoints() {
        Pipe first = pipes.peekFirst();
        if (first.horizontalPosition <= 25 && first.horizontalPosition >= 22) {
            score += 1;
            System.out.println(score);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            image = ImageIO.read(new File("images/background.png"));
        }
        catch (IOException e) {
            System.out.println("Error in opening background image");
        }
        g.drawImage(image, 0, 0, this);

        try {
            titleImage = ImageIO.read(new File("images/title.png"));
        }
        catch (IOException e){
            System.out.println("Error in opening title image");
        }
        if (state == Constants.STATE_INACTIVE) {
            g.drawImage(titleImage, 0, 0, this);
        }

        if (state != Constants.STATE_INACTIVE && state != Constants.STATE_CREATE) {
            for (Pipe p : pipes) {
                p.draw(g, this);
            }
        }

        paintScore(g);

        ground.draw(g, this);
        Graphics2D g2d = (Graphics2D) g;
        bird.draw(g2d, this);
        g2d.dispose();
    }

    /**
     * Draws the player's score to the screen.
     */
    public void paintScore(Graphics g) {
        if (state != Constants.STATE_INACTIVE && state != Constants.STATE_CREATE) {
            int lastDigit = score % 10;
            g.drawImage(numbers[lastDigit], 50, 50, this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (state == Constants.STATE_INACTIVE) {
            state = Constants.STATE_CREATE;
        }
        bird.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
