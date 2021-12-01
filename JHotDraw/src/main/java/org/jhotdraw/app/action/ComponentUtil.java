/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.app.action;

/**
 *
 * @author patrick
 *
 */
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import javax.swing.*;
import org.jhotdraw.app.EditableComponent;

public class ComponentUtil {

    // Decompose conditional
    private static boolean isJComponent(Component component) {
        return component instanceof JComponent;
    }

    private static boolean isEditableComponent(Component component) {
        return component instanceof EditableComponent;
    }

    public static Component getComponentFocusOwner() {
        return KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
    }

    public static JComponent getJComponentFocusOwner() {
        Component focusOwner = getComponentFocusOwner();
        return isJComponent(focusOwner) ? (JComponent) focusOwner : null;
    }

    public static EditableComponent getEditableComponentFocusOwner() {
        Component focusOwner = getComponentFocusOwner();
        return isEditableComponent(focusOwner) ? (EditableComponent) focusOwner : null;
    }

    public static void export(JComponent component) {
        component.getTransferHandler().exportToClipboard(
                component,
                component.getToolkit().getSystemClipboard(),
                TransferHandler.MOVE
        );
    }

    public static void copy(JComponent component) {
        component.getTransferHandler().exportToClipboard(
                component,
                component.getToolkit().getSystemClipboard(),
                TransferHandler.COPY
        );
    }
}
