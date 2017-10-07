package demo.player;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.MobState;
import demo.projectile.Arrow;
import demo.spritesheets.SpriteSheets;
import gamestate.GameState;
import graphics.AnimSprite;
import input.Keyboard;
import input.Mouse;

abstract class PlayerState extends MobState {

    Keyboard keyboard;

    private static final int ATTACK_RATE = 1 * 30;

    int count = ATTACK_RATE;

    PlayerState(@NotNull Mob mob, @NotNull GameState gameState) {
        super(mob, gameState);
        keyboard = gameState.getKeyboard();
    }

    PlayerState(@NotNull Mob mob, @NotNull GameState gameState, int count) {
        this(mob, gameState);
        this.count = count;
    }

    @Override
    public MobState update() {

        if(++count < ATTACK_RATE)
            return this;

        count = 0;

        if(Mouse.button1) {


            int mouseX = Mouse.mouseX + (int) gameState.getScrollX();
            int mouseY = Mouse.mouseY + (int) gameState.getScrollY();

            double dx = mouseX - mob.x;
            double dy = mouseY - mob.y;

            System.out.println(mouseX + ", " + mob.x);

            double angle = Math.atan2(dy, dx);

            Arrow arrow = new Arrow(mob.x, mob.y, angle);
            arrow.initialize(gameState);
            gameState.addEntity(arrow);
        }
        return this;
    }
}
