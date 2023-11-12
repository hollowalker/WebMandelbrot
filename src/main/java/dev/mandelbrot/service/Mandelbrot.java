package dev.mandelbrot.service;

import dev.mandelbrot.entity.Pixel;

import java.util.ArrayList;
import java.util.List;

public class Mandelbrot {

    private class MandelbrotThread extends Thread{
        int x;
        int height;
        double realSetMin;
        double cReWidthOnPixel;
        double imaginarySetMax;
        double cImHeightOnPixel;
        int MAX_ITERATION = 9000;
        List<Pixel> pixels;

        public MandelbrotThread(int x, int height, double realSetMin, double cReWidthOnPixel, double imaginarySetMax, double cImHeightOnPixel) {
            this.x = x;
            this.height = height;
            this.realSetMin = realSetMin;
            this.cReWidthOnPixel = cReWidthOnPixel;
            this.imaginarySetMax = imaginarySetMax;
            this.cImHeightOnPixel = cImHeightOnPixel;
        }

        @Override
        public void run() {
            pixels = new ArrayList<>();
            for (var y = 0; y < height; y++) {
                double c_real = realSetMin + x * cReWidthOnPixel; //from -2 to 1 default
                double c_imaginary = imaginarySetMax - y * cImHeightOnPixel; //from -1 to 1 default

                double z_x = 0;
                double z_y = 0;
                int iteration = 0;

                do {
                    double x_new = z_x * z_x - z_y * z_y + c_real;
                    z_y = 2 * z_x * z_y + c_imaginary;
                    z_x = x_new;
                    iteration++;
                } while (iteration < MAX_ITERATION && z_x * z_x + z_y * z_y <= 4);

                if (iteration < MAX_ITERATION) {
                    pixels.add(new Pixel(x, y, Mandelbrot.colorPalette.get(iteration % (Mandelbrot.colorPalette.size() - 1))));
                }
                else {
                    pixels.add(new Pixel(x, y));
                }
            }
        }

        public List<Pixel> getPixels() {
            return pixels;
        }
    }

    public final static List<int[]> colorPalette = getPalette();
    //COLORS
    private static int lagrange(int[] dot1, int[] dot2, int x) {
        return ((dot1[1] * (x - dot2[0])) / (dot1[0] - dot2[0])) + ((dot2[1] * (x - dot1[0])) / (dot2[0] - dot1[0]));
    }

    private static List<int[]> getPalette() {
        int size = 250;
        int range = size / 6;
        List<int[]> colors = new ArrayList<>();
        int lagrangeColor;
        for (int k = 0; k < size; k++) {
            if (k <= range) { //red to yellow
                lagrangeColor = lagrange(new int[] {0, 0}, new int[] {range, 255}, k);
                colors.add(new int[] {255, lagrangeColor, 0});
            } else if (k <= range * 2) { //yellow to green
                lagrangeColor = lagrange(new int[] {range + 1, 255}, new int[] {range * 2, 0}, k);
                colors.add(new int[] {lagrangeColor, 255, 0});
            } else if (k <= range * 3) {//green to cyan
                lagrangeColor = lagrange(new int[] {range * 2 + 1, 0}, new int[] {range * 3, 255}, k);
                colors.add(new int[] {0, 255, lagrangeColor});
            } else if (k <= range * 4) {//cyan to blue
                lagrangeColor = lagrange(new int[] {range * 3 + 1, 255}, new int[] {range * 4, 0}, k);
                colors.add(new int[] {0, lagrangeColor, 255});
            } else if (k <= range * 5) {//blue to purple
                lagrangeColor = lagrange(new int[] {range * 4 + 1, 0}, new int[] {range * 5, 255}, k);
                colors.add(new int[] {lagrangeColor, 0, 255});
            } else {//purple to red
                lagrangeColor = lagrange(new int[] {range * 5 + 1, 255}, new int[] {size - 1, 0}, k);
                colors.add(new int[] {255, 0, lagrangeColor});
            }
        }
        return colors;
    }

    public List<Pixel> getDots(int width, int height, double realSetMin, double realSetMax, double imaginarySetMin, double imaginarySetMax) throws InterruptedException {
        double cReWidthOnPixel = (realSetMax - realSetMin) / width;
        double cImHeightOnPixel = (imaginarySetMax - imaginarySetMin) / height;
        List<Pixel> pixels = new ArrayList<>();
        List<MandelbrotThread> mandelbrotThreads = new ArrayList<>();
        for (var x = 0; x < width; x++) {
            mandelbrotThreads.add(new MandelbrotThread(x, height, realSetMin, cReWidthOnPixel, imaginarySetMax, cImHeightOnPixel));
            mandelbrotThreads.get(mandelbrotThreads.size() - 1).start();
        }
        for (MandelbrotThread mandelbrotThread : mandelbrotThreads) {
            mandelbrotThread.join();
        }
        for (MandelbrotThread mandelbrotThread : mandelbrotThreads) {
            pixels.addAll(mandelbrotThread.getPixels());
        }
        return pixels;
    }
}
