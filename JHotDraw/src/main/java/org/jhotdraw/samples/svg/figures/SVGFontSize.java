/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.figures;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import static org.jhotdraw.draw.AttributeKeys.FONT_SIZE;
import static org.jhotdraw.draw.AttributeKeys.TRANSFORM;

/**
 *
 * @author zasca
 */
public class SVGFontSize extends SVGAttributedFigure      {
    public void setFontSize(float size) {
        // FONT_SIZE.basicSet(this, new Double(size));
        Point2D.Double p = new Point2D.Double(0, size);
        AffineTransform tx =  TRANSFORM.get(this);
        if (tx != null) {
            try {
                tx.inverseTransform(p, p);
                Point2D.Double p0 = new Point2D.Double(0, 0);
                tx.inverseTransform(p0, p0);
                p.y -= p0.y;
            } catch (NoninvertibleTransformException ex) {
                ex.printStackTrace();
            }
        }
        FONT_SIZE.set(this, Math.abs(p.y));
    }
    
    public float getFontSize() {
        //   return FONT_SIZE.get(this).floatValue();
        Point2D.Double p = new Point2D.Double(0, FONT_SIZE.get(this));
        AffineTransform tx =  TRANSFORM.get(this);
        if (tx != null) {
            tx.transform(p, p);
            Point2D.Double p0 = new Point2D.Double(0, 0);
            tx.transform(p0, p0);
            p.y -= p0.y;
                /*
            try {
                tx.inverseTransform(p, p);
            } catch (NoninvertibleTransformException ex) {
                ex.printStackTrace();
            }*/
        }
        return (float) Math.abs(p.y);
    }

    @Override
    protected void drawFill(Graphics2D g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void drawStroke(Graphics2D g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D.Double getBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(Point2D.Double p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getTransformRestoreData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void restoreTransformTo(Object restoreData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void transform(AffineTransform tx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

    
