package com.daleszynski.raytracer.java.geometry;

import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.material.Material;
import com.daleszynski.raytracer.java.material.SingleColorMaterial;
import com.daleszynski.raytracer.java.math.Normal3;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.optimization.AxisAlignedBoundingBox;
import com.daleszynski.raytracer.java.texture.SingleColorTexture;
import com.daleszynski.raytracer.java.texture.TexCoord2D;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Stellt ein Triangle mesh dar.
 */
public class TriangleMesh extends Geometry{

    /**
     * Liste mit den Dreiecken
     */
    public final List<Triangle> triangles = new LinkedList<>();
    /**
     * Liste mit den Eckpunkten.
     */
    public final List<Point3> verticies;

    /**
     * Liste mit den Normalen
     */
    public final List<Normal3> normals;

    /**
     * Liste mit den Faces
     */
    public final List<Integer> faces;

    /**
     * Liste mit den Face Normalen
     */
    public final List<Integer> faceNormals;

    /**
     * Liste mit Texturkoordinaten
     */
    private final List<TexCoord2D> textures;

    /**
     * Liste mit den Face Textures
     */
    private final List<Integer> faceTextures;

    /**
     * Die Boundingbox
     */
    private AxisAlignedBoundingBox bounding;

    /**
     * Erstellt das Triangle Mesh
     * @param verticies Liste mit den Eckpunkten.
     * @param normals Liste mit den Normalen der Eckpunkte
     * @param textures Liste mit den Texturkoordinaten
     * @param faces Liste der Dreicke
     * @param faceNormals liste der Normalen der Dreiecke
     * @param faceTextures Liste der Texturen der Dreiecke
     * @param m das Material
     */
    public TriangleMesh(List<Point3> verticies, List<Normal3> normals, List<TexCoord2D> textures, List<Integer> faces, List<Integer> faceNormals, List<Integer> faceTextures, Material m) {
        super(m);
        this.verticies = verticies;
        this.normals = normals;
        this.faceNormals = faceNormals;
        this.textures = textures;
        this.faces = faces;
        this.faceTextures = faceTextures;

        generateTriangles();

    }


    /**
     * Eigentliche methode die die Dreiecke erstellt.
     */
    private void generateTriangles() {
        double minX = 0;
        double minY = 0;
        double minZ = 0;
        double maxX = 0;
        double maxY = 0;
        double maxZ = 0;
        final int offset =  -faces.get(faces.indexOf(Collections.min(faces)));
        for (int i = 0; i < faces.size(); i+=3) {
            final int vertexIndexA = faces.get(i);
            final int vertexIndexB = faces.get(i+1);
            final int vertexIndexC = faces.get(i+2);

            final Point3 a = verticies.get(vertexIndexA+offset);
            final Point3 b = verticies.get(vertexIndexB+offset);
            final Point3 c = verticies.get(vertexIndexC+offset);

            final Normal3 n1;
            final Normal3 n2;
            final Normal3 n3;
            if(normals.size() <= 0) {
                Normal3 n = a.sub(b).x(a.sub(c)).normalized().asNormal();
                n1 = n;
                n2 = n;
                n3 = n;
            } else {
                int n1i = faceNormals.get(i);
                int n2i = faceNormals.get(i+1);
                int n3i = faceNormals.get(i+2);
                n1 = normals.get(n1i+offset);
                n2 = normals.get(n2i+offset);
                n3 = normals.get(n3i+offset);
            }

            final TexCoord2D t1;
            final TexCoord2D t2;
            final TexCoord2D t3;

            if(textures.size() <= 0) {
                final TexCoord2D t = new TexCoord2D(0,0);
                t1 = t;
                t2 = t;
                t3 = t;
            } else  {
                int t1i = faceTextures.get(i);
                int t2i = faceTextures.get(i+1);
                int t3i = faceTextures.get(i+2);
                t1 = textures.get(t1i+offset);
                t2 = textures.get(t2i+offset);
                t3 = textures.get(t3i+offset);
            }

            minX = Math.min(minX, Math.min(Math.min(a.x, b.x), c.x));
            minY = Math.min(minY, Math.min(Math.min(a.y, b.y), c.y));
            minZ = Math.min(minZ, Math.min(Math.min(a.z, b.z), c.z));
            maxX = Math.max(maxX, Math.max(Math.max(a.x, b.x), c.x));
            maxY = Math.max(maxY, Math.max(Math.max(a.y, b.y), c.y));
            maxZ = Math.max(maxZ, Math.max(Math.max(a.z, b.z), c.z));

            final Point3 lbf = new Point3(minX, minY, minZ);
            final Point3 run = new Point3(maxX, maxY, maxZ);
            this.bounding = new AxisAlignedBoundingBox(lbf, run, new SingleColorMaterial(new SingleColorTexture(new Color(0,1,0))));
            this.triangles.add(new Triangle(c, b, a, n3, n2, n1, t3, t2, t1, this.m));
        }
    }

    @Override
    public Hit hit(Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("r must not be null");
        }

        if(bounding.hit(r) != null) {
            final Optional<Hit> min  = triangles.stream()
                    .map(geo -> geo.hit(r))
                    .filter(hit -> hit != null)
                    .filter(hit -> hit.t > 0)
                    .min((h1, h2) -> Double.compare(h1.t, h2.t));

            if(min.isPresent()) {
                return min.get();
            }
        }
        return  null;
    }
}
