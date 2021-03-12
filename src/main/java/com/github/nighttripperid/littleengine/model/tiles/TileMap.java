/*
 * Copyright (c) 2021, BitBurger, Evan Doering
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.nighttripperid.littleengine.model.tiles;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class TileMap {
    @Setter
    @Getter
    private int width_T;
    @Setter
    @Getter
    private int height_T;

    private final Map<Integer, Integer[]> tileMap = new HashMap<>(); // hashed by layerId
    private final Map<Integer, TILED_TileMap.Object> tileObjects = new HashMap<>(); // hashed by tileId

        public Tile getTile(Tileset tileset, int layerId, int x_T, int y_T) {

            if (    x_T < 0 ||
                    y_T < 0 ||
                    x_T >= width_T ||
                    y_T >= height_T) {

            return Tileset.VOID_TILE;

        } else {
            return tileset.getTileset().get(getTileId(layerId, x_T, y_T));
        }
    }

    public Integer getTileId(int layerId, int x_T, int y_T) {

        if (    x_T < 0 ||
                y_T < 0 ||
                x_T >= width_T ||
                y_T >= height_T) {

            return null;

        } else {
            return tileMap.get(layerId)[x_T + y_T * width_T] - 1;
        }
    }

    public void putLayer(int layerId, Integer[] data) {
            tileMap.put(layerId, data);
    }

    public void putTileObject(int tileId, TILED_TileMap.Object object) {
            tileObjects.put(tileId, object);
    }
    public TILED_TileMap.Object getTileObject(Integer tileId) {
            return tileId == null ? null : tileObjects.get(tileId);
    }

    public int getNumLayers() {
            return tileMap.size();
    }

    public boolean hasLayer(int layerId) {
            return tileMap.get(layerId) != null;
    }
}