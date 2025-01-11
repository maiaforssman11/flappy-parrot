public class Constants {
    /**
     * Game states.
     */
    public static final int STATE_INACTIVE = 1;
    public static final int STATE_CREATE = 2;
    public static final int STATE_ACTIVE = 3;
    public static final int STATE_OVER = 4;

    /**
     * Constants for screen dimensions.
     */
    public static final int SCREEN_WIDTH = 432;
    public static final int SCREEN_HEIGHT = 768;

    /**
     * Constants for ground dimensions.
     */
    public static final int GROUND_WIDTH = 432;
    public static final int GROUND_HEIGHT = 156;
    public static final int GROUND_SPEED = 3;

    /**
     * Constants for bird dimensions/characteristics.
     */
    public static final int BIRD_WIDTH = 51;
    public static final int BIRD_HEIGHT = 36;
    public static final int START_POSITION = 350;

    /**
     * Constants for pipe dimensions.
     */
    public static final int PIPE_WIDTH = 75;
    public static final int PIPE_HEIGHT = 1350;

    /**
     * Horizontal position at which to add a new pipe.
     */
    public static final int NEW_PIPE_POS = 264;

    /**
     * Constants for flapping physics.
     */
    public static final double JUMP_POWER = -6;
    public static final double FALL_POWER = .3;

    /**
     * Constants for the flap rate of the bird.
     */
    public static final int FLAP_RATE_INACTIVE = 7;
    public static final int FLAP_RATE_ACTIVE = 3;

}
