import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.math.BigDecimal;

public class Arc extends Function implements Calculations, Drawable{

    protected double r;
    protected double xcenter;
    protected double ycenter;

    public Arc(double r, double xcenter, double ycenter){
        super();
        this.r = r;
        this.xcenter = xcenter;
        this.ycenter = ycenter;
        super.setName("Arc");
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
        return x < super.getStartDomain() || x > super.getEndDomain() || r * r < Math.pow(x - xcenter, 2);
    }

    @Override
    public double getArea(double x_start, double x_end){
        double delta = 0.01;
        double i = x_start, area = 0;
        while (i <= x_end){
            //Check if the value is defined (real) at the point i
            if (!undefined(i)) area += val(i) * delta;
            i += delta;
        }
        return area;
    }

    @Override
    public double getSlope(double x){
        double delta = 1e-9;
        //Check if the value is defined (real) at the point i
        if (!undefined(x)) return (val(x + delta) - val(x - delta)) / delta / 2.0;
        return Double.NaN;
    }

    @Override
    public void draw(Canvas canvas){
        double screenX = canvas.getWidth(), screenY = canvas.getHeight();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //X-axis & Y-axis
        gc.strokeLine(0, screenY/2, screenX, screenY/2);
        gc.strokeLine(screenX/2, 0, screenX/2, screenY);
        gc.setStroke(super.getColour());

        double domain1 = super.getStartDomain();
        double domain2 = super.getEndDomain();
        double ratioX = canvas.getWidth() / Math.abs(domain2 - domain1);
        double adjustX = (domain2 + domain1) / 2;

        //OBJECTIVE: Find real domain, compare with setup domain
        double realDomain1 = this.xcenter - this.r;
        double realDomain2 = this.xcenter + this.r;
        if (realDomain1 > realDomain2){
            //Ensure that domain1 <= domain2
            double temp = realDomain2;
            realDomain2 = realDomain1;
            realDomain1 = temp;
        }
        //Compare with setup domain to find the final defined & intended domain
        realDomain1 = Math.max(realDomain1, domain1);
        realDomain2 = Math.min(realDomain2, domain2);
        System.out.println("ADJUSTED DOMAIN: " + realDomain1 + " " + realDomain2);

        BigDecimal delta = new BigDecimal(Math.abs(realDomain2 - realDomain1) / 1000);
        BigDecimal loop = new BigDecimal(domain1);

        double highest = -Double.MAX_VALUE, lowest = Double.MAX_VALUE;
        while (loop.doubleValue() <= domain2){
            if (undefined(loop.doubleValue())){
                loop = loop.add(delta);
                continue;
            }
            highest = Math.max(highest, -val(loop.doubleValue()));
            lowest = Math.min(lowest, -val(loop.doubleValue()));
            loop = loop.add(delta);
        }
        double adjustY = (highest + lowest) / 2;
        double ratioY = screenY / (highest - lowest);

        loop = new BigDecimal(realDomain1);

        while (loop.doubleValue() <= realDomain2){
            double prevX = loop.doubleValue();
            loop = loop.add(delta);
            double startX = ratioX * (prevX - adjustX) + screenX/2;
            double startY = ratioY * (-val(prevX) - adjustY) + screenY/2;
            double endX = ratioX * (loop.doubleValue() - adjustX) + screenX/2;
            double endY = ratioY * (-val(loop.doubleValue()) - adjustY) + screenY/2;
            gc.strokeLine(startX, startY, endX, endY);
        }
    }
}