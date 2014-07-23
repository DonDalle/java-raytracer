package com.daleszynski.raytracer.java.geometry;


import com.daleszynski.raytracer.java.loader.FileExtensionException;
import com.daleszynski.raytracer.java.loader.OBJLoader;
import com.daleszynski.raytracer.java.material.Material;
import com.daleszynski.raytracer.java.math.Ray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;



/**
 * Lädt ein Modell aus einer Datei, zur Zeit wird nur das OBJ Format unterstützt.
 */
public class ShapeFromFile extends Geometry {

    /**
     * Das Triangle Mesh
     */
    private Geometry mesh;

    /**
     * Lädt Datei
     * @param fileName Dateiname
     * @param material Material
     * @throws IOException bei ungültigem Dateiformat, oder wenn die Datei nicht gefunden wurde
     */
    public ShapeFromFile(String fileName, Material material) throws IOException {
        super(material);

        final File f = new File(fileName);
        if (!f.exists()) {
            throw new FileNotFoundException(fileName);
        }

        if (fileName.endsWith(".obj")) {
            mesh = handleOBJFile(fileName, material);
            return;
        }
        throw new FileExtensionException("Unsupported Extension: " + fileName);
    }

    /**
     * Lädt ein OBJ Modell
     * @param fileName Name und Pfad zur Datei
     * @param material Material
     * @return erstelltes Triangle Mesh
     * @throws IOException falls die Datei nicht gefunden wurde
     */
    private Geometry handleOBJFile(String fileName, Material material) throws IOException {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName must not be null");
        }
        if (material == null) {
            throw new IllegalArgumentException("com.daleszynski.raytracer.java.material must not be null");
        }

        System.out.println("Loading Model: " + fileName + "...");
        OBJLoader loader = new OBJLoader();
        loader.parse(fileName);

        final List<Integer> faces = new ArrayList<>();
        final List<Integer> faceTextures = new ArrayList<>();
        final List<Integer> faceNormals = new ArrayList<>();

        for (String face : triangulate(loader.faces)) {
            final boolean hasVn = face.contains("//");
            if (hasVn) {
                face = face.replace("//", "/");
            }

            final StringTokenizer parts = new StringTokenizer(face, " ");
            if (parts.countTokens() != 3) {
                continue;
            }
            while (parts.hasMoreElements()) {
                String part = parts.nextToken();
                String[] subParts = part.split("/");
                final int len = subParts.length;
                if (len == 1) {
                    //Vertex only
                    faces.add(Integer.parseInt(subParts[0]));
                } else if (len == 2 && hasVn) {
                    //V und VN
                    faces.add(Integer.parseInt(subParts[0]));
                    faceNormals.add(Integer.parseInt(subParts[1]));
                } else if (len == 2) {
                    //V UND VT
                    faces.add(Integer.parseInt(subParts[0]));
                    faceTextures.add(Integer.parseInt(subParts[1]));
                } else if (len == 3) {
                    //V VT VN
                    faces.add(Integer.parseInt(subParts[0]));
                    faceTextures.add(Integer.parseInt(subParts[1]));
                    faceNormals.add(Integer.parseInt(subParts[2]));
                } else {
                    System.err.println("Invalid part len: " + face);
                }
            }
        }
        return new TriangleMesh(loader.vertices, loader.verticesNormals, loader.verticesTextures, faces, faceNormals, faceTextures,  this.m);

    }

    /**
     * Konvertiert eine Liste mit faces in Dreiecke, falls die Polygone mehr als Drei ecken haben.
     * @param faces die Liste
     * @return die Dreiecke
     */
    private List<String> triangulate(List<String> faces) {
        List<String> triangles = new ArrayList<>();
        for (String face : faces) {
            final String[] parts = face.split(" ");
            if (parts.length > 3) {
                for (int x = 1; x < parts.length - 1; x++) {
                    String triangle = "";
                    triangle += parts[0] + " ";
                    triangle += parts[x] + " ";
                    triangle += parts[x + 1];
                    triangles.add(triangle);
                }

            } else {
                triangles.add(face);
            }
        }
        faces.addAll(triangles);
        return triangles;
    }


    @Override
    public Hit hit(final Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("ray must not be null");
        }
        return mesh.hit(r);
    }
}
