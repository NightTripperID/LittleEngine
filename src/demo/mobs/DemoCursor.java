package demo.mobs;

import com.sun.istack.internal.NotNull;
import input.MouseCursor;

import java.awt.*;
import java.awt.event.MouseEvent;

public class DemoCursor extends MouseCursor {

    public static final int CURSOR_UP = 0;
    public static final int CURSOR_DOWN = 1;

    public DemoCursor(@NotNull Point cursorHotSpot, @NotNull String name, @NotNull String... imagePaths ) {
        super(cursorHotSpot, name, imagePaths);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        super.mousePressed(event);
        gameState.setCustomMouseCursor(images.get(CURSOR_DOWN), cursorHotSpot, name);
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        super.mouseReleased(event);
        gameState.setCustomMouseCursor(images.get(CURSOR_UP), cursorHotSpot, name);
    }
}
