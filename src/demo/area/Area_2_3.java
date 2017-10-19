package demo.area;

import demo.mob.player.Player;
import demo.overlay.Overlay;
import demo.tile.DemoTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import gamestate.Bundle;
import gamestate.Intent;
import server.Server;

public class Area_2_3 extends Area_2 {

    private static boolean cached;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        initMap(11, 7, Tile.TileSize.X16);

        if (!cached) {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/map_2-3.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/triggermap_2-3.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/spawnmap_2-3.png");
            cached = true;
        } else {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/map_2-3.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_2-3.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_2-3.png");
        }

        Bundle inBundle = (Bundle) getIntent().getSerializableExtra("bundle");
        Player player = (Player) inBundle.getSerializableExtra("player");
        TileCoord tileCoord = (TileCoord) inBundle.getSerializableExtra("tileCoord");
        player.x = tileCoord.getX();
        player.y = tileCoord.getY();
        player.initialize(this);
        addEntity(player);
        setOverlay(new Overlay(player));

        setScrollX((int) player.x - getScreenWidth() / 2);
        setScrollY((int) player.y - getScreenHeight() / 2);

        putTrigger(0xffff0000, () -> { // red
            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(27, 5, DemoTile.SIZE));
            bundle.putExtra("player", player);
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("pixels", getScreenPixels());
            intent.putExtra("nextGameState", Area_2_1.class);
            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xff00ff00, () -> { // green
            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(1, 9, DemoTile.SIZE));
            bundle.putExtra("player", player);
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("pixels", getScreenPixels());
            intent.putExtra("nextGameState", Area_2_4.class);
            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xff0000ff, () -> { // blue
            if(player.inventory.remove("doorkey")) {
                setMapTile(5, 1, 0xffb200ff);
                setTriggerTile(5, 1, 0xff00ffff);
            }
        });

        putTrigger(0xff00ffff, () -> { // cyan
            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(5, 13, DemoTile.SIZE));
            bundle.putExtra("player", player);
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("pixels", getScreenPixels());
            intent.putExtra("nextGameState", Area_2_5.class);
            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/map_2-3.png");
        pixelsToPNG(getTriggerTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_2-3.png");
        pixelsToPNG(getMobTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_2-3.png");
    }
}
