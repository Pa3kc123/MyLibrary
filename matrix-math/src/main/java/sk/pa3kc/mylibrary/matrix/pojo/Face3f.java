package sk.pa3kc.mylibrary.matrix.pojo;

import java.util.ArrayList;
import java.util.List;

public class Face3f {
    private List<Integer> vertexIndexes;
    private List<Integer> vertexTextureIndexes;
    private List<Integer> vertexNormalIndexes;

    public Face3f() {
        this.vertexIndexes = new ArrayList<Integer>();
        this.vertexTextureIndexes = new ArrayList<Integer>();
        this.vertexNormalIndexes = new ArrayList<Integer>();
    }

    public void add(int vertexIndex, int vertexTextureIndex, int vertexNormalIndex) {
        this.vertexIndexes.add(vertexIndex);
        this.vertexTextureIndexes.add(vertexTextureIndex);
        this.vertexNormalIndexes.add(vertexTextureIndex);
    }
    public float[] get(int index) {
        return
    }
    public void remove(int vertexIndex, int vertexTextureIndex, int vertexNormalIndex) {
        this.vertexIndexes.remove(vertexIndex);
        this.vertexTextureIndexes.remove(vertexTextureIndex);
        this.vertexNormalIndexes.remove(vertexTextureIndex);
    }
}
