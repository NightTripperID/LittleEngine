package demo.mob.treasure;

import demo.area.Area;
import demo.mob.Mob;
import demo.mob.player.Player;
import demo.spritesheets.SpriteSheets;
import graphics.AnimSprite;
import graphics.Screen;

public class Potion extends Mob {

    public Potion(int col, double x, double y) {
        super(col, x, y, 1, 1, 8, 8, 2, 0, true, false);

        currSprite = new AnimSprite(SpriteSheets.POTION, getWidth(), getHeight(), 1);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
    }

    @Override
    public void runCollision(Mob mob) {

        if(mob instanceof Player) {
            Player player = (Player) mob;
            if(player.inventory.getCount("potion") < Player.MAX_POTIONS) {
                player.inventory.add("potion");
                ((Area) gameState).setMobSpawn((int) x, (int) y, 0xff00ff);
                this.setRemoved(true);
            }
        }
    }
}
