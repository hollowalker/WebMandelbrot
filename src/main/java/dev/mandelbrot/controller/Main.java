package dev.mandelbrot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Main {

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html")
    public String main() {
        return "index.html";
    }
}
