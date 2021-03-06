/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andre
 */
public class SVGImageFigureTest {
    
    public SVGImageFigureTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadImage method, of class SVGImageFigure.
     */
    @Test
    public void testLoadImage_InputStream() throws Exception {
        System.out.println("loadImage");
        File file = new File("src/test/java/org/jhotdraw/samples/svg/figures/black.jpg");
        InputStream in = new FileInputStream(file);
        SVGImageFigure instance = new SVGImageFigure();
        instance.loadImage(in);
        
        BufferedImage buffResult = instance.getBufferedImage();
        
        byte[] imgResult = instance.getImageData();
        
        assertNotNull(buffResult);
        assertNotNull(imgResult);
    }
}