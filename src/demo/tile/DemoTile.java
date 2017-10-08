package demo.tile;

import com.sun.istack.internal.NotNull;
import graphics.Sprite;

public class DemoTile extends Tile {

    public static final int SIZE = 16;

    private String name = "unnamed";

    public DemoTile(@NotNull Sprite sprite, boolean solid, boolean trigger, @NotNull String name) {
        super(sprite, solid, trigger);
        this.name = name;
    }

    public DemoTile(@NotNull Sprite sprite, boolean solid, boolean trigger) {
        super(sprite, solid, trigger);
    }

    public DemoTile(@NotNull Sprite sprite, boolean solid, @NotNull String name) {
        super(sprite, solid);
        this.name = name;
    }

    public DemoTile(@NotNull Sprite sprite, boolean solid) {
        super(sprite, solid);
    }

    @Override
    public String toString() {
        return name;
    }
}
