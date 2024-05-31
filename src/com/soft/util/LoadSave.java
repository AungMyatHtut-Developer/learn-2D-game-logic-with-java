package com.soft.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String TILES_IMG = "my-last-tiles.png";

    public static BufferedImage GetResourceImage(String fileName) {
        BufferedImage image = null;
        try (InputStream inputStream = LoadSave.class.getResourceAsStream("/img/"+fileName)){
            if (inputStream == null) {
                throw new IOException("Image not found!");
            }
            image = ImageIO.read(inputStream);
            System.out.println("We found the image");

        } catch (Exception e) {
            System.out.println("Exception happened when loading Sprite. Error : "+ e);
        }
        return image;
    }
}
