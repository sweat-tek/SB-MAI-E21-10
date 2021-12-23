/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.NoSuchMethodException;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.*;

/**
 *
 * @author amali
 */
public abstract class TestAbstractSaveBeforeAction extends TestAbstractAction {
    public Class<AbstractSaveBeforeAction> actionClass;
    public AbstractSaveBeforeAction action;
    
    public TestAbstractSaveBeforeAction(AbstractSaveBeforeAction action) {
        this.actionClass = (Class<AbstractSaveBeforeAction>) action.getClass();
    }
    
    @Before
    public void setUpActionClass() {
        try {
            action = actionClass.getConstructor(Application.class).newInstance(mockApp);
        } catch ( IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            fail();
        }
    }
    
    @After
    public void shutDownActionClass() {
        action = null;
    }
    
    @Test
    public void SaveIfUnsavedChangesHasUnsavedTest(){
        AbstractSaveBeforeAction mockAction = mock(action.getClass());
        View view = mock(View.class);
        doReturn(true).when(view).hasUnsavedChanges();
        doCallRealMethod().when(mockAction).saveIfAnyUnsavedChanges(view);
        mockAction.saveIfAnyUnsavedChanges(view);
        verify(mockAction).showSavePrompt(view);
    }
    
    
    @Test
    public void SaveIfUnsavedChangesHasNoUnsavedTest(){
        AbstractSaveBeforeAction mockAction = mock(action.getClass());
        View view = mock(View.class);
        doReturn(false).when(view).hasUnsavedChanges();
        doCallRealMethod().when(mockAction).saveIfAnyUnsavedChanges(view);
        mockAction.saveIfAnyUnsavedChanges(view);
        verify(mockAction).doIt(view);
    }

}
