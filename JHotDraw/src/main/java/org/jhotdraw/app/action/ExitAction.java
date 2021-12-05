/*
 * @(#)ExitAction.java  1.0  04 January 2005
 *
 * Copyright (c) 1996-2006 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */

package org.jhotdraw.app.action;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.gui.*;
import org.jhotdraw.gui.Worker;
import org.jhotdraw.gui.event.*;
import org.jhotdraw.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.JHotDrawFeatures;
import org.jhotdraw.app.View;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;
/**
 * Exits the application after letting the user review all unsaved views.
 *
 * @author  Werner Randelshofer
 * @version 1.0  04 January 2005  Created.
 */



//extend AbstractSaveBeforeAction instead of AbstractApplicationAction
public class ExitAction extends AbstractSaveBeforeAction {
    public final static String ID = "application.exit";
    
    /** Creates a new instance. */
    public ExitAction(Application app) {
        super(app);
        labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }

    @FeatureEntryPoint(JHotDrawFeatures.MANAGE_DRAWINGS)
    public void actionPerformed(ActionEvent evt) {
        //if the app is not enabled, it cannot do anything.
        if (!app.isEnabled()) {
            return;
        }
        //has to be set to false because an action is called that act on the application. 
        //after performing the action, the application is enabled again. it is used to avoid parallel invocation of an action
        app.setEnabled(false);
        checkIfUnsavedThenClose();
    }
    
    // method which calls the retrieveUnsavedViews and checks which are enabled.
    //if there are no unsaved views and but there are unsaved and enabled views, it invokes the checkUnsavedEnabledViews method. 
    //which creates a List containing Views that have unsaved changes. 
    protected void checkIfUnsavedThenClose(){
        List<View> unsavedViews = retrieveUnsavedViews();
        List<View> unsavedAndEnabledViews = unsavedViews.stream().filter(View::isEnabled).collect(Collectors.toList());
        if (unsavedViews.isEmpty() || !unsavedAndEnabledViews.isEmpty()) {
            checkUnsavedEnabledViews(unsavedAndEnabledViews);
        } else {
            app.setEnabled(true);
        }
    }
    
    // method which retrieves the unsaved views. It takees all the views, and filters them through, only returning the views which
    // have unsaved changes - doing this in a List consisting of Views.
    protected List<View> retrieveUnsavedViews() {
        Stream<View> allViews = app.views().stream();
        Stream<View> viewsWithUnsavedChanges = allViews.filter(View::hasUnsavedChanges);
        return viewsWithUnsavedChanges.collect(Collectors.toList());
    }
    
    // method which checks if there are any unsaved and enabled views. if there are none, the application is closed
    // if there are unsaved and enabled view, it prompts the user with the save prompt for each unsaved view.
    protected void checkUnsavedEnabledViews (List<View> unsavedAndEnabledViews) {
        if (unsavedAndEnabledViews.isEmpty()) {
            doExit();
        } else {
            for (View p : unsavedAndEnabledViews){
                p.setEnabled(false);
                showSavePrompt(p);
            }
        }
    }
   
    
    // method which checks if there is a view open in the application, and if yes, it then disposes of the current view.
    // if there are no unsaved views, it calls the doExit method which exits the application.
    @Override
    protected void doIt(View view) {
        if (view != null && view.getApplication() != null) {
            view.getApplication().dispose(view);
        }
        if (retrieveUnsavedViews().isEmpty()) {
            doExit();
        }
    }
    
    // method which exits/stops the application
    protected void doExit() {
        app.stop();
        System.exit(0);
    }
    
}