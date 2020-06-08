package sk.pa3kc.mylibrary.matrix.pojo;

public class Vector3f {
    public float x;
    public float y;
    public float z;

    public Vector3f() {
        this(0f, 0f, 0f);
    }
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3f fromStrings(String x, String y, String z) {
        return new Vector3f(
            Float.parseFloat(x),
            Float.parseFloat(y),
            Float.parseFloat(z)
        );
    }
    public static Vector3f negate(Vector3f vec) {
        return new Vector3f(-vec.x, -vec.y, -vec.z);
    }
    public static float[] flattenVectors(Vector3f... vecs) {
        final float[] result = new float[vecs.length * 3];

        int i = 0;
        for (Vector3f vec : vecs) {
            result[i++] = vec.x;
            result[i++] = vec.y;
            result[i++] = vec.z;
        }

        return result;
    }

    public Vector3f negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    @Override
    public String toString() {
        return "[ " + this.x + " | " + this.y + " | " + this.z + " ]";
    }
}
