package org.jhotdraw.draw.action;

import java.util.List;
import org.jhotdraw.draw.BezierFigure;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.QuadTreeDrawing;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class ArrangeTest{
    
    private static DrawingEditor editor;

    @BeforeClass
    public static void setUpClass(){
        editor = new DefaultDrawingEditor();
        DrawingView view = new DefaultDrawingView();
        view.setDrawing(new QuadTreeDrawing());
        editor.setActiveView(view);
    }
    
    @Test
    public void sendToBackTest(){
        BezierFigure figure1 = new BezierFigure();
        BezierFigure figure2 = new BezierFigure();
        
        Drawing drawing = editor.getActiveView().getDrawing();
        drawing.add(figure1);
        drawing.add(figure2);
        List<Figure> figures = drawing.getFiguresFrontToBack();
        // Test that figure two start as the front most drawing
        Assert.assertEquals(figures.get(0), figure2);
        //Send figure 2 to the back
        drawing.sendToBack(figure2);
        figures = drawing.getFiguresFrontToBack();
        // Test that figure2 is now at the back
        Assert.assertEquals(figures.get(figures.size() - 1), figure2);

    }
    
    @Test
    public void bringToFrontTest(){
        BezierFigure figure1 = new BezierFigure();
        BezierFigure figure2 = new BezierFigure();
        
        Drawing drawing = editor.getActiveView().getDrawing();
        drawing.add(figure1);
        drawing.add(figure2);
        List<Figure> figures = drawing.getFiguresFrontToBack();
        // Test that figure1 starts as the drawing furthest back
        Assert.assertEquals(figures.get(figures.size() - 1), figure1);
        // Bring figure1 to the front
        drawing.bringToFront(figure1);
        figures = drawing.getFiguresFrontToBack();
        // Test that figure1 is now at the front
        Assert.assertEquals(figures.get(0), figure1);

    }
    
}
