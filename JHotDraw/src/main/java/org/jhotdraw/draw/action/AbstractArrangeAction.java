package org.jhotdraw.draw.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;


public abstract class AbstractArrangeAction extends AbstractSelectedAction{
    
    public String ID;
    
    public AbstractArrangeAction(DrawingEditor editor, String ID){
        super(editor);
        this.ID = ID;
        labels.configureAction(this, ID);
    }
    
    public abstract void undoArrange(DrawingView view, Collection<Figure> figures);
    
    public abstract void doArrange(DrawingView view, Collection<Figure> figures);
    
    @FeatureEntryPoint(JHotDrawFeatures.ARRANGE)
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        final DrawingView view = getView();
        final LinkedList<Figure> figures = new LinkedList<Figure>(view.getSelectedFigures());
        doArrange(view, figures);
        //bringToFront(view, figures);
        fireUndoableEditHappened(new AbstractUndoableEdit() {
            @Override
            public String getPresentationName() {
                return labels.getTextProperty(ID);
            }
            @Override
            public void redo() throws CannotRedoException {
                super.redo();
                doArrange(view, figures);
                //BringToFrontAction.bringToFront(view, figures);
            }
            @Override
            public void undo() throws CannotUndoException {
                super.undo();
                undoArrange(view, figures);
                //SendToBackAction.sendToBack(view, figures);
            }
        }
        
        );
    }

}
