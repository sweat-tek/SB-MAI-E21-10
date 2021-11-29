/*
 * @(#)SendToBackAction.java  2.0  2008-05-30
 *
 * Copyright (c) 2003-2008 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */

package org.jhotdraw.draw.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.util.*;
import javax.swing.*;
import java.util.*;
import javax.swing.undo.*;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.*;

/**
 * SendToBackAction.
 *
 * @author  Werner Randelshofer
 * @version 2.0 2008-05-30 Renamed from MoveToBackAction to SendToBackAction
 * for consistency with the API of Drawing.
 * <br>1.0 24. November 2003  Created.
 */

public class SendToBackAction extends AbstractArrangeAction {
    
       public static String ID = "edit.sendToBack";
    /** Creates a new instance. */
    public SendToBackAction(DrawingEditor editor) {
        super(editor, ID);
    } 

    @FeatureEntryPoint(JHotDrawFeatures.ARRANGE)
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        super.actionPerformed(e);
    }
    public static void sendToBack(DrawingView view, Collection figures) {
        Iterator i = figures.iterator();
        Drawing drawing = view.getDrawing();
        while (i.hasNext()) {
            Figure figure = (Figure) i.next();
            drawing.sendToBack(figure);
        }
    }

    @Override
    public void undoArrange(DrawingView view, Collection<Figure> figures) {
        BringToFrontAction.bringToFront(view, figures);
    }

    @Override
    public void doArrange(DrawingView view, Collection<Figure> figures) {
        sendToBack(view, figures);
    }
}
