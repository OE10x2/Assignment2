import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Arc extends Function implements Calculations, Drawable{

    protected double r;
    protected double xcenter;
    protected double ycenter;

    public Arc(double r, double xcenter, double ycenter){
        super();
        this.r = r;
        this.xcenter = xcenter;
        this.ycenter = ycenter;
    }

    @Override
    public double val(double x){
        return Math.sqrt(r*r - Math.pow((x - xcenter), 2)) + ycenter;
    }

    @Override
    public String toString(){
        String name = "sqrt(", xTerm = "";
        String xString = Double.toString(-xcenter);
        String rString = Double.toString(r * r);
        //Handle xcenter
        if (xcenter == 0.0) xTerm = "x^2";
        else if (xcenter > 0) xTerm = "(x" + xString + ")^2";
        else if (xcenter < 0) xTerm = "(x+" + xString + ")^2";
        //Handle r
        if (r != 0) name += rString;
        name += "-" + xTerm + ")";
        //Handle ycenter
        if (ycenter > 0) name += "+" + ycenter;
        else if (ycenter < 0) name += ycenter;
        return name;
    }

    @Override
    public boolean undefined(double x){
        //True if the function is undefined
        return (x >= super.getStartDomain()) && (x <= super.getEndDomain() && r * r < Math.pow(x - x1, 2));
    }

    @Override
    public double getArea(double x_start, double x_end){
        double delta = 0.01;
        double i = x_start, area = 0;
        while (i <= x_end){
            //Check if the value is defined (real) at the point i
            if (r * r >= Math.pow(i - xcenter, 2)) area += val(i) * delta;
            i += delta;
        }
        return area;
    }

    @Override
    public double getSlope(double x){
        double delta = 1e-9;
        //Check if the value is defined (real) at the point i
        if (r * r >= Math.pow(x - xcenter, 2)) return (val(x + delta) - val(x - delta)) / delta / 2.0;
        return Double.NaN;
    }

    @Override
    public void draw(GraphicsContext gc, double screenX, double screenY) {
        double i = super.x1, XEnd = super.x2; //Domain
        double delta = 0.1;
        gc.setLineWidth(1);
        gc.setStroke(super.getColour());
        while (i <= XEnd){
            double prevX = i;
            //Cut off the extra digits for i to avoid errors
            i = Math.round((i + delta) * 10.0) / 10.0;
            //Check if the value is defined at i
            if (r * r < Math.pow(i - xcenter, 2)) continue;
            double startX = prevX + screenX/2.0;
            double startY = -val(prevX) + screenY/2.0;
            double endX = i + screenX/2.0;
            double endY = -val(i) + screenY/2.0;
            gc.strokeLine(startX, startY, endX, endY);
        }
        gc.setStroke(Color.GREEN);
        gc.setLineDashes(5d);
        gc.strokeLine(0, 440, 600, 440);
    }
}