import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FunctionTester extends Application{

    public static void main(String[] args){
        launch(args);
    }

    double screenX = 600, screenY = 600;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Drawing Functions Test");
        Group root = new Group();
        Canvas canvas = new Canvas(screenX, screenY);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);

        Linear line = new Linear(-2.0, -1.0, 0.0);
        line.setColour(Color.BLACK);
        System.out.println(line.toString());
        System.out.println(line.getArea(-10, 10));
        System.out.println(line.getSlope(0));
        line.draw(gc, screenX, screenY);

        Quadratic q = new Quadratic(0.25, -0.5, -1.0, 0.0);
        q.setColour(Color.PURPLE);
        q.setDomain(50, 100);
        System.out.println(q.toString());
        System.out.println(q.getArea(-10, 10));
        System.out.println(q.getSlope(0));
        q.draw(gc, screenX, screenY);

        Parabola p = new Parabola(1.0, 0.0, 0.0);
        System.out.println(p.toString());
        System.out.println(p.getArea(-10, 10));
        System.out.println(p.getSlope(0));
        p.draw(gc, screenX, screenY);

        Cubic c = new Cubic(0.35, 0.25, -0.5, -1.0, 2.0);
        System.out.println(c.toString());
        System.out.println(c.getArea(-10, 10));
        System.out.println(c.getSlope(0));
        //c.draw(gc, screenX, screenY);

        Arc a = new Arc(4.0, 0.0, -2.0);
        System.out.println(a.toString());
        System.out.println(a.getArea(-10, 10));
        System.out.println(a.getSlope(0));
        //a.draw(gc, screenX, screenY);

        Logarithm l = new Logarithm(1.0, 0.0, 0.0);
        l.setColour(Color.YELLOW);
        System.out.println(l.toString());
        System.out.println(l.getArea(-10, 10));
        System.out.println(l.getSlope(0));
        //l.draw(gc, screenX, screenY);

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    // test method for drawing - you can use this as an example for drawing various types of lines
    private void drawShapes(GraphicsContext gc){
        gc.setStroke(Color.BLUE);
        gc.strokeLine(0, screenY/2, screenX, screenY/2);
        gc.strokeLine(screenX/2, 0, screenX/2, screenY);
        //X-axis and Y-axis
    }
}