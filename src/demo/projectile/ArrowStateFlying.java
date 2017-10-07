package demo.projectile;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.MobState;
import gamestate.GameState;
import graphics.Screen;
import server.Server;

public class ArrowStateFlying extends ProjectileState {

    public ArrowStateFlying(@NotNull Mob mob, @NotNull GameState gameState) {
        super(mob, gameState);
    }

    @Override
    public MobState update() {

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        mob.x += mob.xa;
        mob.y += mob.ya;

        if (mob.x + mob.getWidth() - gameState.getScrollX() < 0
                || mob.x - gameState.getScrollX() > gameState.getScreenWidth()
                || mob.y + mob.getHeight() - gameState.getScrollY() < 0
                || mob.y - gameState.getScrollY() > gameState.getScreenHeight()) {

            gameState.removeEntity(mob);
        }

        return this;
    }
}
