package org.jhotdraw.UndoRedo;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.undo.UndoRedoManager;
import static org.junit.Assert.assertTrue;
/**
 *
 * @author Kristian
 */


public class AfterAction extends Stage<AfterAction> {
    @ExpectedScenarioState
    protected UndoRedoManager instance;

    @ExpectedScenarioState
    protected EditForTest edit;

    public AfterAction the_action_can_be_undone() {
        assertTrue(instance.getUndoPresentationName().equals(edit.getUndoPresentationName()));
        return this;
    }
    public AfterAction the_action_can_be_redone() {
        assertTrue(instance.getRedoPresentationName().equals(edit.getRedoPresentationName()));
        return this;
    }
}
