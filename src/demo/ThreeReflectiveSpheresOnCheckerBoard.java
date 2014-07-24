package demo;


import com.daleszynski.raytracer.java.camera.Camera;
import com.daleszynski.raytracer.java.camera.PerspectiveCamera;
import com.daleszynski.raytracer.java.geometry.Geometry;
import com.daleszynski.raytracer.java.geometry.Node;
import com.daleszynski.raytracer.java.geometry.Plane;
import com.daleszynski.raytracer.java.geometry.Sphere;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.image.Display;
import com.daleszynski.raytracer.java.light.PointLight;
import com.daleszynski.raytracer.java.material.ReflectiveMaterial;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Transform;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.raytracer.Raytracer;
import com.daleszynski.raytracer.java.raytracer.World;
import com.daleszynski.raytracer.java.texture.CheckerBoardTexture;
import com.daleszynski.raytracer.java.texture.SingleColorTexture;

@SuppressWarnings("UnusedDeclaration")
public class ThreeReflectiveSpheresOnCheckerBoard {

    public static void main(String[] args) {
        World world = new World(new Color(0,0,0), new Color(.3,.3,.3), 1);


        world.addGeo(new Node(new Transform().translation(0,-1,0), new Plane(new ReflectiveMaterial(new CheckerBoardTexture(0.5,0.5), new SingleColorTexture(new Color(0,0,0)), 64, new SingleColorTexture(new Color(.3,.3,.3))))));

        final Geometry middleSphere = new Node(new Transform().translation(0,0,2).scaling(1),new Sphere(new ReflectiveMaterial(new SingleColorTexture(new Color(.2,.9,.2)), new SingleColorTexture(new Color(0,0,0)), 64, new SingleColorTexture(new Color(.5,.5,.5)))));
        final Geometry leftSphere = new Node(new Transform().translation(2.2,0,2).scaling(1),new Sphere(new ReflectiveMaterial(new SingleColorTexture(new Color(.2,.2,.9)), new SingleColorTexture(new Color(0,0,0)), 64, new SingleColorTexture(new Color(.5,.5,.5)))));
        final Geometry rightSphere = new Node(new Transform().translation(-2.2,0,2).scaling(1),new Sphere(new ReflectiveMaterial(new SingleColorTexture(new Color(.9,.2,.2)), new SingleColorTexture(new Color(0,0,0)), 64, new SingleColorTexture(new Color(.5,.5,.5)))));
        world.addGeo(middleSphere);
        world.addGeo(leftSphere);
        world.addGeo(rightSphere);

        //Geometry leftSphere = new Node(new Transform(), new Sp)


       world.addLight(new PointLight(new Color(.5,.5,.5), new Point3(0,10,-10), true));


        Camera camera = new PerspectiveCamera(new Point3(0,0,-3), new Vector3(0,0,1),new Vector3(0,1,0), Math.PI/4);
        Camera camera2 = new PerspectiveCamera(new Point3(0,2,-6), new Vector3(0,-0.4,1),new Vector3(0,1,0), Math.PI/4);
        Raytracer raytracer = new Raytracer(world);
        new Display(raytracer.render(camera2, 1200,1000));

    }
}
