import javax.swing.*;

/**
 * An application for playing flappy bird.
 */
public class App implements Runnable {

    /**
     * The application window.
     */
    private static JFrame window;

    /**
     * The game panel.
     */
    private static GamePanel gamePanel;

    /**
     * The game thread.
     */
    Thread gameThread;

    /**
     * The number of frames drawn per second by the game loop.
     */
    int FPS = 60;

    /**
     * Creates an instance of the Flappy Parrot game app.
     */
    public App() {
        initWindow();
        start();
    }

    /**
     * Initializes the game window.
     */
    private static void initWindow() {

        window = new JFrame("Flappy Parrot");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new GamePanel();
        window.add(gamePanel);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false);
        window.pack();
        window.addMouseListener(gamePanel);

    }

    /**
     * Handles the frame rate for the game and timing of updates.
     */
    public void run() {

        double updateInterval = 1000000000/FPS;
        double nextUpdateTime = System.nanoTime() + updateInterval;
        while (gameThread != null) {
            gamePanel.update();
            gamePanel.repaint();

            try {
                double remainingTime = (nextUpdateTime - System.nanoTime())/1000000;

                if (remainingTime < 0) {remainingTime = 0;}

                Thread.sleep((long) remainingTime);

                nextUpdateTime += updateInterval;
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop();
    }

    /**
     * Starts the game thread.
     */
    public synchronized void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Stops the game thread.
     */
    public synchronized void stop() {
        try {
            gameThread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Main program that runs the flappy parrot game app.
     */
    public static void main(String[] args) {
        new App();
    }
}
