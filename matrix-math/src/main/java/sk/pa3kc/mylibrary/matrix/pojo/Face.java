package sk.pa3kc.mylibrary.matrix.pojo;

public class Face {
    private int[] vertexIndexes;
    private int[] vertexTextureIndexes;
    private int[] vertexNormalIndexes;

    public Face(int count) {
        this.vertexIndexes = new int[count];
        this.vertexTextureIndexes = new int[count];
        this.vertexNormalIndexes = new int[count];
    }

    public int[] getVertexIndexes() {
        return this.vertexIndexes;
    }
    public int[] getVertexTextureIndexes() {
        return this.vertexTextureIndexes;
    }
    public int[] getVertexNormalIndexes() {
        return this.vertexNormalIndexes;
    }

    public void setVertexIndex(int index, int value) {
        this.vertexIndexes[index] = value - 1;
    }
    public void setVertexTextureIndex(int index, int value) {
        this.vertexTextureIndexes[index] = value - 1;
    }
    public void setVertexNormalIndex(int index, int value) {
        this.vertexNormalIndexes[index] = value - 1;
    }
}
