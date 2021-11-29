package org.jhotdraw.UndoRedo;

import org.jhotdraw.undo.UndoRedoManager;
import org.junit.Test;
import static org.junit.Assert.*;
import com.tngtech.jgiven.junit.ScenarioTest;

/**
 *
 * @author Kristian
 */


public class UndoRedoManagerTest extends ScenarioTest<NoAction, Action, AfterAction> {   
    @Test
    public void testCanUndo() {
        given().no_Undoable_changes();
        when().an_undoable_action_is_made();
        then().the_action_can_be_undone();
    }
    @Test
    public void testCanRedo() {
        given().no_Redoable_changes();
        when().an_redoable_action_is_made();
        then().the_action_can_be_redone();
    }
    
    @Test
    public void testUndoRedoLimit() {
        UndoRedoManager instance = new UndoRedoManager();
        assertEquals(100, instance.getLimit());
        
    }

    @Test
    public void testSetSignificantEdits() {
        UndoRedoManager instance = new UndoRedoManager();
        // The defaults boolean is false
        assertFalse(instance.hasSignificantEdits());
        
        // Set HasSignificantEdits to true
        instance.setHasSignificantEdits(true);
        assertTrue(instance.hasSignificantEdits());

        // Set HasSignificantEdits to false
        instance.setHasSignificantEdits(false);
        assertFalse(instance.hasSignificantEdits());
    }

    @Test
    public void testUndoActionsDisabled() {
        UndoRedoManager instance = new UndoRedoManager();
        assertFalse(instance.getUndoAction().isEnabled());
    }
    @Test
    public void testRedoActionsDisabled() {
        UndoRedoManager instance = new UndoRedoManager();
        assertFalse(instance.getRedoAction().isEnabled());
    }

    @Test
    public void testSignificantEdit() {
        UndoRedoManager instance = new UndoRedoManager();
        // The defaults boolean is false, this is because no edit is made.
        assertFalse(instance.hasSignificantEdits());
        
        //Add edit that is not significant
        instance.addEdit(new EditForTest(false, false, false));
        assertFalse(instance.hasSignificantEdits());

        //Add edit that is significant
        instance.addEdit(new EditForTest(true, false, false));
        assertTrue(instance.hasSignificantEdits());
    }

    @Test
    public void testDiscardAllEdits() {
        UndoRedoManager instance = new UndoRedoManager();
        assertTrue(!instance.hasSignificantEdits());

        instance.addEdit(new EditForTest(true, false, false));
        assertTrue(instance.hasSignificantEdits());

        //Discards all edits in the system
        instance.discardAllEdits();
        assertTrue(!instance.hasSignificantEdits());
    }
    

    @Test
    public void testUndoPresentationName() {
        UndoRedoManager instance = new UndoRedoManager();
        
        //Tests that the presentaiton name is nothing
        assertEquals("", instance.getPresentationName());

        //Test that the edit undo presentation name is equals the instance undo presentation name
        EditForTest edit = new EditForTest(true, true, false);
        instance.addEdit(edit);
        assertEquals(edit.getUndoPresentationName(), instance.getUndoPresentationName());
    }
    
    @Test
    public void testRedoPresentationName() {
        UndoRedoManager instance = new UndoRedoManager();
        
        //Tests that the presentaiton name is nothing
        assertEquals("", instance.getPresentationName());

        //Test that the edit redo presentation name is equals the instance redo presentation name
        EditForTest edit = new EditForTest(true, false, true);
        instance.addEdit(edit);
        assertEquals(edit.getRedoPresentationName(), instance.getRedoPresentationName());
    }
}
