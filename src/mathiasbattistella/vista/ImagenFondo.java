/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.vista;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.border.Border;

/**
 *
 * @author user6
 */
public class ImagenFondo implements Border{
    public BufferedImage back;
    public ImagenFondo() throws MalformedURLException, IOException{
        try {
            URL imagePatch = new URL(getClass().getResource("/mathiasbattistella/utilidades/fondo.jpg").toString());
            back = ImageIO.read(imagePatch);
        } catch (Error e) {
            throw new Error("No se ha podido cargar la imágen de fondo");
        }
    }
    
    @Override
    public void paintBorder(Component cmpnt, Graphics grphcs, int x, int y, int with, int height) {
        grphcs.drawImage(back, (x + (with - back.getWidth())/2), (y+(height-back.getHeight())/2), null);
    }

    @Override
    public Insets getBorderInsets(Component cmpnt) {
        return new Insets(0, 0, 0, 0);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
    
}
