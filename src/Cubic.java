import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.math.BigDecimal;

public class Cubic extends Function implements Calculations, Drawable{

    protected double a;
    protected double b;
    protected double c;
    protected double d;
    protected double x1;

    public Cubic(double a, double b, double c, double d, double x1){
        super();
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.x1 = x1;
        super.setName("Cubic");
    }

    @Override
    public double val(double x){
        return a * Math.pow((x - x1), 3) + b * Math.pow((x - x1), 2) + c * (x - x1) + d;
    }

    @Override
    public boolean undefined(double x){
        return x < super.getStartDomain() || x > super.getEndDomain();
    }

    @Override
    public double getArea(double x_start, double x_end){
        double delta = 0.01;
        double i = x_start, area = 0;
        while (i <= x_end){
            area += val(i) * delta;
            i += delta;
        }
        return area;
    }

    @Override
    public double getSlope(double x){
        double delta = 1e-9;
        if (!undefined(x)) return (val(x + delta) - val(x - delta)) / delta / 2.0;
        return Double.NaN;
    }

    @Override
    public String toString(){
        String name = "", xTerm;
        //Handle x1
        if (x1 == 0) xTerm = "x";
        else if (x1 > 0) xTerm = "(x" + -x1 + ")";
        else xTerm = "(x+" + -x1 + ")";
        //Handle a
        if (a == 1) name += xTerm + "^3";
        else if (a == -1) name += "-" + xTerm + "^3";
        else if (a != 0) name += a + xTerm + "^3";
        //Handle b
        if (b == 1){
            if (a != 0) name += "+";
            name += xTerm + "^2";
        }
        else if (b == -1) name += "-" + xTerm + "^2";
        else if (b > 0){
            if (a != 0) name += "+";
            name += b + xTerm + "^2";
        }
        else if (b < 0) name += b + xTerm + "^2";
        //Handle c
        if (c == 1){
            if (a != 0 || b != 0) name += "+";
            name += xTerm + "^2";
        }
        else if (c == -1) name += "-" + xTerm;
        else if (c > 0){
            if (a != 0 || b != 0) name += "+";
            name += c + xTerm;
        }
        else if (c < 0) name += c + xTerm;
        //Handle d
        if (d > 0){
            if (a != 0 || b != 0 || c != 0) name += "+";
            name += d;
        }
        else if (d < 0) name += d;
        //Handle all zeroes case
        if (a == 0 && b == 0 && c == 0 && d == 0) name = "0";
        return name;
    }

    @Override
    public void draw(Canvas canvas){
        double screenX = canvas.getWidth(), screenY = canvas.getHeight();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //X-axis & Y-axis
        gc.strokeLine(0, screenY/2, screenX, screenY/2);
        gc.strokeLine(screenX/2, 0, screenX/2, screenY);
        gc.setStroke(super.getColour());

        double domain1 = super.getStartDomain(), domain2 = super.getEndDomain();
        double adjustX = (domain2 + domain1) / 2;
        double ratioX = screenX / (domain2 - domain1);

        BigDecimal delta = new BigDecimal(Math.abs(domain2 - domain1) / 1000);
        BigDecimal loop = new BigDecimal(domain1);

        double highest = -Double.MAX_VALUE, lowest = Double.MAX_VALUE;
        while (loop.doubleValue() <= domain2){
            highest = Math.max(highest, -val(loop.doubleValue()));
            lowest = Math.min(lowest, -val(loop.doubleValue()));
            loop = loop.add(delta);
        }
        double adjustY = (highest + lowest) / 2;
        double ratioY = screenY / (highest - lowest);

        loop = new BigDecimal(domain1);

        while (loop.doubleValue() <= domain2){
            double prevX = loop.doubleValue();
            loop = loop.add(delta);
            double startX = ratioX * (prevX - adjustX) + screenX/2;
            double startY = ratioY * (-val(prevX) - adjustY) + screenY/2;
            double endX = ratioX * (loop.doubleValue() - adjustX) + screenX/2;
            double endY = ratioY * (-val(loop.doubleValue()) - adjustY) + screenY/2;
            gc.strokeLine(startX, startY, endX, endY);
        }

        //LABEL AXIS
        double domainLabel = (domain2 - domain1) / 10;
        double rangeLabel = (highest - lowest) / 10;
        System.out.println("RANGE: " + rangeLabel + " " + -highest + " " + -lowest);
        gc.setLineWidth(2.0);
        gc.setStroke(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        for (int i = 1; i <= 9; i++){
            if (i == 5) continue; //So called "Origin"; skip
            double curLoopX = canvas.getWidth() / 10 * i;
            double curLoopY = canvas.getHeight() / 10 * i;
            double curX = domain1 + domainLabel * i;
            double curY = lowest + rangeLabel * i;
            //Set the y to be below x-axis (around 310)
            //For the mark, += 5; width 2
            //Same for y-axis; right
            gc.strokeLine(curLoopX, canvas.getWidth() / 2 - 5, curLoopX, canvas.getWidth() / 2 + 5);
            gc.strokeLine(canvas.getHeight() / 2 - 5, curLoopY, canvas.getHeight() / 2 + 5, curLoopY);
            String labelX = Double.toString(Math.round(curX * 1000.0) / 1000.0);
            String labelY = Double.toString(Math.round(curY * 10.0) / 10.0);
            gc.fillText(labelX, curLoopX, 320);
            gc.fillText(labelY, 325, curLoopY+5);
        }
    }
}