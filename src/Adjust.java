public class Adjust extends Function{
    protected double shiftX;
    protected double shiftY;
    protected double zoom;

    public Adjust(){
        super();
        this.shiftX = 0.0;
        this.shiftY = 0.0;
        this.zoom = 1.0;
    }

    @Override
    public String toString(){
        return null;
    }

    public double getZ(){
        return this.zoom;
    }

    public void setZ(double z){
        this.zoom = z;
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
