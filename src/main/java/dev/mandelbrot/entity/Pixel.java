package dev.mandelbrot.entity;

public class Pixel {
    int x;
    int y;
    int[] rgb;

    public Pixel(int x, int y, int[] rgb) {
        this.x = x;
        this.y = y;
        this.rgb = rgb;
    }

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
        this.rgb = new int[] {0, 0, 0};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] getRgb() {
        return rgb;
    }
}
