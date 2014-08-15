package demo;

import com.daleszynski.raytracer.java.camera.Camera;
import com.daleszynski.raytracer.java.camera.PerspectiveCamera;
import com.daleszynski.raytracer.java.geometry.Node;
import com.daleszynski.raytracer.java.geometry.Plane;
import com.daleszynski.raytracer.java.geometry.Sphere;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.image.Display;
import com.daleszynski.raytracer.java.light.AreaLight;
import com.daleszynski.raytracer.java.material.LambertMaterial;
import com.daleszynski.raytracer.java.material.OrenNayarMaterial;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Transform;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.raytracer.Raytracer;
import com.daleszynski.raytracer.java.raytracer.World;
import com.daleszynski.raytracer.java.sampling.RegularSamplingPattern;
import com.daleszynski.raytracer.java.sampling.SamplingPattern;
import com.daleszynski.raytracer.java.texture.SingleColorTexture;
import com.daleszynski.raytracer.java.utility.Textures;

import java.io.IOException;

/**
 * Created by Dalle on 01.08.14.
 */
public class SoftShadows {
    public static void main(String[] args) throws IOException {
        World world = new World(new Color(0,0,0), new Color(.3), 1);


        //world.addGeo(new Node(new Transform().translation(0,1,0),new Sphere(new TransparentMaterial(0.6))));
        world.addGeo(new Node(new Transform().translation(0,1,0),new Sphere(new OrenNayarMaterial(Textures.RED, 20))));
        //world.addGeo(new Node(new Transform().translation(0,2,0).scaling(2), new ShapeFromFile("files/models/teddy.obj", new LambertMaterial(new SingleColorTexture(new Color(0.8,1,0.2))))));
        world.addGeo(new Plane(new LambertMaterial(new SingleColorTexture(new Color(.8,.8,.8)))));

        //Geometry leftSphere = new Node(new Transform(), new Sp)


        final Color lightColor = new Color(.5);
        final SamplingPattern lightSamplingPattern = new RegularSamplingPattern(4,4);
        //world.addLight(new PointLight(lightColor, new Point3(0,10,3), true));
        world.addLight(new AreaLight(lightColor, new Point3(0,4,4), new Vector3(0,0,-1), new Vector3(0,1,0), 1, lightSamplingPattern, true, 1,0,0));
        world.addLight(new AreaLight(lightColor, new Point3(0,4,-4), new Vector3(0,0,1), new Vector3(0,1,0), 1, lightSamplingPattern, true, 1,0,0));
        world.addLight(new AreaLight(lightColor, new Point3(4,4,0), new Vector3(-1,0,0), new Vector3(0,1,0), 1, lightSamplingPattern, true, 1,0,0));
        world.addLight(new AreaLight(lightColor, new Point3(-4,4,0), new Vector3(1,0,0), new Vector3(0,1,0), 1, lightSamplingPattern, true, 1,0,0));


        //world.addLight(new PointLight(new Color(1,1,1), new Point3(8,8,0), true));

        Camera camera = new PerspectiveCamera(new Point3(0,1,-6), new Vector3(0,0,1),new Vector3(0,1,0), Math.PI/4);
        Camera camera2 = new PerspectiveCamera(new Point3(8,8,8), new Vector3(-1,-1,-1),new Vector3(0,1,0), Math.PI/4);

        Raytracer raytracer = new Raytracer(world);
        new Display(raytracer.render(camera2, 1000, 800));

    }

}
