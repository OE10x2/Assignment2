import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Example of using the Canvas and GraphicsContext class within JavaFX
 *
 */

public class FunctionTester extends Application {

    Canvas canvas = new Canvas(600, 600);

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setTitle("Drawing Functions Test");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    // test method for drawing - you can use this as an example for drawing various types of lines
    private void drawShapes(GraphicsContext gc) {
        Linear line = new Linear(1.0, 1.0, 0.0);
        //line.draw(canvas);

        Quadratic q = new Quadratic(0.25, -0.5, -1.0, 0.0);
        //q.draw(canvas);

        Parabola p = new Parabola(1.0, 0.0, 0.0);
        //p.draw(canvas);

        Cubic c = new Cubic(0.35, 0.25, -0.5, -1.0, 2.0);
        //c.draw(canvas);

        Arc a = new Arc(20.0, 50.0, 20.0);
        a.setColour(Color.TURQUOISE);
        a.setDomain(-300, 300);
        a.draw(canvas);

        Logarithm l = new Logarithm(1.0, 0.0, 0.0);
        //l.draw(canvas);
    }

}