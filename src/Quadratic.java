import javafx.scene.canvas.Canvas;

public class Quadratic extends Cubic{

    protected double a;
    protected double b;
    protected double c;
    protected double x1;

    public Quadratic(double a, double b, double c, double x1){
        super(0, a, b, c, x1);
        this.a = a;
        this.b = b;
        this.c = c;
        this.x1 = x1;
        super.setName("Quadratic");
    }
}
