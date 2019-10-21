import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FunctionTester extends Application{

    Canvas canvas = new Canvas(600, 600);
    Group root = new Group();

    public static void main(String[] args){ launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Drawing Functions Test");
        drawShapes();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawShapes(){
        Linear line = new Linear(1.0, 1.0, 0.0);
        //line.draw(canvas);

        Quadratic q = new Quadratic(0.25, -0.5, -1.0, 0.0);
        //q.draw(canvas);

        Parabola p = new Parabola(1.0, 0.0, 0.0);
        //p.setDomain(-1000, -999.56);
        //p.draw(canvas);

        Cubic c = new Cubic(0.35, 0.25, -0.5, -1.0, 2.0);
        c.setColour(Color.TURQUOISE);
        c.setDomain(-10, 19);
        c.draw(canvas);

        Arc a = new Arc(4.0, 0.0, 0.0);
        a.setColour(Color.TURQUOISE);
        a.setDomain(-3, 1);
        //a.draw(canvas);
        //System.out.println(a.getArea(-5, -4));

        Logarithm l = new Logarithm(1.0, 0.0, 90.0);
        l.setDomain(-100, 100);
        //l.draw(canvas);
        //System.out.println(l.getArea(5 ,6));
    }
}