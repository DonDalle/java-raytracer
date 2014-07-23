package com.daleszynski.raytracer.java.math;

/**
 * Test Datei für die Mathe Klassen
 */
public class MathTest {

    public static void main(String args[]) {
        Normal3 n1 = new Normal3(1, 2, 3);
        System.out.println(n1.mul(0.5));

        Normal3 n2 = new Normal3(3, 2, 1);
        System.out.println(n1.add(n2));


        System.out.println();
        //Vektor dot Vektor
        System.out.println("Vektor dot Vektor");

        Vector3 v1 = new Vector3(1, 0, 0);
        Vector3 v2 = new Vector3(0, 1, 0);
        System.out.println(v1.dot(v1));
        System.out.println(v1.dot(v2));
        System.out.println();


        //Vektor dot normale
        System.out.println("Vektor dot normale");

        v1 = new Vector3(1, 0, 0);
        n1 = new Normal3(1, 0, 0);
        n2 = new Normal3(0, 1, 0);
        System.out.println(v1.dot(n1));
        System.out.println(v1.dot(n2));
        System.out.println();

        //Normale dot Vektor
        System.out.println("Normale dot Vektor");

        n1 = new Normal3(1, 0, 0);
        v1 = new Vector3(1, 0, 0);
        v2 = new Vector3(0, 1, 0);
        System.out.println(n1.dot(v1));
        System.out.println(n1.dot(v2));
        System.out.println();

        //Punkt  / Vektor tests

        System.out.println("Punkt/Vektor Tests");
        Point3 p1 = new Point3(1, 1, 1);
        Point3 p2 = new Point3(2, 2, 0);
        System.out.println(p1.sub(p2));

        v1 = new Vector3(4, 3, 2);
        System.out.println(p1.sub(v1));
        System.out.println(p1.add(v1));
        System.out.println();
        //Magnitude Test
        System.out.println("Magnitude test");
        v1 = new Vector3(1, 1, 1);
        System.out.println((Math.sqrt(3) == v1.magnitude) + " (" + v1 + ")");
        System.out.println();

        //Vector add, sub, mul tests
        System.out.println("Vektor mul, add, sub tests");
        v1 = new Vector3(1, 2, 3);
        v2 = new Vector3(3, 2, 1);

        System.out.println(v1.add(v2));  //Should be 4,4,4
        System.out.println(v1.mul(2));   //Should be 2,4,6
        System.out.println(v1.sub(v2));  //Should be -2, 0, 2
        System.out.println();


        //Reflektionen tests
        System.out.println("Reflektion tests");
        v1 = new Vector3(-0.707, 0.707, 0);
        n1 = new Normal3(0, 1, 0);

        v2 = new Vector3(0.707, 0.707, 0);
        n2 = new Normal3(1, 0, 0);

        System.out.println(v1.reflectedOn(n1));
        System.out.println(v2.reflectedOn(n2));
        System.out.println();

        //Matrix tests
        System.out.println("Matrix tests");

        Mat3x3 m1 = new Mat3x3(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Mat3x3 m2 = new Mat3x3(1, 0, 0, 0, 1, 0, 0, 0, 1);
        Mat3x3 m3 = new Mat3x3(0, 0, 1, 0, 1, 0, 1, 0, 0);


        System.out.println(m1.mul(m2));
        System.out.println(m1.mul(m3));

        v1 = new Vector3(8, 8, 8);
        System.out.println("Spalten austauschen");
        Mat3x3 m4 = m1.changeCol1(v1);
        System.out.println(m4);
        m4 = m4.changeCol2(v1);
        System.out.println(m4);
        m4 = m4.changeCol3(v1);
        System.out.println(m4);
        System.out.println();

        //Kreuzprodukt test
        v1 = new Vector3(1,2,3);
        v2 = new Vector3(3,2,1);
        System.out.println("Kreuzprodukt test");
        System.out.println(v1.x(v2));


    }
}
