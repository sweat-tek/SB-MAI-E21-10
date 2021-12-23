/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

import org.jhotdraw.app.Application;
import org.junit.Before;
import org.junit.After;
import java.beans.PropertyChangeListener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
/**
 *
 * @author amali
 */
public abstract class TestAbstractAction {
    public Application mockApp;
    
    @Before
    public void setUpApp() {
        mockApp = mock(Application.class);
        doNothing().when(mockApp).addPropertyChangeListener(any(PropertyChangeListener.class));
    }
    
    @After
    public void shutDownApp() {
        mockApp = null;
    }
}
