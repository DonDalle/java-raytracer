package com.daleszynski.raytracer.java.loader;

import com.daleszynski.raytracer.java.math.Normal3;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.texture.TexCoord2D;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {

    /**
     * Ein Kommentar im OBJ Format
     */
    public static final String COMMENT = "#";

    /**
     * Ein Eckpunkt im OBJ Format
     */
    public static final String VERTEX = "v";

     /**
     * Eine Normale für einen Eckpunkt im OBJ Format
     */
    public static final String VERTEX_NORMAL = "vn";

    /**
     * Eine Texturkorrdinate im OBJ Format
     */
    public static final String VERTEX_TEXTURE = "vt";

    /**
     * Eine Seize im OBJ Format
     */
    public static final String FACE = "f";

    /**
     * Liste mit Eckpunkten
     */
    public final List<Point3> vertices = new ArrayList<>();

    /**
     * Liste mit Normalen für die Eckpunkte, kann leer sein
     */
    public final List<Normal3> verticesNormals = new ArrayList<>();

    public final List<TexCoord2D> verticesTextures = new ArrayList<>();

    /**
     * Liste mit den Seiten als String Format. (wie in der OBJ Spezifikation, ohne führendes "f")
     */
    public final List<String> faces = new ArrayList<>();

    public void parse(final String objName) throws IOException{
        if (objName == null) {
            throw new IllegalArgumentException("objName must not be null");
        }
        final File objFile = new File (objName);
        final FileReader objFileReader = new FileReader(objFile);
        final BufferedReader reader = new BufferedReader(objFileReader);

        while(true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            final String prefix = line.split("\\s+")[0];
            if (line.length() != 0 && !prefix.equals(COMMENT)) {
                switch (prefix) {
                    case VERTEX:
                        processVertex(line);
                        break;
                    case VERTEX_NORMAL:
                        processVertexNormal(line);
                        break;
                    case VERTEX_TEXTURE:
                        processVertexTexture(line);
                        break;
                    case FACE:
                        processFace(line);
                        break;
                    default:
                        System.out.println("Unsupported type: " + prefix);
                        break;
                }
            }
        }
    }

    /**
     * Bearbeitet ein Face
     * @param line das Face
     */
    private void processFace(String line) {
        line = line.replaceAll("\\s+", " ").replace("f ", "");
        faces.add(line);

    }


    /**
     * behandelt eine textur koordinate
     * @param line die textur koordinate
     */
    @SuppressWarnings("unused")
    private void processVertexTexture(String line) {
        final String[] uv =  line.split("\\s+");
        final double u = Double.parseDouble(uv[1]);
        final double v = Double.parseDouble(uv[2]);
        verticesTextures.add(new TexCoord2D(u, v));

    }

    /**
     * Behandelt eine Normale
     * @param line die Normale
     */
    private void processVertexNormal(String line) {
        final String[] xyz =  line.split("\\s+");
        final double x = Double.parseDouble(xyz[1]);
        final double y = Double.parseDouble(xyz[2]);
        final double z = Double.parseDouble(xyz[3]);
        verticesNormals.add(new Normal3(x, y, z));
    }

    /**
     * Behandelt einen Eckpunkt
     * @param line der Eckpunkt
     */
    private void processVertex(String line) {
        final String[] xyz =  line.split("\\s+");
        final double x = Double.parseDouble(xyz[1]);
        final double y = Double.parseDouble(xyz[2]);
        final double z = Double.parseDouble(xyz[3]);
        vertices.add(new Point3(x,y,z));
    }
}
