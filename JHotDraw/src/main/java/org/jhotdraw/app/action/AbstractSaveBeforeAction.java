/*
 * @(#)AbstractSaveBeforeAction.java  2.0  2006-06-15
 *
 * Copyright (c) 1996-2007 by the original authors of JHotDraw
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

import org.jhotdraw.gui.Worker;
import org.jhotdraw.io.*;
import org.jhotdraw.gui.*;
import org.jhotdraw.gui.event.*;
import org.jhotdraw.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;

/**
 * Base class for actions that can only be safely performed when a 
 * {@link org.jhotdraw.app.View} has no unsaved changes.
 * <p>
 * If the view has no unsaved changes, method doIt is invoked immediately.
 * If unsaved changes are present, a dialog is shown asking whether the user
 * wants to discard the changes, cancel or save the changes before doing it.
 * If the user chooses to discard the chanegs, doIt is invoked immediately.
 * If the user chooses to cancel, the action is aborted.
 * If the user chooses to save the changes, the view is saved, and doIt
 * is only invoked after the view was successfully saved.
 *
 * @author  Werner Randelshofer
 * @version 2.0 2006-06-15 Reworked. 
 * <br>1.2 2006-05-19 Make filename acceptable by ExtensionFileFilter.
 * <br>1.1 2006-05-03 Localized messages.
 * <br>1.0 27. September 2005 Created.
 */
public abstract class AbstractSaveBeforeAction extends AbstractViewAction {

    private Component oldFocusOwner;
    protected Application app;
    
    protected ResourceBundleUtil labels;
    protected final String SAVELABEL = "file.saveBefore.saveOption.text";
    protected final String DONTSAVELABEL = "file.saveBefore.dontSaveOption.text";
    protected final String CANCELLABEL = "file.saveBefore.cancelOption.text";
    protected final String COULDNOTSAVE = "file.saveBefore.couldntSave.message";
    protected final String DOYOUWANTTOSAVE = "file.saveBefore.doYouWantToSave.message";

    /** Creates a new instance. */
    public AbstractSaveBeforeAction(Application app) {
        super(app);
        this.app = app;
    }

    public void actionPerformed(ActionEvent evt) {
        final View p = getActiveView();
        labels = ResourceBundleUtil.getBundle("org.jhotdraw.app.Labels");
        
        //if there are no unsaved changes, return immediately
        if(!p.isEnabled()){
            return;
        }
        
        //if there are unsaved changes, prompts to ask if you want to save, exit without saving, or cancelling it
        Window wAncestor = SwingUtilities.getWindowAncestor(p.getComponent());
        oldFocusOwner = (wAncestor == null) ? null : wAncestor.getFocusOwner();
        p.setEnabled(false);
        saveIfAnyUnsavedChanges(p);
        p.setEnabled(true);
        returnFocusOrigin();
        
    }
    
    // method which promps user to save, exit without saving, or cancelling if there are no unsaved changes
    // if there are no unsaved changes, it exits immediately
    protected void saveIfAnyUnsavedChanges(View p){
        if (p.hasUnsavedChanges()) {
            showSavePrompt(p);
        } else {
            doIt(p);
        }
    }
    
    // returns focus to the origin
    protected void returnFocusOrigin(){
        if(oldFocusOwner != null){
            oldFocusOwner.requestFocus();
        }
    }
    
    
    // method that create the save prompt pane with the different labels, which are mentioned earlier
    protected JOptionPane createSavePrompt(){
        JOptionPane pane = new JOptionPane(
                        "<html>" + UIManager.getString("OptionPane.css") +
                        labels.getString(DOYOUWANTTOSAVE),
                        JOptionPane.WARNING_MESSAGE);
        Object[] options = {
        labels.getString(SAVELABEL),
        labels.getString(DONTSAVELABEL), 
        labels.getString(CANCELLABEL)
        };
        pane.setOptions(options);
        pane.setInitialValue(options[0]);
        pane.putClientProperty("Quaqua.OptionPane.destructiveOption", new Integer(2));
        return pane;
    }
    
    
    // method which gets and shows the save prompt pane and creates a SheetListener
    protected void showSavePrompt(View p){
        JSheet.showSheet(createSavePrompt(), p.getComponent(), new OptionsSheetListener(p));
    }
    

    // method which is called if you press save changes on the prompt. creates a SheetListener which saves the file.
    protected void saveChanges(final View p) {
        if (p.getFile() == null) {
            JFileChooser fileChooser = p.getSaveChooser();
            JSheet.showSaveSheet(fileChooser, p.getComponent(), new SaveSheetListener(p));
        } else {
            saveToFile(p, p.getFile());
        }
    }
    
    // method which actualy saves the file. this is not changed
    protected void saveToFile(final View p, final File file) {
        p.execute(new Worker() {

            public Object construct() {
                try {
                    p.write(file);
                    return null;
                } catch (IOException e) {
                    return e;
                }
            }

            public void finished(Object value) {
                fileSaved(p, file, value);
            }
        });
    }

    // almost not changed. Calls the showErrorPrompt message instead of writing it directly there.
    protected void fileSaved(View p, File file, Object value) {
        if (value == null) {
            p.setFile(file);
            p.markChangesAsSaved();
            doIt(p);
        } else {
            showErrorPrompt(file, value);
        }
        p.setEnabled(true);
        if (oldFocusOwner != null) {
            oldFocusOwner.requestFocus();
        }
    }
    
    // method to post if there is an error. extracted from the fileSaved method
    protected void showErrorPrompt(File file, Object value){
        String message;
            if ((value instanceof Throwable) && ((Throwable) value).getMessage() != null) {
                message = ((Throwable) value).getMessage();
            } else {
                message = value.toString();
            }
            String sendMessage = "<html>" + UIManager.getString("OptionPane.css") +
                    "<b>" + labels.getFormatted(COULDNOTSAVE, file.getName()) + "</b><br>" +
                    ((message == null) ? "" : message);
            JSheet.showMessageSheet(getActiveView().getComponent(), sendMessage, JOptionPane.ERROR_MESSAGE);
    }
    
    // a SheetListener is a public interface which is an object that can inspect a Sheet as it is being transformed. An object of this class
    // is created when the user has clicked exit and has unsaved changes, causing a JOptionsPane to prompt.
    protected class OptionsSheetListener implements SheetListener {
        private final View p;
        
        public OptionsSheetListener (View p) {
            this.p = p;
        }
        
        public void optionSelected(SheetEvent evt){
            Object value = evt.getValue();
            if (value instanceof String){
                parseLabel((String) value);
            } else {
                p.setEnabled(true);
            }
        }
        
        // Parses the labels and does the appropriate things according to what option is chosen from the message popping up on the screen
        private void parseLabel(String value){
            if (value.equals(labels.getString(CANCELLABEL))){
                p.setEnabled(true);
            } else if (value.equals(labels.getString(DONTSAVELABEL))){
                doIt(p);
                p.setEnabled(true);
            } else if (value.equals(labels.getString(SAVELABEL))){
                saveChanges(p);
            }
        }
    } 
    
    // a SheetListener is a public interface which is an object that can inspect a Sheet as it is being transformed. An object of this class is
    // created when the user has selected "save" on the prompt from the JOptionPane or JFileChooser pane on the JSheet.
    protected class SaveSheetListener implements SheetListener {
        private final View p;
        
        public SaveSheetListener (View p) {
            this.p = p;
        }
        
        public void optionSelected(final SheetEvent evt) {
            if (evt.getOption() == JFileChooser.APPROVE_OPTION){
                final File file = getSelectedFile(evt);
                saveToFile(p, file);
            } else {
                p.setEnabled(true);
                returnFocusOrigin();
            }
        }
        
        private File getSelectedFile (SheetEvent evt) {
            if (evt.getFileChooser().getFileFilter() instanceof ExtensionFileFilter) {
                return ((ExtensionFileFilter) evt.getFileChooser().getFileFilter()).makeAcceptable(evt.getFileChooser().getSelectedFile());
            } else {
                return evt.getFileChooser().getSelectedFile();
            }
        }
    }

    protected abstract void doIt(View p);
}
