import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Logarithm extends Adjust implements Calculations, Drawable{

    protected double a;
    protected double b;
    protected double x1;

    public Logarithm(double a, double b, double x1){
        super();
        this.a = a;
        this.b = b;
        this.x1 = x1;
    }

    @Override
    public double val(double x){
        return a * Math.log(x - x1) + b;
    }

    @Override
    public String toString(){
        String name = "", xTerm = "ln(x";
        //handle x1
        if (x1 > 0) xTerm += -x1 + ")";
        else if (x1 < 0) xTerm += "+" + -x1 + ")";
        else xTerm += ")";
        //handle a
        if (a == 1) name += xTerm;
        else if (a == -1) name += "-" + xTerm;
        else if (a != 0) name += a + xTerm;
        //handle b
        if (b > 0) name += "+" + b;
        else if (b < 0) name += b;
        //If the function is zero
        if (a == 0 && b == 0) name += "0";
        return name;
    }

    @Override
    public boolean undefined(double x){
        //True if the function is undefined
        return x < super.getStartDomain() || x > super.getEndDomain() || x <= x1;
    }

    @Override
    public double getArea(double x_start, double x_end){
        double delta = 0.01;
        double i = x_start, area = 0;
        while (i <= x_end){
            //Check if the point is defined at point i
            if (!undefined(i)) area += val(i) * delta;
            i += delta;
        }
        return area;
    }

    @Override
    public double getSlope(double x){
        double delta = 1e-9;
        //Check if the point is defined at point i
        if (!undefined(x)) return (val(x + delta) - val(x - delta)) / delta / 2.0;
        return Double.NaN;
    }

    @Override
    public void draw(Canvas canvas){
        double i = super.getStartDomain(), XEnd = super.getEndDomain(); //Domain
        double x = super.shiftX;
        double y = super.shiftY;
        double delta = 0.1;
        double screenX = canvas.getWidth(), screenY = canvas.getHeight();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.strokeLine(x, screenY/2 + y, screenX + x, screenY/2 + y);
        gc.strokeLine(screenX/2 + x, y, screenX/2 + x, screenY + y);

        gc.setStroke(super.getColour());
        while (i <= XEnd){
            double prevX = i;
            //Cut off the extra digits for i to avoid errors
            i = Math.round((i + delta) * 10.0) / 10.0;
            //Check if the value is defined at i
            if (undefined(i)) continue;
            double startX = (prevX + x) + screenX/2.0;
            double startY = (y - val(prevX)) + screenY/2.0;
            double endX = (i + x) + screenX/2.0;
            double endY = (y - val(i)) + screenY/2.0;
            gc.strokeLine(startX, startY, endX, endY);
        }
    }
}