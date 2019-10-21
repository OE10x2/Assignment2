import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Arc extends Function implements Calculations, Drawable{

    protected double r;
    protected double xcenter;
    protected double ycenter;

    public Arc(double r, double xcenter, double ycenter){
        super();
        this.r = r;
        this.xcenter = xcenter;
        this.ycenter = ycenter;
        super.setName("Arc");
    }

    @Override
    public double val(double x){
        return Math.sqrt(r*r - Math.pow((x - xcenter), 2)) + ycenter;
    }

    @Override
    public String toString(){
        String name = "sqrt(", xTerm = "";
        //Handle xcenter
        if (xcenter == 0.0) xTerm = "x^2";
        else if (xcenter > 0) xTerm = "(x" + -xcenter + ")^2";
        else if (xcenter < 0) xTerm = "(x+" + -xcenter + ")^2";
        //Handle r
        if (r != 0) name += Double.toString(r * r);
        name += "-" + xTerm + ")";
        //Handle ycenter
        if (ycenter > 0) name += "+" + ycenter;
        else if (ycenter < 0) name += ycenter;
        return name;
    }

    @Override
    public boolean undefined(double x){
        return x < super.getStartDomain() || x > super.getEndDomain() || r * r < Math.pow(x - xcenter, 2);
        //Case 1: the given point x is not within the given domain
        //Case 2: the given point is within the given domain, but is not defined in the actual function
    }

    @Override
    public double getArea(double x_start, double x_end){
        if (x_start > x_end) return 0;
        //If the given start is larger than end, then the input is invalid
        double realDomain1, realDomain2;
        if (this.r > 0){
            realDomain1 = this.xcenter - this.r;
            realDomain2 = this.xcenter + this.r;
        }else{
            realDomain1 = this.xcenter + this.r;
            realDomain2 = this.xcenter - this.r;
        }
        realDomain1 = Math.max(realDomain1, super.getStartDomain());
        realDomain2 = Math.min(realDomain2, super.getEndDomain());
        //Find the actual defined domain
        if (x_end < realDomain1 || x_start > realDomain2) return 0;
        //If the given domain does not have an overlap, then the input is invalid
        double i = x_start, area = 0;
        double round = 1000;
        //Ensure that the function is always divided into 1000 parts,
        //So that this function would work for any domain length
        double delta = (x_end - Math.max(x_start, this.x1)) / round;
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
    public void draw(Canvas canvas){
        double screenX = canvas.getWidth(), screenY = canvas.getHeight();
        double domain1 = super.getStartDomain(), domain2 = super.getEndDomain();
        double round = 100; //2 decimal places
        double roundIn = 1e9; //9 decimal places
        double axisWidth = 2; //For drawing the axis
        double numLabel = 10; //Number of labels on the axis
        double labelLength = 5, labelGapS = 20, labelGapL = 30;
        double roundLabelX = 100, roundLabelY = 10;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(super.getColour());

        //OBJECTIVE: Find real domain, compare with setup domain
        double realDomain1, realDomain2;
        if (this.r > 0){
            realDomain1 = this.xcenter - this.r;
            realDomain2 = this.xcenter + this.r;
        }else{
            realDomain1 = this.xcenter + this.r;
            realDomain2 = this.xcenter - this.r;
        }
        //Ensure that realDomain1 < realDomain2

        if (domain2 <= realDomain1 || domain1 >= realDomain2){
            //Then the given domain does not overlap with the actual defined domain
            gc.setStroke(Color.BLACK); //For drawing the axis
            gc.setTextAlign(TextAlignment.CENTER); //Make the text appear centered about the given coordinates
            gc.strokeLine(0, screenY/2, screenX, screenY/2); //X-axis
            double posX = domain1;
            for (int i = 1; i < numLabel; i++){
                posX += (domain2 - domain1) / numLabel;
                double screenPosX = screenX / numLabel * i;
                String labelX = Double.toString(Math.round(posX * roundLabelX) / roundLabelX);
                gc.fillText(labelX, screenPosX, screenY/2+labelGapS);
                gc.strokeLine(screenPosX, screenY/2+labelLength, screenPosX, screenY/2-labelLength);
            }
            return;
        }

        //Compare with setup domain to find the final defined & intended domain
        realDomain1 = Math.max(realDomain1, domain1);
        realDomain2 = Math.min(realDomain2, domain2);

        double delta = Math.abs(realDomain2 - realDomain1) / round;
        //Find the length of the domain, then divide into 1000 parts for better accuracy
        double curX = domain1;
        double range2 = -Double.MAX_VALUE, range1 = Double.MAX_VALUE;
        while (curX <= domain2){
            if (!undefined(curX)){
                range2 = Math.max(range2, val(curX));
                range1 = Math.min(range1, val(curX));
            }
            curX = Math.round((curX + delta) * roundIn) / roundIn;
        }
        //Loop through all possible values for domain and find the maximum & minimum range

        double ratioX = screenX / (domain2 - domain1);
        double adjustX = (domain2 + domain1) / 2;
        double ratioY = screenY / (range2 - range1);
        double adjustY = (range2 + range1) / 2;

        curX = realDomain1;
        while (curX <= realDomain2){
            double prevX = curX;
            curX = Math.round((curX + delta) * roundIn) / roundIn;
            //Round off the double precision errors while saving as many decimal places as possible
            double startX = ratioX * (prevX - adjustX) + screenX/2;
            double startY = ratioY * (adjustY - val(prevX)) + screenY/2;
            double endX = ratioX * (curX - adjustX) + screenX/2;
            double endY = ratioY * (adjustY - val(curX)) + screenY/2;
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