package demo.area;

import demo.tile.DemoTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import gamestate.Bundle;
import gamestate.Intent;
import server.Server;

public class Area_2_4 extends Area_2 {

    private static boolean cached;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        initMap(19, 20, Tile.TileSize.X16);

        if (!cached) {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/map_2-4.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/triggermap_2-4.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/spawnmap_2-4.png");
            cached = true;
        } else {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/map_2-4.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_2-4.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_2-4.png");
        }

        putTrigger(0xffff0000, () -> {
            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(9, 3, DemoTile.SIZE));
            bundle.putExtra("player", player);
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("pixels", getScreenPixels());
            intent.putExtra("nextGameState", Area_2_3.class);
            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/map_2-4.png");
        pixelsToPNG(getTriggerTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_2-4.png");
        pixelsToPNG(getMobTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_2-4.png");
    }
}