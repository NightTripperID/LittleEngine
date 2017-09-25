package test.gamestates;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;
import gamestate.Intent;
import graphics.Screen;
import server.Server;
import test.mobs.MouseCursor;
import test.mobs.Pudgie;

public class LevelTwo extends GameState {

    private Pudgie pudgie;
    private MouseCursor cursor;
    private int count;

    public LevelTwo(@NotNull Server server) {
        super(server);
    }

    @Override
    public void onCreate() {

        pudgie = (Pudgie) getIntent().getSerializableExtra("pudgie");
        pudgie.initialize(this);

        cursor = (MouseCursor) getIntent().getSerializableExtra("cursor");
        cursor.initialize(this);
    }

    @Override
    public void onUpdate() {

        if(getKeyboard().enterPressed) {
            Intent intent = new Intent(PauseMenu.class);
            startGameState(intent);
        }

        pudgie.onUpdate();

        if(count++ == MAX_TICK) {
            Intent intent = new Intent(LevelOne.class);
            intent.putExtra("pudgie", pudgie);
            intent.putExtra("cursor", cursor);
            swapGameState(intent);
        }
    }

    @Override
    public void onRender(@NotNull Screen screen) {
        screen.renderString8x8(40, 40, 0x00ff00, "Level 2");
        pudgie.onRender(screen);

        cursor.onRender(screen);
    }

    @Override
    public void onDestroy() {

    }
}
