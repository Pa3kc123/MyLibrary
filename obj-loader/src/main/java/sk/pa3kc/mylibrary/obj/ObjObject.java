package sk.pa3kc.mylibrary.obj;

import sk.pa3kc.mylibrary.matrix.pojo.Vector2f;
import sk.pa3kc.mylibrary.matrix.pojo.Vector3f;

public class ObjObject {
    private Vector3f[] verticies;
    private Vector2f[] vertexTextures;
    private Vector3f[] vertexNormals;
    private int[] indicies;

    public ObjObject() {}
    public ObjObject(
        Vector3f[] verticies,
        Vector2f[] vertexTextures,
        Vector3f[] vertexNormals,
        int[] indicies
    ) {
        this.verticies = verticies;
        this.vertexTextures = vertexTextures;
        this.vertexNormals = vertexNormals;
        this.indicies = indicies;
        this.verticies = verticies;
    }

    public Vector3f[] getVerticies() {
        return this.verticies;
    }
    public Vector2f[] getVertexTextures() {
        return this.vertexTextures;
    }
    public Vector3f[] getVertexNormals() {
        return this.vertexNormals;
    }
    public int[] getIndicies() {
        return this.indicies;
    }

    public void setVerticies(Vector3f[] verticies) {
        this.verticies = verticies;
    }
    public void setVertexTextures(Vector2f[] vertexTextures) {
        this.vertexTextures = vertexTextures;
    }
    public void setVertexNormals(Vector3f[] vertexNormals) {
        this.vertexNormals = vertexNormals;
    }
    public void setIndicies(int[] indicies) {
        this.indicies = indicies;
    }
}
