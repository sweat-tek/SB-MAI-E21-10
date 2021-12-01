/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg;

import java.io.File;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.ImageInputFormat;
import org.jhotdraw.samples.svg.figures.SVGImageFigure;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author theis
 */
public class SVGViewTest {
    
    private static SVGView instance;
    private static Drawing result;
    
    @BeforeClass
    public static void setUpClass() {
        instance = new SVGView();
        result = instance.createDrawing();
    }

    /**
     * Test of createDrawing method, of class SVGView.
     */
    @Test
    public void testCreateDrawing() {
        System.out.println("Testing how many formats there are\n");
        assertEquals(6, result.getInputFormats().size());
        System.out.println("Size of the list of input formats is = " + result.getInputFormats().size());
    }

    @Test
    public void testReadingOtherFileExtensions() throws Exception {
        System.out.println("Trying to read image in the format of .jpeg\n");
        File f = new File("/Users/theis/Dropbox/Pictures/moi.jpeg");
        ImageInputFormat iif = new ImageInputFormat(new SVGImageFigure());
        iif.read(f, result, true);
        
        assertFalse(result.getChildren().isEmpty());
        System.out.println("If false then array of children is not empty after added .jpeg image. "
                + "\n Result is = " + result.getChildren().isEmpty());
    }
}