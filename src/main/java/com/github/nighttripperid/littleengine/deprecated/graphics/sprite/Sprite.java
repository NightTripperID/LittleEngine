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

package com.github.nighttripperid.littleengine.deprecated.graphics.sprite;

import lombok.Getter;

/**
 * Represents a 2 dimensional graphic on screen. com.bitburger.graphics.Sprite data is loaded from a com.bitburger.graphics.SpriteSheet object.
 * Width, height, and coordinates can be specified. Sprites can also be rotated using affine matrix transformation.
 */
@Deprecated
public class Sprite {

    @Getter
    private int width;
    @Getter
    private int height;

    private int xOfs;
    private int yOfs;

    public int[] pixels;
    protected SpriteSheet sheet;

    /**
     * Used by AnimSprite extension to create a Sprite that represents a sprite strip to be animated.
     * @param sheet: The sheet from which the sprite strip is grabbed.
     * @param width: The width of the sprite strip.
     * @param height: The height of the sprite strip.
     */
    protected Sprite(SpriteSheet sheet, int width, int height) {
        this.width = width;
        this.height = height;
        this.sheet = sheet;
    }

    /**
     * Creates a Sprite object using a specified pixel array.
     * @param pixels The pixels representing the Sprite.
     * @param width The Sprite width.
     * @param height The Sprite height.
     */
    public Sprite(int[] pixels, int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[pixels.length];
        System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
    }

    /**
     * Creates a Sprite object using a specified SpriteSheet
     * @param sheet The given SpriteSheet.
     * @param width The Sprite width.
     * @param height The Sprite height.
     * @param xOfs The x offset of the Sprite on the SpriteSheet in tile precision.
     * @param yOfs The y offset of the Sprite on the SpriteSheet in tile precision.
     */
    public Sprite(SpriteSheet sheet, int width, int height, int xOfs, int yOfs) {
        this.sheet = sheet;
        this.width = width;
        this.height = height;
        this.xOfs = xOfs * width;
        this.yOfs = yOfs * height;
        pixels = new int[width * height];
        load();
    }

    /**
     * Creates a Sprite object using a specified SpriteSheet
     * @param sheet The given SpriteSheet.
     * @param size The Sprite size.
     * @param xOfs The x offset of the Sprite on the SpriteSheet in tile precision.
     * @param yOfs The y offset of the Sprite on the SpriteSheet in tile precision.
     */
    public Sprite(SpriteSheet sheet, int size, int xOfs, int yOfs) {
        this.sheet = sheet;
        this.width = size;
        this.height = size;
        this.xOfs = xOfs * size;
        this.yOfs = yOfs * size;
        pixels = new int[size * size];
        load();
    }

    /**
     * Creates a Sprite object that is filled with a solid color.
     * @param col The given color.
     * @param width The given width.
     * @param height The given height.
     */
    public Sprite(int col, int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        setColor(col);
    }

    /**
     * Returns an array of Sprites that are retrieved from a SpriteSheet.
     * @param sheet The SpriteSheet from which the Sprite array is retrieved.
     * @return The array of Sprites retrieved from the SpriteSheet.
     */
    public static Sprite[] split(SpriteSheet sheet) {
        int amount = (sheet.getSpriteSheetWidth() * sheet.getSpriteSheetHeight()) / (sheet.spriteWidth * sheet.spriteHeight);
        Sprite[] sprites = new Sprite[amount];
        int current = 0;
        int[] pixels = new int[sheet.spriteWidth * sheet.spriteHeight];

        for (int yp = 0; yp < sheet.getSpriteSheetHeight() / sheet.spriteHeight; yp++) {
            for (int xp = 0; xp < sheet.getSpriteSheetWidth() / sheet.spriteWidth; xp++) {

                for (int y = 0; y < sheet.spriteHeight; y++) {
                    for (int x = 0; x < sheet.spriteWidth; x++) {
                        int xo = x + xp * sheet.spriteWidth;
                        int yo = y + yp * sheet.spriteHeight;
                        pixels[x + y * sheet.spriteWidth] = sheet.getPixelBuffer()[xo + yo * sheet.getSpriteSheetWidth()];
                    }
                }
                sprites[current++] = new Sprite(pixels, sheet.spriteWidth, sheet.spriteHeight);
            }
        }
        return sprites;
    }

    /**
     * Rotate specified sprite by the specified angle.
     * @param sprite: The specified com.bitburger.graphics.Sprite object.
     * @param angle: The angle in radians.
     * @return the rotated sprite
     */
    public static Sprite rotate(Sprite sprite, double angle){
        return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
    }

    /**
     * Rotates the Sprite using an affine matrix
     * @param pixels The pixels representing the Sprite
     * @param width The Sprite width
     * @param height The Sprite height
     * @param angle The angle in radians by which to rotate the Sprite
     * @return the affine matrix rotated pixel array
     */
    private static int[] rotate(int[] pixels, int width, int height, double angle) {
        int[] result = new int[width * height];

        double nx_x = rot_x(-angle, 1.0, 0.0);
        double nx_y = rot_y(-angle, 1.0, 0.0);
        double ny_x = rot_x(-angle, 0.0, 1.0);
        double ny_y = rot_y(-angle, 0.0, 1.0);

        double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
        double y0 = rot_y(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

        for (int y = 0; y < height; y++) {
            double x1 = x0;
            double y1 = y0;
            for (int x = 0; x < width; x++) {
                int xx = (int) x1;
                int yy = (int) y1;
                int col = 0;
                if (xx < 0 || xx >= width || yy < 0 || yy >= height)
                    col = 0xffff00ff;
                else
                    col = pixels[xx + yy * width];
                result[x + y * width] = col;
                x1 += nx_x;
                y1 += nx_y;
            }
            x0 += ny_x;
            y0 += ny_y;
        }

        return result;
    }

    /**
     * Returns the new x coordinate of a pixel rotated by a specified angle.
     * @param angle The given rotation angle in radians.
     * @param x The initial x coordinate of the given pixel.
     * @param y The initial y coordinate of the given pixel.
     * @return The new x coordinate of the given pixel.
     */
    private static double rot_x(double angle, double x, double y) {
        double cos = Math.cos(angle-Math.PI/2);
        double sin = Math.sin(angle- Math.PI/2);
        return x * cos + y * -sin;
    }

    /**
     * Returns the new y coordinate of a pixel rotated by a specified angle.
     * @param angle The given rotation angle in radians.
     * @param x The initial x coordinate of the given pixel.
     * @param y The initial y coordinate of the given pixel.
     * @return The new y coordinate of the given pixel.
     */
    private static double rot_y(double angle, double x, double y) {
        double cos = Math.cos(angle-Math.PI/2);
        double sin = Math.sin(angle-Math.PI/2);
        return x * sin + y * cos;
    }

    /**
     * Loads the Sprite pixels from the Sprite's SpriteSheet.
     */
    private void load() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = sheet.pixelBuffer[(x + xOfs) + (y + yOfs) * sheet.getSpriteSheetWidth()];
            }
        }
    }

    /**
     * Fills the Sprite with the specified color.
     * @param col The given color
     */
    private void setColor(int col) {
        for(int i = 0; i < pixels.length; i++)
        pixels[i] = col;
    }
}