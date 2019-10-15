import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FunctionTester extends Application{

    public static void main(String[] args){
        launch(args);
    }

    double screenX = 600, screenY = 600;
    double tempX = 0, tempY = 0;
    Group root = new Group();
    Scene scene = new Scene(root);
    Group newG = new Group();
    Scene newS = new Scene(newG, 400, 300);

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Drawing Functions Test");
        Canvas canvas = new Canvas(screenX, screenY);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeLine(0, screenY/2, screenX, screenY/2);
        gc.strokeLine(screenX/2, 0, screenX/2, screenY);
        root.getChildren().add(canvas);

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
        Button confirmFunc = new Button("Draw Function!");

        TextField type1 = new TextField();
        type1.setPrefWidth(50);
        Label name1 = new Label();
        TextField type2 = new TextField();
        type2.setPrefWidth(50);
        Label name2 = new Label();
        TextField type3 = new TextField();
        type3.setPrefWidth(50);
        Label name3 = new Label();
        TextField type4 = new TextField();
        type4.setPrefWidth(50);
        Label name4 = new Label();
        TextField type5 = new TextField();
        type5.setPrefWidth(50);
        Label name5 = new Label();

        confirmType.setOnAction(event -> {
            newG.getChildren().clear();
            newG.getChildren().addAll(CB, confirmType);
            String type = CB.getValue();
            HBox texts = new HBox();
            texts.setTranslateX(50);
            texts.setTranslateY(100);
            if (type.equals("Linear")){
                name1.setText("m: ");
                name2.setText("b: ");
                name3.setText("x1: ");
                Text description = new Text("f(x) = m(x - x1) + b");
                newG.getChildren().add(description);
                texts.getChildren().addAll(name1, type1, name2, type2, name3, type3);
            }else if (type.equals("Parabola")){
                texts.getChildren().addAll();
            }else if (type.equals("Quadratic")){
                texts.getChildren().addAll();
            }else if (type.equals("Cubic")){
                texts.getChildren().addAll();
            }else if (type.equals("Arc")){
                texts.getChildren().addAll();
            }else if (type.equals("Logarithm")){
                texts.getChildren().addAll();
            }
            newG.getChildren().add(texts);
        });

        Linear line = new Linear(1.0, 1.0, 0.0);
        if (line.getColour() == Color.BLACK) line.setColour(Color.BROWN);
        line.setName("Line 1");
        System.out.println(line.getName());
        System.out.println(line.toString());
        System.out.println(line.getArea(-10, 10));
        System.out.println(line.getSlope(0));
        line.draw(canvas);

        Quadratic q = new Quadratic(0.25, -0.5, -1.0, 0.0);
        q.setColour(Color.PURPLE);
        q.setDomain(-6, 10);
        System.out.println(q.toString());
        System.out.println(q.getArea(-10, 10));
        System.out.println(q.getSlope(0));
        q.draw(canvas);

        Parabola p = new Parabola(1.0, 0.0, 0.0);
        System.out.println(p.toString());
        System.out.println(p.getArea(-10, 10));
        System.out.println(p.getSlope(0));
        p.draw(canvas);

        Cubic c = new Cubic(0.35, 0.25, -0.5, -1.0, 2.0);
        System.out.println(c.toString());
        System.out.println(c.getArea(-10, 10));
        System.out.println(c.getSlope(0));
        c.draw(canvas);

        Arc a = new Arc(4.0, 0.0, -2.0);
        a.setDomain(-300, 300); //TESTING ONLY
        a.setColour(Color.FUCHSIA);
        System.out.println(a.toString());
        System.out.println(a.getArea(-10, 10));
        System.out.println(a.getSlope(0));
        a.draw(canvas);

        Logarithm l = new Logarithm(1.0, 0.0, 0.0);
        System.out.println(l.toString());
        System.out.println(l.getArea(-10, 10));
        System.out.println(l.getSlope(0));

        //Zoom button
        Image plusSign = new Image("Plus.png");
        ImageView editPlus = new ImageView(plusSign);
        editPlus.setPreserveRatio(true);
        editPlus.setFitHeight(30);
        Button zoomIn = new Button();
        zoomIn.setGraphic(editPlus);
        zoomIn.setTranslateX(500);
        zoomIn.setTranslateY(400);
        zoomIn.setOnAction(event -> {
            l.setZoom(l.getZoom() + 1);
            l.draw(canvas);
        });

        Image MinusSign = new Image("Minus.png");
        ImageView editMinus = new ImageView(MinusSign);
        editMinus.setPreserveRatio(true);
        editMinus.setFitWidth(30);
        Button zoomOut = new Button();
        zoomOut.setGraphic(editMinus);
        zoomOut.setTranslateX(500);
        zoomOut.setTranslateY(455);
        zoomOut.setOnAction(event -> {
            if (l.getZoom() > 1){
                l.setZoom(l.getZoom() - 1);
                l.draw(canvas);
            }
        });

        root.getChildren().addAll(zoomIn, zoomOut);

        canvas.setOnMousePressed(event -> {
            tempX = event.getX();
            tempY = event.getY();
        });

        canvas.setOnMouseDragged(event -> {
            l.setX(l.getX() + event.getX() - tempX);
            l.setY(l.getY() - event.getY() + tempY);
            l.draw(canvas);
        });

        //root.getChildren().add(add);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}