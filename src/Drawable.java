import javafx.scene.canvas.GraphicsContext;

public interface Drawable{
    public void draw(GraphicsContext gc, double screenX, double screenY);
}
