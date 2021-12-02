/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jhotdraw.samples.svg.gui;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jhotdraw.app.JHotDrawFeatures;

/**
 *
 * @author marku
 */
public class ProxyPanel extends JPanel {
        private Runnable runner;
        private final AbstractToolBar atb;
        
        public ProxyPanel(AbstractToolBar atb) {
            this.atb = atb;
            setOpaque(false);
            setBackground(Color.GREEN);
            // The paint method is only called, if the proxy panel is at least
            // one pixel wide and high.
            setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
        }

        @Override
        @FeatureEntryPoint(JHotDrawFeatures.TOOL_PALETTE)
        public void paint(Graphics g) {
            super.paint(g);
            final int state = atb.getDisclosureState();
            setContraints(state);
            if (runner == null) {
                runner = () -> {
                    setContraints(state);
                } //Maybe this runnable, could be its own class with its own name, extending runnable.
                ; 
                SwingUtilities.invokeLater((Runnable) runner);
            }
        }
        public void setContraints(int state){
            JComponent[] panels = atb.getPanels();
            try {
                panels[state] = atb.createDisclosedComponent(state);
            } catch (Throwable t) {
                t.printStackTrace();
                panels[state]=null;
            }
            atb.setPanels(panels);
            setProxyPanel(state);
            
        }
        private void setProxyPanel(int state){
            JComponent parent = (JComponent) getParent();
            if (parent != null) {
                GridBagLayout layout = (GridBagLayout) parent.getLayout();
                GridBagConstraints gbc = layout.getConstraints(ProxyPanel.this);
                parent.remove(ProxyPanel.this);
                parent = addPanel(state, parent, gbc);
                parent.revalidate();
                ((JComponent) parent.getRootPane().getContentPane()).revalidate();
            }
        }
        private JComponent addPanel(int state, JComponent parent, GridBagConstraints gbc){
            if (atb.getDisclosureState() == state) {
                    if (atb.getPanels()[state] != null) {
                        parent.add(atb.getPanels()[state], gbc);
                    } else {
                        JPanel empty = new JPanel(new BorderLayout());
                        empty.setOpaque(false);
                        parent.add(empty, gbc);
                    }
                }
            return parent;
        }
    }
