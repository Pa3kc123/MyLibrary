package sk.pa3kc.mylibrary.obj;

import sk.pa3kc.mylibrary.matrix.pojo.Face;

public class ObjObject {
    private Face[] faces;
    private float[] verticies;
    private float[] vertexTextures;
    private float[] vertexNormals;

    public ObjObject() {}

    public Face[] getFaces() {
        return this.faces;
    }
    public float[] getVerticies() {
        return this.verticies;
    }
    public float[] getVertexTextures() {
        return this.vertexTextures;
    }
    public float[] getVertexNormals() {
        return this.vertexNormals;
    }

    public void setFaces(Face[] faces) {
        this.faces = faces;
    }
    public void setVerticies(float[] verticies) {
        this.verticies = verticies;
    }
    public void setVertexTextures(float[] vertexTextures) {
        this.vertexTextures = vertexTextures;
    }
    public void setVertexNormals(float[] vertexNormals) {
        this.vertexNormals = vertexNormals;
    }
}
