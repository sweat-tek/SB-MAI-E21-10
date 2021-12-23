/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.gui.Worker;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;


/**
 *
 * @author amali
 */
public class TestNewAction extends TestAbstractAction{
    private NewAction newAction;
    
    @Before
    public void setUp() {
        newAction = new NewAction(mockApp);
    }
    
    @After
    public void shutDown(){
        newAction = null;
    }
    
    @Test
    public void CreateNewViewTest() {
        View view = mock(View.class);
        doNothing().when(view).setMultipleOpenId(1);
        doNothing().when(view).execute(any(Worker.class));
        doNothing().when(view).clear();
        doReturn(view).when(mockApp).createView();
        doNothing().when(mockApp).add(view);
        doNothing().when(mockApp).show(view);
        
        try {
            Method method = NewAction.class.getDeclaredMethod("createNewView", Application.class, int.class);
            method.setAccessible(true);
            method.invoke(newAction, mockApp, 1);

            verify(mockApp).createView();
            verify(view).setMultipleOpenId(1);
            verify(mockApp).add(view);
            verify(view).execute(any());
            verify(mockApp).show(view);

        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            fail(e.getMessage());
        }
        
    }
    
    @Test
    public void GetMultipleOpenIdFilledTest() {
        Collection<View> views = new ArrayList<>();
        View view1 = mock(View.class);
        doReturn(1).when(view1).getMultipleOpenId();
        View view2 = mock(View.class);
        doReturn(2).when(view2).getMultipleOpenId();
        views.add(view1);
        views.add(view2);
        doReturn(views).when(mockApp).views();
        try {
            Method method = NewAction.class.getDeclaredMethod("getMultipleOpenId", Application.class);
            method.setAccessible(true);
            int multipleOpeningId = (int) method.invoke(newAction, mockApp);
            assertEquals(2, multipleOpeningId);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void GetMultipleOpenIdEmpty() {
        doReturn(new ArrayList<View>()).when(mockApp).views();
        try {
            Method method = NewAction.class.getDeclaredMethod("getMultipleOpenId", Application.class);
            method.setAccessible(true);
            int multipleOpeningId = (int) method.invoke(newAction, mockApp);
            assertEquals(1, multipleOpeningId);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            fail(e.getMessage());
        }
    }
    
}
