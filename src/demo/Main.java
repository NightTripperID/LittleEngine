package demo;

import demo.area.Area_1_1;
import demo.mob.DemoCursor;
import demo.player.Player;
import demo.tile.TileCoord;
import gamestate.Bundle;
import gamestate.Intent;

import server.Server;

import java.awt.*;

public class Main {
    public static void main(String[] args) {

        Bundle bundle = new Bundle();
        TileCoord tileCoord = new TileCoord(14, 17, 16);
        bundle.putExtra("player", new Player(tileCoord.getX(), tileCoord.getY()));

        Intent intent = new Intent(Area_1_1.class);
        intent.setBundle(bundle);

        Server server = new Server(320, 240, 3, "Demo");

        DemoCursor cursor = new DemoCursor(new Point(8, 8), "cursor", "src/resource/pointerup.png", "src/resource/pointerdown.png");

        server.setCustomMouseCursor(cursor.getImage(DemoCursor.CURSOR_UP),
                cursor.getCursorHotSpot(), cursor.getName());

        server.startServer(intent);
    }
}
