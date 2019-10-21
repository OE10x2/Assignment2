import javafx.scene.paint.Color;

public abstract class Function{

    protected double x1, x2;
    protected Color col;
    protected String name;

    public Function(double x1, double x2){
        this.x1 = x1;
        this.x2 = x2;
    }

    public Function(){
        this.x1 = -300;
        this.x2 = 300;
    }

    public abstract String toString();

    public void setDomain(double x1, double x2){
        if (x1 == x2) return;
        //Ensure that the starting domain is not equal to the ending domain
        if (x1 > x2){
            //Given starting domain is larger than ending domain;
            //Switch the order in this case
            this.x1 = x2;
            this.x2 = x1;
        }else{
            this.x1 = x1;
            this.x2 = x2;
        }
    }

    public double getStartDomain(){
        return this.x1;
    }

    public double getEndDomain(){
        return this.x2;
    }

    public void setColour(Color col){
        this.col = col;
    }

    public Color getColour(){
        return col;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}