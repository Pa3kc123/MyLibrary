package sk.pa3kc.mylibrary.matrix.pojo;

public class Vector2f {
    public float x;
    public float y;

    public Vector2f() {
        this(0f, 0f);
    }
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2f fromStrings(String x, String y) {
        return new Vector2f(
            Float.parseFloat(x),
            Float.parseFloat(y)
        );
    }
    public static Vector2f negate(Vector2f vec) {
        return new Vector2f(-vec.x, -vec.y);
    }

    public Vector2f negate() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    @Override
    public String toString() {
        return "[ " + this.x + " | " + this.y + " ]";
    }
}
