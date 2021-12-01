package org.jhotdraw.UndoRedo;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.undo.UndoRedoManager;
import static org.junit.Assert.*;

/**
 *
 * @author Kristian
 */


public class Action extends Stage<Action>{
    @ExpectedScenarioState
    protected UndoRedoManager instance;

    @ProvidedScenarioState
    protected EditForTest edit = new EditForTest(true, true, true);

    public Action an_undoable_action_is_made() {
        // Add an edit that is not significant so it should not account as an undoable edit
        instance.addEdit(new EditForTest(false, true, false));
        assertFalse(instance.canUndo());
        assertFalse(instance.canUndoOrRedo());

        // Add an edit that is significant, so it should be undoable
        instance.addEdit(new EditForTest(true, true, false));
        assertTrue(instance.canUndo());
        assertTrue(instance.canUndoOrRedo());
        return this;
    }
    public Action an_redoable_action_is_made() {
        // Add an edit that is not significant so it should not account as an undoable edit
        instance.addEdit(new EditForTest(false, true, false));
        assertFalse(instance.canRedo());
        assertFalse(instance.canUndoOrRedo());

        
        // Add an edit that is significant, so it should be undoable
        instance.addEdit(edit);
        assertTrue(instance.canUndo());
        assertTrue(instance.canUndoOrRedo());
        instance.undo();
        assertTrue(instance.canRedo());
        assertTrue(instance.canUndoOrRedo());
        return this;
    }
}
