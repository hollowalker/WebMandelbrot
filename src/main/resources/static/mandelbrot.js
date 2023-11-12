const API_URL = '/api/v1/mandelbrot';

async function postData(url , data ) {
    const response = await fetch(url, {
        method: "POST",
        mode: "cors",
        cache: "no-cache",
        credentials: "same-origin",
        headers: {
            "Content-Type": "application/json",
        },
        redirect: "follow",
        referrerPolicy: "no-referrer",
        body: JSON.stringify(data),
    });
    return response.json();
}

document.addEventListener('DOMContentLoaded', function() {

    const canvas = document.getElementById('mandelbrot');
    const ctx = canvas.getContext("2d");

    const ZOOM_FACTOR = 0.1;

    let width = document.body.clientWidth;
    let height = document.body.clientHeight;

    ctx.canvas.width = width;
    ctx.canvas.height = height;

    let REAL_SET = {
        min: -2.0,
        max: 1.0
    };
    let IMAGINARY_SET = {
        min: -1.0,
        max: 1.0
    }
    postData(API_URL, {
        "width": width,
        "height": height,
        "reSetMin": REAL_SET.min,
        "reSetMax": REAL_SET.max,
        "imSetMin": IMAGINARY_SET.min,
        "imSetMax": IMAGINARY_SET.max
    }).then((dots) => {
        dots.forEach(dot => {
            ctx.fillStyle = `rgb(${dot.rgb[0]}, ${dot.rgb[1]},${dot.rgb[2]})`;
            ctx.fillRect(dot.x, dot.y, 1, 1);
        });
    });

    canvas.addEventListener('click', function(event) {
        let newMandelbrotInfo = {
            realCenter: REAL_SET.min + (REAL_SET.max - REAL_SET.min) / width * event.pageX,
            imaginaryCenter: IMAGINARY_SET.max - (IMAGINARY_SET.max - IMAGINARY_SET.min) / height * event.pageY,
            mandelbrotRealWidth: (REAL_SET.max - REAL_SET.min) * 0.5,
            mandelbrotImaginaryHeight: (IMAGINARY_SET.max - IMAGINARY_SET.min) * 0.5
        }
        REAL_SET = {
            min: newMandelbrotInfo.realCenter - newMandelbrotInfo.mandelbrotRealWidth / 2,
            max: newMandelbrotInfo.realCenter + newMandelbrotInfo.mandelbrotRealWidth / 2
        }
        IMAGINARY_SET = {
            min: newMandelbrotInfo.imaginaryCenter - newMandelbrotInfo.mandelbrotImaginaryHeight / 2,
            max: newMandelbrotInfo.imaginaryCenter + newMandelbrotInfo.mandelbrotImaginaryHeight / 2
        }
        postData(API_URL, {
            "width": width,
            "height": height,
            "reSetMin": REAL_SET.min,
            "reSetMax": REAL_SET.max,
            "imSetMin": IMAGINARY_SET.min,
            "imSetMax": IMAGINARY_SET.max
        }).then((dots) => {
            console.log(dots);
            dots.forEach(dot => {
                ctx.fillStyle = `rgb(${dot.rgb[0]}, ${dot.rgb[1]},${dot.rgb[2]})`;
                ctx.fillRect(dot.x, dot.y, 1, 1);
            });
        });
    });
});

