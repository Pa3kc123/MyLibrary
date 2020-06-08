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
    public static float[] flattenVectors(Vector2f... vecs) {
        final float[] result = new float[vecs.length * 2];

        int i = 0;
        for (Vector2f vec : vecs) {
            result[i++] = vec.x;
            result[i++] = vec.y;
        }

        return result;
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
