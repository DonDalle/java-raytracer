package com.daleszynski.raytracer.java.loader;


import java.io.IOException;

public class FileExtensionException extends IOException {
    public FileExtensionException(String message) {
        super(message);
    }
}
