import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Cubic extends Function implements Calculations, Drawable{

    protected double a;
    protected double b;
    protected double c;
    protected double d;
    protected double x1;

    public Cubic(double a, double b, double c, double d, double x1){
        super();
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.x1 = x1;
        super.setName("Cubic");
    }

    @Override
    public double val(double x){
        return a * Math.pow((x - x1), 3) + b * Math.pow((x - x1), 2) + c * (x - x1) + d;
    }

    @Override
    public boolean undefined(double x){
        return x < super.getStartDomain() || x > super.getEndDomain();
    }

    @Override
    public double getArea(double x_start, double x_end){
        double round = 1000; //3 decimal places
        double i = x_start, area = 0;
        double delta = (x_end - x_start) / round;
        //Ensure that the function is always divided into 1000 parts,
        //So that this function would work for any domain length
        while (i <= x_end){
            if (!undefined(i)) area += val(i) * delta;
            //Find the area of the approximated rectangle only if the point is defined
            i += delta;
        }
        return area;
    }

    @Override
    public double getSlope(double x){
        double delta = 1e-9;
        //Make the delta as small as possible to make the slope more accurate
        if (!undefined(x)) return (val(x + delta) - val(x - delta)) / (delta * 2);
        //Check if the value is defined (real) at the point i
        return Double.NaN;
        //If the function is undefined at point x, return undefined
    }

    @Override
    public String toString(){
        String name = "", xTerm;
        //Handle x1
        if (x1 == 0) xTerm = "x";
        else if (x1 > 0) xTerm = "(x" + -x1 + ")";
        else xTerm = "(x+" + -x1 + ")";
        //Handle a
        if (a == 1) name += xTerm + "^3";
        else if (a == -1) name += "-" + xTerm + "^3";
        else if (a != 0) name += a + xTerm + "^3";
        //Handle b
        if (b == 1){
            if (a != 0) name += "+";
            name += xTerm + "^2";
        }else if (b == -1) name += "-" + xTerm + "^2";
        else if (b > 0){
            if (a != 0) name += "+";
            name += b + xTerm + "^2";
        }else if (b < 0) name += b + xTerm + "^2";
        //Handle c
        if (c == 1){
            if (a != 0 || b != 0) name += "+";
            name += xTerm + "^2";
        }else if (c == -1) name += "-" + xTerm;
        else if (c > 0){
            if (a != 0 || b != 0) name += "+";
            name += c + xTerm;
        }else if (c < 0) name += c + xTerm;
        //Handle d
        if (d > 0){
            if (a != 0 || b != 0 || c != 0) name += "+";
            name += d;
        }else if (d < 0) name += d;
        //Handle all zeroes case
        if (a == 0 && b == 0 && c == 0 && d == 0) name = "0";
        return name;
    }

    @Override
    public void draw(Canvas canvas){
        double screenX = canvas.getWidth(), screenY = canvas.getHeight();
        double domain1 = super.getStartDomain(), domain2 = super.getEndDomain();
        double round = 100, roundIn = 1e9; //2 and 9 decimal places
        double axisWidth = 2; //For drawing the axis
        double numLabel = 10; //Number of labels on the axis
        double labelLength = 5, labelGapS = 20, labelGapL = 30;
        double roundLabelX = 100, roundLabelY = 10;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(super.getColour());

        double delta = Math.abs(domain2 - domain1) / round;
        double curX = domain1;
        double range2 = -Double.MAX_VALUE, range1 = Double.MAX_VALUE;
        while (curX <= domain2){
            if (undefined(curX)){
                curX = Math.round((curX + delta) * roundIn) / roundIn;
                continue;
            }
            range2 = Math.max(range2, val(curX));
            range1 = Math.min(range1, val(curX));
            curX = Math.round((curX + delta) * roundIn) / roundIn;
        }
        //Loop through all possible values for domain and find the maximum & minimum range

        double ratioX = screenX / (domain2 - domain1);
        double adjustX = (domain2 + domain1) / 2;
        double ratioY = screenY / (range2 - range1);
        double adjustY = (range2 + range1) / 2;

        curX = domain1;
        while (curX <= domain2){
            double prevX = curX;
            curX = Math.round((curX + delta) * roundIn) / roundIn;
            //Round off the double precision errors while saving as many decimal places as possible
            double startX = ratioX * (prevX - adjustX) + screenX / 2;
            double startY = ratioY * (adjustY - val(prevX)) + screenY / 2;
            double endX = ratioX * (curX - adjustX) + screenX / 2;
            double endY = ratioY * (adjustY - val(curX)) + screenY / 2;
            //Adjust each point when displaying on the screen
            gc.strokeLine(startX, startY, endX, endY);
        }

        //X-axis and Y-axis
        gc.setLineWidth(axisWidth);
        gc.setStroke(Color.BLACK);
        double Xaxis = screenX / (range2 - range1) * range2;
        gc.strokeLine(0, Xaxis, screenX, Xaxis);
        double Yaxis = screenY / (domain1 - domain2) * domain1;
        gc.strokeLine(Yaxis, 0, Yaxis, screenY);

        //Labels
        double domainLabel = (domain2 - domain1) / numLabel;
        double rangeLabel = (range2 - range1) / numLabel;
        gc.setTextAlign(TextAlignment.CENTER);
        //Calculates the difference between consecutive labels
        for (int i = 1; i < numLabel; i++){
            double posX = domain1 + domainLabel * i; //The x-value being displayed
            double screenPosX = screenX / numLabel * i; //The adjusted x-coordinate on the canvas
            double posY = range1 + rangeLabel * (numLabel-i); //The y-value being displayed
            double screenPosY = screenY / numLabel * i; //The adjusted y-coordinate on the canvas
            String labelX = Double.toString(Math.round(posX * roundLabelX) / roundLabelX);
            String labelY = Double.toString(Math.round(posY * roundLabelY) / roundLabelY);

            if (range1 >= 0){
                //The y-axis is not on the screen; it is to the left of the screen
                gc.fillText(labelX, screenPosX, screenY-labelGapS);
                gc.strokeLine(screenPosX, screenY, screenPosX, screenY-labelLength);
            }else if (range2 <= 0){
                //The y-axis is not on the screen; it is to the right of the screen
                gc.fillText(labelX, screenPosX, labelGapS);
                gc.strokeLine(screenPosX, 0, screenPosX, labelLength);
            }else{
                //The y-axis is being displayed on the screen
                gc.strokeLine(screenPosX, Xaxis+labelLength, screenPosX, Xaxis-labelLength);
                //This line is the marker on the axis
                if (Xaxis > screenY / 2) gc.fillText(labelX, screenPosX, Xaxis-labelGapS);
                    //This means that the y-axis would be on the right side of the screen.
                    //In this case, display the values to the left of the axis to completely show the values
                else gc.fillText(labelX, screenPosX, Xaxis+labelGapS);
                //This means that the y-axis is on the left side of the screen.
                //In this case, display the values to the right of the axis.
            }

            //Similarly, I repeat the process for the x-axis values. All the checks are identical.
            if (domain1 >= 0){
                gc.setTextAlign(TextAlignment.LEFT);
                gc.fillText(labelY, labelGapS, screenPosY);
                gc.strokeLine(0, screenPosY, labelLength, screenPosY);
            }else if (domain2 <= 0){
                gc.setTextAlign(TextAlignment.RIGHT);
                gc.fillText(labelY, screenX-labelGapS, screenPosY);
                gc.strokeLine(screenX, screenPosY, screenX-labelLength, screenPosY);
            }else{
                gc.strokeLine(Yaxis+labelLength, screenPosY, Yaxis-labelLength, screenPosY);
                if (Yaxis > screenX / 2) gc.fillText(labelY, Yaxis-labelGapL, screenPosY);
                else gc.fillText(labelY, Yaxis+labelGapL, screenPosY);
            }
        }
    }
}