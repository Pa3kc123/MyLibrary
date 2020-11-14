package sk.pa3kc.mylibrary.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.pa3kc.mylibrary.matrix.pojo.Face;

public abstract class ObjLoader {
    private enum Command {
        COMMENT("#"),
        VERTEX("v"),
        VERTEX_TEXTURE("vt"),
        VERTEX_NORMAL("vn"),
        FACE("f");

        private final String command;
        private Command(String command) {
            this.command = command;
        }
    }

    private static final Map<String, Command> SUPPORTED_COMMANDS = new HashMap<String, Command>() {
        private static final long serialVersionUID = 1L;

        {
            for (final Command command : Command.values()) {
                super.put(command.command, command);
            }
        }
    };

    private static int lineNum = 1;

    private ObjLoader() {}

    public static ObjObject loadObjModel(String filename) throws IOException {
        if (filename == null) {
            throw new NullPointerException();
        }

        final File file = new File(filename);

        if (!file.exists()) {
            throw new FileNotFoundException(file.getPath() + " does not exists");
        }

        ArrayList<Face> faces = new ArrayList<Face>();
        ArrayList<Float> verticies = new ArrayList<Float>();
        ArrayList<Float> textures = new ArrayList<Float>();
        ArrayList<Float> normals = new ArrayList<Float>();

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));

            for (String line = br.readLine(); line != null; line = br.readLine(), lineNum++) {
                if (line.startsWith("#") || line.length() == 0) continue;

                final String[] splits = split(line.trim());

                Command command = SUPPORTED_COMMANDS.get(splits[0]);

                switch (command) {
                    case COMMENT: break;
                    case VERTEX: processVertex3f(verticies, splits); break;
                    case VERTEX_TEXTURE: processVertex2f(textures, splits); break;
                    case VERTEX_NORMAL: processVertex3f(normals, splits); break;
                    case FACE: processFace(faces, splits); break;
                }
            }

            //     switch (splits[0]) {
            //         // Vertex position
            //         case "v":
            //             verticies.add(new Vector3f(
            //                 Float.parseFloat(splits[1]),
            //                 Float.parseFloat(splits[2]),
            //                 Float.parseFloat(splits[3])
            //             ));
            //         break;

            //         // Vertex texture
            //         case "vt":
            //             textures.add(new Vector2f(
            //                 Float.parseFloat(splits[1]),
            //                 Float.parseFloat(splits[2])
            //             ));
            //         break;

            //         // Vertex normal
            //         case "vn":
            //             normals.add(new Vector3f(
            //                 Float.parseFloat(splits[1]),
            //                 Float.parseFloat(splits[2]),
            //                 Float.parseFloat(splits[3])
            //             ));
            //         break;

            //         // Vertex indicies (faces)
            //         case "f":
            //             texturesArr = new float[verticies.size() * 2];
            //             normalsArr = new float[normals.size() * 3];
            //         break mainLoop;
            //     }
            // }

            // final int[][] vers = new int[3][3];
            // for (; line != null; line = br.readLine()) {
            //     if (line.startsWith("#") || line.length() == 0) continue;

            //     final String[] splits = line.split(" ");

            //     for (int groupIndex = 1; groupIndex < 4; groupIndex++) {
            //         final String[] tmp = splits[groupIndex].split("/");

            //         for (int valIndex = 0; valIndex < 3; valIndex++) {
            //             vers[groupIndex - 1][valIndex] = Integer.parseInt(tmp[valIndex]);
            //         }
            //     }

            //     for (int groupId = 0; groupId < vers.length; groupId++) {
            //         final int currVerPointer = vers[groupId][0] - 1;
            //         indicies.add(currVerPointer);

            //         final Vector2f currTex = textures.get(vers[groupId][1] - 1);
            //         texturesArr[currVerPointer * 2] = currTex.x;
            //         texturesArr[currVerPointer * 2 + 1] = 1 - currTex.y;

            //         final Vector3f currNormal = normals.get(vers[groupId][2] - 1);
            //         normalsArr[currVerPointer * 3] = currNormal.x;
            //         normalsArr[currVerPointer * 3 + 1] = currNormal.y;
            //         normalsArr[currVerPointer * 3 + 2] = currNormal.z;
            //     }
            // }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        // verticiesArr = new float[verticies.size() * 3];
        // indiciesArr = new int[indicies.size()];

        // int index = 0;
        // for (Vector3f vertex : verticies) {
        //     verticiesArr[index++] = vertex.x;
        //     verticiesArr[index++] = vertex.y;
        //     verticiesArr[index++] = vertex.z;
        // }

        // for (int i = 0; i < indicies.size(); i++) {
        //     indiciesArr[i] = indicies.get(i);
        // }

        // return loader.loadModelToVAO(verticiesArr, texturesArr, normalsArr, indiciesArr);

        final ObjObject obj = new ObjObject();

        obj.setFaces(faces.toArray(new Face[0]));
        obj.setVerticies(convertWrapperArr(verticies.toArray(new Float[0])));
        obj.setVertexTextures(convertWrapperArr(textures.toArray(new Float[0])));
        obj.setVertexNormals(convertWrapperArr(normals.toArray(new Float[0])));

        return obj;
    }

    private static float[] convertWrapperArr(Float[] arr) {
        final float[] result = new float[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i].floatValue();
        }

        return result;
    }
    private static int[] convertWrapperArr(Integer[] arr) {
        final int[] result = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i].intValue();
        }

        return result;
    }
    private static boolean processVertex2f(List<Float> list, String[] splits) {
        return list.addAll(
            Arrays.asList(
                Float.parseFloat(splits[1]),
                Float.parseFloat(splits[2])
            )
        );
    }
    private static boolean processVertex3f(List<Float> list, String[] splits) {
        return list.addAll(
            Arrays.asList(
                Float.parseFloat(splits[1]),
                Float.parseFloat(splits[2]),
                Float.parseFloat(splits[3])
            )
        );
    }
    private static boolean processFace(List<Face> list, String[] splits) {
        final Face face = new Face(splits.length - 1);

        int checksum = -1;
        for (int i = 1; i < splits.length; i++) {
            final String split = splits[i];
            final String[] vertexSplits = split.split("/");

            if (checksum == -1) {
                checksum = vertexSplits.length;
            } else if (checksum != vertexSplits.length) {
                throw new RuntimeException("Error while parsing obj file at line " + lineNum);
            }

            if (vertexSplits.length == 1) {
                face.setVertexIndex(i - 1, Integer.parseInt(vertexSplits[0]));
            } else {
                face.setVertexIndex(i - 1, Integer.parseInt(vertexSplits[0]));
                face.setVertexTextureIndex(i - 1, Integer.parseInt(vertexSplits[1]));
                face.setVertexNormalIndex(i - 1, Integer.parseInt(vertexSplits[2]));
            }
        }

        return list.add(face);
    }

    private static String[] split(String line) {
        final String[] splits = line.split(" ");

        final List<String> tmp = new ArrayList<String>();

        for (final String split : splits) {
            if (!"".equals(split)) {
                tmp.add(split);
            }
        }

        return tmp.toArray(new String[0]);
    }
}
