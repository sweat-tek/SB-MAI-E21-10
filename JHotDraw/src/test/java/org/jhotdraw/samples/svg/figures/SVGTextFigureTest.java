/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.Connector;
import org.jhotdraw.draw.Handle;
import org.jhotdraw.draw.TextEditingTool;
import org.jhotdraw.draw.TextHolderFigure;
import org.jhotdraw.draw.Tool;
import org.jhotdraw.geom.Dimension2DDouble;
import org.jhotdraw.geom.Insets2D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author zasca
 */
public class SVGTextFigureTest {
    
    public SVGTextFigureTest() {
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
     * Test of setText method, of class SVGTextFigure.
     */
    @Test
    public void testSetText() {
        System.out.println("setText");
        String newText = "Hello";
        SVGTextFigure actual = new SVGTextFigure();
        actual.setText(newText);
        
        String expResult = "Hello"; 
        
        assertEquals(expResult, actual.getText());

    }

    /**
     * Test of isEditable method, of class SVGTextFigure.
     */
    @Test
    public void testIsEditable() {
        System.out.println("isEditable");
        SVGTextFigure instance = new SVGTextFigure();
        boolean expResult = true;
        boolean result = instance.isEditable();
        assertEquals(expResult, result);
  
    }
    
    /**
     * Test of getTool method, of class SVGTextFigure.
     */
    @Test
    public void testGetTool() {
        System.out.println("getTool");
        Point2D.Double p = new Point2D.Double(0.0, -6.0);
        SVGTextFigure instance = new SVGTextFigure();
        instance.isEditable();
        
        Tool result = instance.getTool(p);
        
        assertThat(result, instanceOf(TextEditingTool.class));
    }
    
}
