package com.daleszynski.raytracer.java.utility;

import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.texture.SingleColorTexture;
import com.daleszynski.raytracer.java.texture.Texture;


/**
 * Simple access to some commonly used textures
 */
@SuppressWarnings("UnusedDeclaration")
public class Textures {
    public static final Texture RED = new SingleColorTexture(new Color(1,0,0));
    public static final Texture GREEN = new SingleColorTexture(new Color(0,1,0));
    public static final Texture BLUE = new SingleColorTexture(new Color(0,0,1));
    public static final Texture WHITE = new SingleColorTexture(new Color(1,1,1));
    public static final Texture BLACK = new SingleColorTexture(new Color(0,0,0));
}
