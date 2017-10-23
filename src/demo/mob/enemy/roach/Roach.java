package demo.mob.enemy.roach;

import demo.mob.enemy.Enemy;
import demo.mob.treasure.YellowDoorkey;
import demo.spritesheets.SpriteSheets;
import demo.spritesheets.Sprites;
import entity.Entity;
import graphics.AnimSprite;
import graphics.Screen;

public class Roach extends Enemy {

    private AnimSprite roachUp = new AnimSprite(SpriteSheets.ROACH_UP, 16, 16, 4);
    private AnimSprite roachDown = new AnimSprite(SpriteSheets.ROACH_DOWN, 16, 16, 4);
    private AnimSprite roachLeft = new AnimSprite(SpriteSheets.ROACH_LEFT, 16, 16, 4);
    private AnimSprite roachRight = new AnimSprite(SpriteSheets.ROACH_RIGHT, 16, 16, 4);

    private int count;
    private static final int MAX_COUNT = 60 * 1;

    private static final int MOVE_SPEED = 3;

    public Roach(int col, double x, double y) {
        super(col, x, y, 1, 1, 16, 16, 3, 1, true);
        roachUp.setFrameRate(5);
        roachDown.setFrameRate(5);
        roachLeft.setFrameRate(5);
        roachRight.setFrameRate(5);
        setxSpeed(0);
        setySpeed(MOVE_SPEED);
        currSprite = roachDown;
    }

    @Override
    public void update() {
        super.update();
        currSprite.update();
        if(count++ == MAX_COUNT) {
            count = 0;
            switch(randomDirection()) {
                case UP:
                    setyDir(-1);
                    setxSpeed(0);
                    setySpeed(MOVE_SPEED);
                    currSprite = roachUp;
                    break;
                case DOWN:
                    setyDir(1);
                    setxSpeed(0);
                    setySpeed(MOVE_SPEED);
                    currSprite = roachDown;
                    break;
                case LEFT:
                    setxDir(-1);
                    setxSpeed(MOVE_SPEED);
                    setySpeed(0);
                    currSprite = roachLeft;
                    break;
                case RIGHT:
                    setxDir(1);
                    setxSpeed(MOVE_SPEED);
                    setySpeed(0);
                    currSprite = roachRight;
                    break;
            }
        }

        xa = getxSpeed() * getxDir();
        ya = getySpeed() * getyDir();

        commitMove(xa, ya);
    }

    private Direction randomDirection() {
        switch(random.nextInt(4)) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
            default:
                return Direction.UP;
        }
    }
}