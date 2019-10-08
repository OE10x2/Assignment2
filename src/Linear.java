import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Linear extends Function implements Calculations, Drawable{

    protected double m;
    protected double b;
    protected double x1;

    public Linear(double m, double b, double x1){
        super();
        //Alt: super.setDomain(-300, 300);
        this.m = m;
        this.b = b;
        this.x1 = x1;
    }

    @Override
    public String toString(){
        String bString = Double.toString(b);
        String xString = Double.toString(-x1);
        String name = "";
        //Handle m
        if (m == 0) return name + bString;
        if (m == 1) name += "(x";
        else if (m == -1.0) name += "-(x";
        else name += m + "(x";
        //handle x1
        if (x1 == 0) name += ")";
        else if (x1 > 0) name += xString + ")";
        else name += "+" + xString + ")";
        //handle b
        if (b > 0) name += "+" + bString;
        else if (b < 0) name += bString;
        return name;
    }

    @Override
    public double val(double x){
        return m * (x - x1) + b;
    }

    @Override
    public boolean undefined(double x){
        //True if the function is undefined
        return (x >= super.getStartDomain()) && (x <= super.getEndDomain());
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
        return m;
        //The slope of any point on the line is same as the slope of the line itself
    };

    @Override
    public void draw(GraphicsContext gc, double screenX, double screenY) {
        double i = super.x1, XEnd = super.x2; //Domain
        double delta = 0.1;
        gc.setLineWidth(1);
        gc.setStroke(super.col);
        while (i <= XEnd){
            double prevX = i;
            i += delta;
            double startX = prevX + screenX/2;
            double startY = -val(prevX) + screenY/2;
            double endX = i + screenX/2;
            double endY = -val(i) + screenY/2;
            gc.strokeLine(startX, startY, endX, endY);
        }
    }
}
