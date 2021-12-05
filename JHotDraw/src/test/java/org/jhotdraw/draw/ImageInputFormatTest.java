/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author theis
 */
public class ImageInputFormatTest {
    
    private static ImageInputFormat instance;
    private static Drawing drawing;
    
    public ImageInputFormatTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        instance = new ImageInputFormat(new SVGImageFigure(), "JPG", "Joint Photographics Experts Group (JPEG)", new String[] {"jpg", "jpeg", "jpe", "jif", "jfif"}, BufferedImage.TYPE_INT_RGB);
        drawing = new QuadTreeDrawing();
        LinkedList<InputFormat> inputFormats = new LinkedList<InputFormat>();
        inputFormats.add(instance);
        drawing.setInputFormats(inputFormats);
    }

    /**
     * Test of getFileExtension method, of class ImageInputFormat.
     */
    @Test
    public void testGetFileExtension() {
        System.out.println("getFileExtension");
        String[] expResult = {"jpg", "jpeg", "jpe", "jif", "jfif"};
        String[] result = instance.getFileExtension();
        for (String st : result){
            System.out.print(st + " ");
        }
        System.out.println("\n");
        assertArrayEquals(expResult, result);
    }
    
    @Test
    public void testRead() throws IOException {
        System.out.println("test Read method");
        File file = new File("src/test/java/org/jhotdraw/draw/Yobama.png");
        boolean replace = true;
        
        System.out.println(drawing.getChildren().isEmpty());
        assertTrue(drawing.getChildren().isEmpty());
        
        instance.read(file, drawing, replace);
        
        assertFalse(drawing.getChildren().isEmpty());
        System.out.println(drawing.getChildren().isEmpty());
    }
}