/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.draw;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author theis
 */
public class PictImageInputFormatTest {

    public static PictImageInputFormat instance;
    private static Drawing drawing;

    @BeforeClass
    public static void setUpClass() {
        instance = new PictImageInputFormat(new SVGImageFigure());
        drawing = new QuadTreeDrawing();
        LinkedList<InputFormat> inputFormats = new LinkedList<InputFormat>();
        inputFormats.add(instance);
        drawing.setInputFormats(inputFormats);
    }

    /**
     * Test of GetImageFromPictStream method, of class PictImageInputFormat.
     */
    @Test
    public void testGetImageFromPictStream() throws Exception {
        System.out.println("Testing the that getImageFromPictStream returns a Image");
        String fileName = "source to a .pct file";
        InputStream in = new FileInputStream(fileName);
        Image img = instance.getImageFromPictStream(in);

        //Here I wanted to test whether the getImageFromPictStream yealds a result
        System.out.println(img);
        assertNotNull(instance.getImageFromPictStream(in));
    }
    
    /**
     * Test of read method, of class PictImageInputFormat.
     */
    @Test
    public void testRead_3args_1() throws Exception {
        System.out.println("Testing the read method with 3 parameters and file type input");
        File file = new File("source to a .pct file");
        boolean replace = true;
        instance.read(file, drawing, replace);

        //As a control I would first test that the drawing is indeed empty
        System.out.println(drawing.getChildren().isEmpty());
        assertTrue(drawing.getChildren().isEmpty());

        //Here I would test whether the .pct had been added to the drawing.
        System.out.println(drawing.getChildren().isEmpty());
        assertFalse(drawing.getChildren().isEmpty());
    }

    /**
     * Test of read method, of class PictImageInputFormat.
     */
    @Test
    public void testRead_3args_2() throws Exception {
        System.out.println("Testing the read method with 3 parameters and InputStream type input");
        File file = new File("source to a .pct file");
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        boolean replace = true;
        instance.read(in, drawing, replace);

        //As a control I would first test that the drawing is indeed empty
        System.out.println(drawing.getChildren().isEmpty());
        assertTrue(drawing.getChildren().isEmpty());

        //Here I would test whether the .pct had been added to the drawing.
        System.out.println(drawing.getChildren().isEmpty());
        assertFalse(drawing.getChildren().isEmpty());
    }
}
