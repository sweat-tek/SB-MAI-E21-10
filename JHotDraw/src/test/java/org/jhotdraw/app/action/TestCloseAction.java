/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import org.jhotdraw.app.AbstractApplication;
import org.jhotdraw.app.View;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.beans.PropertyChangeListener;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 *
 * @author amali
 */
public class TestCloseAction extends TestAbstractSaveBeforeAction {
    private CloseAction closeAction;
    
    public TestCloseAction() {
        super(new CloseAction(null));
    }
    
    @Before
    public void setUp() {
        closeAction = new CloseAction(mockApp);
    }
    
    @After
    public void shutDown() {
        closeAction = null;
    }
    
    @Test
    public void DoItTest() {
        View view = mock(View.class);
        doNothing().when(mockApp).dispose(view);
        doReturn(mockApp).when(view).getApplication();
        closeAction.doIt(view);
        verify(mockApp).dispose(view);
    }
    
    @Test
    public void DoItNullTest() {
        closeAction.doIt(null);
    }
    
    @Test
    public void DoItApplicationNullTest() {
        View view = mock(View.class);
        doReturn(null).when(view).getApplication();
        closeAction.doIt(view);
    }
    
}
