package sk.pa3kc.mylibrary.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.pa3kc.mylibrary.matrix.pojo.Vector2f;
import sk.pa3kc.mylibrary.matrix.pojo.Vector3f;

public abstract class ObjLoader {
    private static enum Command {
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

    private ObjLoader() {}

    public static ObjObject loadObjModel(String filename) throws FileNotFoundException {
        if (filename == null) {
            throw new NullPointerException();
        }

        final File file = new File(filename);

        if (!file.exists()) {
            throw new FileNotFoundException(file.getPath() + " does not exists");
        }

        ArrayList<Vector3f> verticies = new ArrayList<Vector3f>();
        ArrayList<Vector2f> textures = new ArrayList<Vector2f>();
        ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
        ArrayList<Integer> indicies = new ArrayList<Integer>();

        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(file));

            String line = br.readLine();

            mainLoop:
            for (; line != null; line = br.readLine()) {
                if (line.startsWith("#") || line.length() == 0) continue;

                final String[] splits = split(line);

                Command command = SUPPORTED_COMMANDS.get(splits[0]);

                switch (command) {
                    case COMMENT: break;
                    case VERTEX:
                        verticies.add(
                            Vector3f.fromStrings(
                                splits[1],
                                splits[2],
                                splits[3]
                            )
                        );
                    break;
                    case VERTEX_TEXTURE:
                        textures.add(
                            Vector2f.fromStrings(
                                splits[1],
                                splits[2]
                            )
                        );
                    break;
                    case VERTEX_NORMAL:
                        normals.add(
                            Vector3f.fromStrings(
                                splits[1],
                                splits[2],
                                splits[3]
                            )
                        );
                    break;
                    case FACE:

                    break;
                }

                switch (splits[0]) {
                    // Vertex position
                    case "v":
                        verticies.add(new Vector3f(
                            Float.parseFloat(splits[1]),
                            Float.parseFloat(splits[2]),
                            Float.parseFloat(splits[3])
                        ));
                    break;

                    // Vertex texture
                    case "vt":
                        textures.add(new Vector2f(
                            Float.parseFloat(splits[1]),
                            Float.parseFloat(splits[2])
                        ));
                    break;

                    // Vertex normal
                    case "vn":
                        normals.add(new Vector3f(
                            Float.parseFloat(splits[1]),
                            Float.parseFloat(splits[2]),
                            Float.parseFloat(splits[3])
                        ));
                    break;

                    // Vertex indicies (faces)
                    case "f":
                        texturesArr = new float[verticies.size() * 2];
                        normalsArr = new float[normals.size() * 3];
                    break mainLoop;
                }
            }

            final int[][] vers = new int[3][3];
            for (; line != null; line = br.readLine()) {
                if (line.startsWith("#") || line.length() == 0) continue;

                final String[] splits = line.split(" ");

                for (int groupIndex = 1; groupIndex < 4; groupIndex++) {
                    final String[] tmp = splits[groupIndex].split("/");

                    for (int valIndex = 0; valIndex < 3; valIndex++) {
                        vers[groupIndex - 1][valIndex] = Integer.parseInt(tmp[valIndex]);
                    }
                }

                for (int groupId = 0; groupId < vers.length; groupId++) {
                    final int currVerPointer = vers[groupId][0] - 1;
                    indicies.add(currVerPointer);

                    final Vector2f currTex = textures.get(vers[groupId][1] - 1);
                    texturesArr[currVerPointer * 2] = currTex.x;
                    texturesArr[currVerPointer * 2 + 1] = 1 - currTex.y;

                    final Vector3f currNormal = normals.get(vers[groupId][2] - 1);
                    normalsArr[currVerPointer * 3] = currNormal.x;
                    normalsArr[currVerPointer * 3 + 1] = currNormal.y;
                    normalsArr[currVerPointer * 3 + 2] = currNormal.z;
                }
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }

        verticiesArr = new float[verticies.size() * 3];
        indiciesArr = new int[indicies.size()];

        int index = 0;
        for (Vector3f vertex : verticies) {
            verticiesArr[index++] = vertex.x;
            verticiesArr[index++] = vertex.y;
            verticiesArr[index++] = vertex.z;
        }

        for (int i = 0; i < indicies.size(); i++) {
            indiciesArr[i] = indicies.get(i);
        }

        return loader.loadModelToVAO(verticiesArr, texturesArr, normalsArr, indiciesArr);
    }

    private static String[] split(String line) {
        final String[] splits = line.split("");

        final List<String> tmp = new ArrayList<String>();

        for (final String split : splits) {
            if (!"".equals(split)) {
                tmp.add(split);
            }
        }

        return tmp.toArray(new String[0]);
    }
}
