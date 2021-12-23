/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import org.jhotdraw.app.View;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.beans.PropertyChangeListener;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author amali
 */
public class TestExitAction extends TestAbstractSaveBeforeAction {
    private ExitAction exitAction;
    
    public TestExitAction() {
        super(new ExitAction(null));
    }
    
    @Before
    public void setUp() {
        exitAction = new ExitAction(mockApp);
    }
    
    @After
    public void tearDown(){
        exitAction = null;
    }
    
    @Test
    public void ViewNotEnabledActionPerformedTest() {
        doReturn(false).when(mockApp).isEnabled();
        action.actionPerformed(null);
        verify(mockApp, times(0)).setEnabled(false);
    }
    
    @Test
    public void ViewEnabledActionPerformedTest() {
        ExitAction mockAction = mock(ExitAction.class);
        mockAction.app = mockApp;
        doReturn(true).when(mockApp).isEnabled();
        doCallRealMethod().when(mockAction).actionPerformed(any());
        mockAction.actionPerformed(null);
        verify(mockAction).checkIfUnsavedThenClose();
    }
    
    @Test
    public void GetUnsavedViewsFalseTest() {
        ExitAction mockAction = mock(ExitAction.class);
        mockAction.app = mockApp;
        doCallRealMethod().when(mockAction).getApplication();
        doCallRealMethod().when(mockAction).retrieveUnsavedViews();
        View view = mock(View.class);
        doReturn(false).when(view).hasUnsavedChanges();
        doReturn(true).when(view).isEnabled();
        List<View> views = new ArrayList<>();
        views.add(view);
        doReturn(views).when(mockApp).views();
        List<View> testResults = mockAction.retrieveUnsavedViews();
        assertTrue(testResults.isEmpty());
    }
    
    @Test
    public void GetUnsavedViewsTrueTest() {
        ExitAction mockAction = mock(ExitAction.class);
        mockAction.app = mockApp;
        doCallRealMethod().when(mockAction).getApplication();
        doCallRealMethod().when(mockAction).retrieveUnsavedViews();
        View view = mock(View.class);
        doReturn(true).when(view).hasUnsavedChanges();
        doReturn(true).when(view).isEnabled();
        List<View> views = new ArrayList<>();
        views.add(view);
        doReturn(views).when(mockApp).views();
        List<View> testResults = mockAction.retrieveUnsavedViews();
        assertFalse(testResults.isEmpty());
    }
    
}
