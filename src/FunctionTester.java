import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FunctionTester extends Application{

    public static void main(String[] args){
        launch(args);
    }

    double screenX = 600, screenY = 600;
    Group root = new Group();
    Scene scene = new Scene(root);
    Group newG = new Group();
    Scene newS = new Scene(newG, 400, 300);

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Drawing Functions Test");
        Canvas canvas = new Canvas(screenX, screenY);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.strokeLine(0, screenY/2, screenX, screenY/2);
        gc.strokeLine(screenX/2, 0, screenX/2, screenY);
        root.getChildren().add(canvas);
/*
        Button add = new Button("Add new function");
        add.setTranslateX(500);
        add.setTranslateY(500);
        add.setOnAction(event -> {
            Stage another = new Stage();
            another.setScene(newS);
            another.show();
        });

        ComboBox<String> CB = new ComboBox<>();
        CB.getItems().addAll("Linear", "Quadratic", "Parabola", "Cubic", "Arc", "Logarithm");
        Button confirmType = new Button("Enter");
        confirmType.setTranslateX(100);
        newG.getChildren().addAll(CB, confirmType);

        TextField typeM = new TextField();
        typeM.setPrefWidth(50);
        Label nameM = new Label("m: ");
        TextField typeX = new TextField();
        typeX.setPrefWidth(50);
        Label nameX = new Label("x1: ");
        TextField typeA = new TextField();
        typeA.setPrefWidth(50);
        Label nameA = new Label("a: ");
        TextField typeB = new TextField();
        typeB.setPrefWidth(50);
        Label nameB = new Label("b: ");
        TextField typeC = new TextField();
        typeC.setPrefWidth(50);
        Label nameC = new Label("c: ");
        TextField typeD = new TextField();
        typeD.setPrefWidth(50);
        Label nameD = new Label("d: ");

        confirmType.setOnAction(event -> {
            newG.getChildren().clear();
            newG.getChildren().addAll(CB, confirmType);
            String type = CB.getValue();
            HBox texts = new HBox();
            texts.setTranslateX(50);
            texts.setTranslateY(100);
            if (type.equals("Linear")){
                texts.getChildren().addAll(nameM, typeM, nameX, typeX, nameB, typeB);
                //Also add default form of function
            }else if (type.equals("Parabola")){
                texts.getChildren().addAll(nameA, typeA, nameB, typeB, nameX, typeX);
            }else if (type.equals("Quadratic")){
                texts.getChildren().addAll(nameA, typeA, nameC, typeC, nameX, typeX);
            }else if (type.equals("Cubic")){
                texts.getChildren().addAll(nameA, typeA, nameB, typeB, nameC, typeC, nameD, typeD, nameX, typeX);
            }
            newG.getChildren().add(texts);
        });
*/
        Linear line = new Linear(-2.0, -100.0, 0.0);
        if (line.getColour() == Color.BLACK) line.setColour(Color.BROWN);
        line.setName("Line 1");
        System.out.println(line.getName());
        System.out.println(line.toString());
        System.out.println(line.getArea(-10, 10));
        System.out.println(line.getSlope(0));
        //line.draw(gc, screenX, screenY);

        Quadratic q = new Quadratic(0.25, -0.5, -1.0, 0.0);
        q.setColour(Color.PURPLE);
        q.setDomain(-6, 10);
        System.out.println(q.toString());
        System.out.println(q.getArea(-10, 10));
        System.out.println(q.getSlope(0));
        //q.draw(gc, screenX, screenY);

        Parabola p = new Parabola(1.0, 0.0, 0.0);
        System.out.println(p.toString());
        System.out.println(p.getArea(-10, 10));
        System.out.println(p.getSlope(0));
        //p.draw(gc, screenX, screenY);

        Cubic c = new Cubic(0.35, 0.25, -0.5, -1.0, 2.0);
        System.out.println(c.toString());
        System.out.println(c.getArea(-10, 10));
        System.out.println(c.getSlope(0));
        //c.draw(gc, screenX, screenY);

        Arc a = new Arc(250.0, 0.0, -2.0);
        a.setDomain(-300, 300);
        a.setColour(Color.FUCHSIA);
        System.out.println(a.toString());
        System.out.println(a.getArea(-10, 10));
        System.out.println(a.getSlope(0));
        a.draw(canvas);

        Logarithm l = new Logarithm(1.0, 0.0, 0.0);
        l.setColour(Color.YELLOW);
        System.out.println(l.toString());
        System.out.println(l.getArea(-10, 10));
        System.out.println(l.getSlope(0));
        l.draw(canvas);

        //root.getChildren().add(add);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
