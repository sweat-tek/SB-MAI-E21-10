package org.jhotdraw.app.action;
import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.app.View;
/**
 *
 * @author Kristian Østergård
 */


public abstract class SuperRedoUndoAction extends AbstractViewAction {

    private static String ID;

    protected PropertyChangeListener redoUndoActionPropertyListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent evt) {
            String name = evt.getPropertyName();
            if (name == AbstractAction.NAME) {
                putValue(AbstractAction.NAME, evt.getNewValue());
            } else if (name == "enabled") {
                updateEnabledState();
            }
        }
    };

    public SuperRedoUndoAction(Application app) {
        super(app);
    }

    protected void updateEnabledState() {
        boolean isEnabled = false;
        Action realRedoUndoAction = getRealRedoUndoAction();
        if (realRedoUndoAction != null) {
            isEnabled = realRedoUndoAction.isEnabled();
        }
        setEnabled(isEnabled);
    }

    @Override
    protected void updateView(View oldValue, View newValue) {
        super.updateView(oldValue, newValue);
        if (newValue != null && newValue.getAction(ID) != null) {
            putValue(AbstractAction.NAME, newValue.getAction(ID).getValue(AbstractAction.NAME));
            updateEnabledState();
        }
    }

    /**
     * Installs listeners on the view object.
     */
    @Override
    protected void installViewListeners(View p) {
        super.installViewListeners(p);
        if (p.getAction(ID) != null) {
            p.getAction(ID).addPropertyChangeListener(redoUndoActionPropertyListener);
        }
    }

    /**
     * Installs listeners on the view object.
     */
    @Override
    protected void uninstallViewListeners(View p) {
        super.uninstallViewListeners(p);
        if (p.getAction(ID) != null) {
            p.getAction(ID).removePropertyChangeListener(redoUndoActionPropertyListener);
        }
    }

    @FeatureEntryPoint(value = JHotDrawFeatures.UNDO_REDO)
    public void actionPerformed(ActionEvent e) {
        Action realRedoUndoAction = getRealRedoUndoAction();
        if (realRedoUndoAction != null) {
            realRedoUndoAction.actionPerformed(e);
        }
    }

    protected abstract Action getRealRedoUndoAction();
}
