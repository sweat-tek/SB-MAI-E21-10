/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static javax.swing.Spring.height;
import org.jhotdraw.draw.DrawingEditor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marku
 */
public class AbstractToolBarTest {
    JFrame rootPane;
    BufferedImage bi; 
    Graphics2D g2; 
    
    JPanel resultParent;
    JPanel expResultParent;
    
    AbstractToolBar abstractToolBar;
    
    public AbstractToolBarTest() {
       
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
         this.rootPane = new JFrame();
         this.bi = new BufferedImage(100,100, BufferedImage.TYPE_INT_ARGB);
         this.g2= bi.createGraphics();
         this.resultParent = new JPanel(new GridBagLayout());
         this.expResultParent = new JPanel(new GridBagLayout());
         this.abstractToolBar = new AbstractToolBar();
    }
    
    @After
    public void tearDown() {
        g2.dispose();
    }

    @Test
    public void TestProxyPanel() {
        System.out.println("TestProxyPanel");
        
        
        rootPane.add(resultParent);
        rootPane.add(expResultParent);
        
        
        JComponent result = abstractToolBar.getDisclosedComponent(1);
        resultParent.add(result);
        
        result.paint(g2);
        int resultInt = resultParent.getComponentCount();
        System.out.println("result:" + resultInt);
        
        JComponent expResult = new ProxyPanel(abstractToolBar);
        expResultParent.add(expResult);
        expResult.paint(g2);
        int expResultInt = expResultParent.getComponentCount();
        System.out.println("expresult:" + expResultInt);
        assertEquals("expects parent to have one component", 1, resultInt);
        assertEquals(expResultInt, resultInt);
        assertEquals("expects the new component of the parent to be a JPanel", JPanel.class, resultParent.getComponents()[0].getClass());
        

    }
    
}
