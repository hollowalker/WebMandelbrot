package dev.mandelbrot.controller;

import dev.mandelbrot.entity.MandelbrotInit;
import dev.mandelbrot.entity.Pixel;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/mandelbrot")
public class Mandelbrot {
    @PostMapping
    public List<Pixel> getMandelbrot(@RequestBody MandelbrotInit mandelbrotInit) throws InterruptedException {
        dev.mandelbrot.service.Mandelbrot mandelbrot = new dev.mandelbrot.service.Mandelbrot();
        List<Pixel> dots = mandelbrot.getDots(
                mandelbrotInit.getWidth(),
                mandelbrotInit.getHeight(),
                mandelbrotInit.getReSetMin(),
                mandelbrotInit.getReSetMax(),
                mandelbrotInit.getImSetMin(),
                mandelbrotInit.getImSetMax());
//        BufferedImage img = null;
//        img = new BufferedImage(mandelbrotInit.getWidth(), mandelbrotInit.getHeight(), BufferedImage.TYPE_INT_ARGB);
//        for (Pixel dot : dots) {
//            img.setRGB(dot.getX(), dot.getY(), new Color(dot.getRgb()[0],dot.getRgb()[1],dot.getRgb()[2]).getRGB());
//        }
//        try
//        {
//            File f = new File("C:/Users/works/OneDrive/Изображения/Mandelbrot.png");
//            ImageIO.write(img, "png", f);
//        }
//        catch(IOException e)
//        {
//            System.out.println("Error: " + e);
//        }
        return dots;
    }
}
