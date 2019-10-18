import javafx.scene.canvas.Canvas;

public class Linear extends Quadratic{

    protected double m;
    protected double b;
    protected double x1;

    public Linear(double m, double b, double x1){
        super(0, m, b, x1);
        this.m = m;
        this.b = b;
        this.x1 = x1;
        super.setName("Linear");
    }
}
