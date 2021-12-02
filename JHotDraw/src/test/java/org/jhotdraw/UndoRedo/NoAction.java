package org.jhotdraw.UndoRedo;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.undo.UndoRedoManager;
import static org.junit.Assert.*;
/**
 *
 * @author Kristian
 */


public class NoAction extends Stage<NoAction> {
    @ProvidedScenarioState
    protected UndoRedoManager instance;

    public NoAction no_Undoable_changes() {
        instance = new UndoRedoManager();

        assertFalse(instance.canUndo());
        assertFalse(instance.canUndoOrRedo());
        return this;
    }
    
    public NoAction no_Redoable_changes() {
        instance = new UndoRedoManager();

        assertFalse(instance.canRedo());
        assertFalse(instance.canUndoOrRedo());
        return this;
    }
}
