public class Parabola extends Quadratic{

    protected double a;
    protected double b;
    protected double x1;

    public Parabola(double a, double b, double x1){
        super(a, 0, b, x1);
        this.a = a;
        this.b = b;
        this.x1 = x1;
        super.setName("Parabola");
    }
}
