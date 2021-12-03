package org.jhotdraw.draw;

import junit.framework.TestCase;

public class DefaultDrawingViewTest extends TestCase {

    private DefaultDrawingView dv;
    private Figure testFigure;

    public void setUp() throws Exception {
        super.setUp();
        dv = new DefaultDrawingView();
        dv.setDrawing(new DefaultDrawing());
        testFigure = new BezierFigure();
    }

    public void testDelete() {
        // Assert that there are no figures currently present in the drawing
        assertEquals(0, dv.getDrawing().getChildCount());

        // Add the figure, and assert that it is present
        dv.getDrawing().add(testFigure);
        assertEquals(1, dv.getDrawing().getChildCount());

        // Add everything to the selection
        dv.selectAll();

        // Check that the testfigure is selected
        assertTrue(dv.getSelectedFigures().contains(testFigure));

        // Delete selection
        dv.delete();

        // Assert that no figures are present
        assertEquals(0, dv.getDrawing().getChildCount());
    }
}