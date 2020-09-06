package ai.kitt.vnest.util.event;

public class RmsChangeEvent {
    int radius;

    public RmsChangeEvent(int i) {
        this.radius = i;
    }

    public int getRadius() {
        return this.radius;
    }
}
