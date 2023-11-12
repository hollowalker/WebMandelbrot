package dev.mandelbrot.entity;

public class MandelbrotInit {
    int width;
    int height;
    double reSetMin;
    double reSetMax;
    double imSetMin;
    double imSetMax;

    public MandelbrotInit(int width, int height, double reSetMin, double reSetMax, double imSetMin, double imSetMax) {
        this.width = width;
        this.height = height;
        this.reSetMin = reSetMin;
        this.reSetMax = reSetMax;
        this.imSetMin = imSetMin;
        this.imSetMax = imSetMax;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getReSetMin() {
        return reSetMin;
    }

    public double getReSetMax() {
        return reSetMax;
    }

    public double getImSetMin() {
        return imSetMin;
    }

    public double getImSetMax() {
        return imSetMax;
    }
}
