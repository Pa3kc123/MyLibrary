package sk.pa3kc.mylibrary.matrix.pojo;

public class Matrix4f {
    private float[] matrix;

    public Matrix4f() {
        this(new float[16]);
    }
    public Matrix4f(float[] matrix) {
        this.matrix = matrix;
    }

    public static Matrix4f identified() {
        final Matrix4f result = new Matrix4f();

        for (int i = 0; i < 4; i++) {
            result.set(i, i, 1f);
        }

        return result;
    }
    public static Matrix4f projectionMatrix(int windowWidth, int windowHeight, float fov, float nearPlane, float farPlane) {
        final float aspectRatio = (float) windowWidth / (float) windowHeight;
        final float yScale = (1f / (float)Math.tan(Math.toRadians(fov / 2f))) * aspectRatio;
        final float xScale = yScale / aspectRatio;
        final float viewDistance = farPlane - nearPlane;

        final Matrix4f result = new Matrix4f();

        result.set(0, 0, xScale);
        result.set(1, 1, yScale);
        result.set(2, 2, -((farPlane + nearPlane) / viewDistance));
        result.set(2, 3, -1);
        result.set(3, 2, -((2 * nearPlane * farPlane) / viewDistance));

        return result;
    }

    public float get(int row, int col) {
        return this.matrix[col + (row * 4)];
    }
    public float[] getMatrix() {
        return this.matrix;
    }
    public float[] getClonedMatrix() {
        return this.matrix.clone();
    }

    public void set(int row, int col, float val) {
        this.matrix[col + (row * 4)] = val;
    }

    public void add(int row, int col, float val) {
        this.matrix[col + (row * 4)] += val;
    }
}
