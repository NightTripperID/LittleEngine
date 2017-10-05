package demo.player;

import demo.mob.Mob;
import demo.mob.MobState;
import demo.spritesheets.AnimSprites;
import demo.spritesheets.SpriteSheets;
import entity.MouseInteractive;
import gamestate.GameState;
import graphics.AnimSprite;
import graphics.Screen;

import java.awt.event.MouseEvent;
import java.io.Serializable;

public class Player extends Mob implements MouseInteractive {

    public Player(int x, int y) {
        super(x, y, 1, 1, 16, 16);
    }

    @Override
    public void initialize(GameState gameState) {
        super.initialize(gameState);
        currSprite = AnimSprites.PLAYER_DOWN;
        currState = new PlayerStateStanding(this);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x, y, getWidth(), getHeight(), currSprite.getSprite().pixels);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currState = currState.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currState = currState.mouseReleased(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currState = currState.mouseDragged(e);
    }
}
