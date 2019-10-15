import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

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
        double i = super.getStartDomain(), XEnd = super.getEndDomain(); //Domain
        double delta = 0.1;
        double screenX = canvas.getWidth();
        double screenY = canvas.getHeight();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(super.getColour());
        while (i <= XEnd){
            double prevX = i;
            //Cut off the extra digits for i to avoid errors
            i = Math.round((i + delta) * 10.0) / 10.0;
            //Check if the value is defined at i
            if (undefined(i)) continue;
            double startX = prevX + screenX/2.0;
            double startY = -val(prevX) + screenY/2.0;
            double endX = i + screenX/2.0;
            double endY = -val(i) + screenY/2.0;
            gc.strokeLine(startX, startY, endX, endY);
        }
    }
}