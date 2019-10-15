public class Adjust extends Function{
    protected double zoom;
    protected double shiftX;
    protected double shiftY;

    public Adjust(){
        super();
        this.zoom = 1.0;
        this.shiftX = 0.0;
        this.shiftY = 0.0;
    }

    @Override
    public String toString(){
        return null;
    }

    public double getZoom(){
        return this.zoom;
    }

    public void setZoom(double zoom){
        this.zoom = zoom;
    }

    public double getX(){
        return this.shiftX;
    }

    public void setX(double x){
        this.shiftX = x;
    }

    public double getY(){
        return this.shiftY;
    }

    public void setY(double y){
        this.shiftY = y;
    }
}
