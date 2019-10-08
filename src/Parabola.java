public class Parabola extends Quadratic implements Calculations, Drawable{

    protected double a;
    protected double b;
    protected double x1;

    public Parabola(double a, double b, double x1){
        super(a, 0, b, x1);
        this.a = a;
        this.b = b;
        this.x1 = x1;
    }

    @Override
    public double val(double x){
        return a * Math.pow((x - x1), 2) + b;
    }

    @Override
    public String toString(){
        String name = "", xTerm;
        String xString = Double.toString(-x1);
        //Handle x1
        if (x1 == 0) xTerm = "x";
        else if (x1 > 0) xTerm = "(x" + xString + ")";
        else xTerm = "(x+" + xString + ")";
        //Handle a
        if (a == 1) name += xTerm + "^2";
        else if (a == -1) name += "-" + xTerm + "^2";
        else if (a != 0) name += a + xTerm + "^2";
        //Handle b
        if (b > 0) name += "+" + b;
        else if (b < 0) name += b;
        //Handle all-zeros case
        if (a == 0 && b == 0) name += "0";
        return name;
    }

}
