/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.View;
import org.jhotdraw.gui.Worker;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.mockito.Mockito.*;
/**
 *
 * @author amali
 */
public class TestClearAction extends TestAbstractSaveBeforeAction {
    private ClearAction clearAction;

    public TestClearAction() {
        super(new CloseAction(null));
    }
    
    @Before
    public void setUp() {
        clearAction = new ClearAction(mockApp);
    }
    
    @After
    public void shutDown() {
        clearAction = null;
    }
    
    @Test
    public void TestDoIt() {
        View view = mock(View.class);
        clearAction.doIt(view);
        verify(view).setEnabled(false);
        verify(view).execute(any(AbstractApplication.ClearViewWorker.class));
    }
}
