import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

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
        double i = super.getStartDomain(), XEnd = super.getEndDomain(); //Domain
        double screenX = canvas.getWidth(), screenY = canvas.getHeight();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //X-axis & Y-axis
        gc.strokeLine(0, screenY/2, screenX, screenY/2);
        gc.strokeLine(screenX/2, 0, screenX/2, screenY);
        gc.setStroke(super.getColour());

        double domain1 = super.getStartDomain();
        double domain2 = super.getEndDomain();
        System.out.println("ADJUSTED DOMAIN: " + domain1 + " " + domain2);
        double ratioX = canvas.getWidth() / Math.abs(domain2 - domain1);
        double adjustX = Math.abs(domain2 + domain1) / 2;
        double delta = Math.abs(domain2 - domain1) / 1000;
        System.out.println("DELTA: " + delta);

        //DELTAY
        
        //LAST FIX: for ratioY, multiply by 90% to show the entire graph more clearly.

        while (i + delta <= domain2){
            double prevX = i;
            //Cut off the extra digits for i to avoid errors
            i = Math.round((i + delta) * 1000.0) / 1000.0;
            //Check if the value is defined at i
            if (undefined(i)) continue;
            System.out.println("X: " + prevX + " " + i);
            double startX = ratioX * (prevX - adjustX) + screenX/2;
            double startY = (-val(prevX)) + screenY/2;
            double endX = ratioX * (i - adjustX) + screenX/2;
            double endY = (-val(i)) + screenY/2;
            System.out.println("CUR: " + startX + " " + startY + " " + endX + " " + endY);
            gc.strokeLine(startX, startY, endX, endY);
        }
    }
}