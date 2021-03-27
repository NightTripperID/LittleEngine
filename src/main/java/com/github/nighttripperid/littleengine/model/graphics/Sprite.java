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
package com.github.nighttripperid.littleengine.model.graphics;

import com.github.nighttripperid.littleengine.model.physics.VectorI2D;

public class Sprite {
    public VectorI2D size;
    public int[] pixels;

    public Sprite(int[] pixels, int width, int height) {
        this.size = new VectorI2D(width, height);
        this.pixels = new int[pixels.length];
        System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
    }

    public Sprite(SpriteSheet spriteSheet, int width, int height, int xOfs, int yOfs) {
        this.size = new VectorI2D(width, height);
        pixels = new int[width * height];
        load(spriteSheet, new VectorI2D(xOfs * width, yOfs * height));
    }

    public Sprite(SpriteSheet spriteSheet, int size, int xOfs, int yOfs) {
        this.size = VectorI2D.of(size);
        pixels = new int[size * size];
        load(spriteSheet, new VectorI2D(xOfs * size, yOfs * size));
    }

    public Sprite(int col, int width, int height) {
        this.size = new VectorI2D(width, height);
        pixels = new int[width * height];
        setColor(col);
    }

    private void load(SpriteSheet spriteSheet, VectorI2D offset) {
        for (int y = 0; y < size.y; y++) {
            for (int x = 0; x < size.x; x++) {
                pixels[x + y * size.x] = spriteSheet.pixelBuffer[(x + offset.x) + (y + offset.y) * spriteSheet.sheetW_P];
            }
        }
    }

    private void setColor(int col) {
        for(int i = 0; i < pixels.length; i++)
            pixels[i] = pixels[i] == 0xffff00ff ? pixels[i] : col;
    }
}

