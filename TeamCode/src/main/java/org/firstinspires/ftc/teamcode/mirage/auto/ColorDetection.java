package org.firstinspires.ftc.teamcode.mirage.auto;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import android.graphics.Bitmap;



public class ColorDetection {
    public void ColorDetection(){

    }
    public int pinkFinder(Bitmap img){

    }
}

/* We are porting this Javascript function
let Jimp = require("./node_modules/jimp")

function toColor(num) {
    let rgb = Jimp.intToRGBA(num);
    return [rgb.r,rgb.g,rgb.b];
}
function RGBToHSL(r, g, b){
    r /= 255, g /= 255, b /= 255;
    var max = Math.max(r, g, b), min = Math.min(r, g, b);
    var h, s, l = (max + min) / 2;

    if(max == min){
        h = s = 0; // achromatic
    }else{
        var d = max - min;
        s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
        switch(max){
            case r: h = (g - b) / d + (g < b ? 6 : 0); break;
            case g: h = (b - r) / d + 2; break;
            case b: h = (r - g) / d + 4; break;
        }
        h /= 6;
    }

    return [h*240, s*240, l*240];
}
function error(actual,predicted,h){
    if(h){
        actual += 240;
        predicted += 240;
        if(actual > predicted){
            actual -= 240;
        } else{
            predicted -= 240;
        }
        actual %= 240;
        predicted %= 240;
        return (Math.abs(actual - predicted));
    }
    return (Math.abs(actual - predicted));
}
Jimp.read("./find_pink.jpg", function (err, image) {
    let width = image.bitmap.width;
    let height = image.bitmap.height;
    console.log(toColor(image.getPixelColor(0, 0)));
    let targetHSL = [211,165,90];

    let centerOfMass = [0,0];
    let pinkestLocation = [0,0];

    let totalError = 0;

    let minError = Infinity;
    for(let y = 0; y<height;y++){
        for(let x = 0; x<width;x++){
            let hsl = RGBToHSL(...toColor(image.getPixelColor(x,y)));
            let calcError = error(hsl[0],targetHSL[0],true) + error(hsl[1],targetHSL[1]) + error(hsl[2],targetHSL[2]);
            calcError = Math.exp(-1/10*calcError);
            centerOfMass[0] += x*calcError;
            centerOfMass[1] += y*calcError;

            if(calcError < minError){
                minError = calcError;
                pinkestLocation = [x,y]
            }
            totalError += calcError;
            //console.log(image.getPixelColor(x, y));
        }
    }
    centerOfMass[0] /= totalError;
    centerOfMass[1] /= totalError;
    console.log(`The center of pink in this image is (${centerOfMass[0]},${centerOfMass[1]})`)
    Jimp.read("./dot.png",function(err,dot){
        image.composite(dot,Math.floor(centerOfMass[0]-100),Math.floor(centerOfMass[1]-100));
        image.write("center_location3.png");
    })
    // console.log(`The pinkest pixel is at (${pinkestLocation[0]},${pinkestLocation[1]})`)
    // let rgb = toColor(image.getPixelColor(pinkestLocation[0],pinkestLocation[1]));
    // console.log(`RGB at this location is (${rgb[0]},${rgb[1]},${rgb[2]})`)
    // let hsl = RGBToHSL(...rgb);
    // console.log(`HSL at this location is (${hsl[0]},${hsl[1]},${hsl[2]})`);
    // console.log(`The error at this location was ${minError}.`)

    // function newScope(){
    //     let hsl = RGBToHSL(...toColor(image.getPixelColor(1516,190)));
    //     let calcError = error(hsl[0],targetHSL[0]) + error(hsl[1],targetHSL[1]) + error(hsl[2],targetHSL[2]);
    //     console.log(`The calculated error at the theoretical best location is: ${calcError}`)

    //     console.log(`HSL at this location is (${hsl[0]},${hsl[1]},${hsl[2]})`);
    // } newScope();
    // console.log(toColor(image.getPixelColor(1516,190)));
    // console.log([212,161,91]);
    // console.log(RGBToHSL(...[162,32,122]))
     // returns the colour of that pixel e.g. 0xFFFFFFFF
});
*/