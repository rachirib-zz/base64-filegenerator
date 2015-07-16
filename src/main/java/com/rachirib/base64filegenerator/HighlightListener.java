package com.rachirib.base64filegenerator;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.text.JTextComponent;
 
public class HighlightListener implements DocumentListener {
 
    JTextComponent comp = null;
    Border defaultBorder = null;
    Border highlightBorder =
            BorderFactory.createLineBorder(java.awt.Color.ORANGE);
 
    public HighlightListener(JTextComponent jtc) {
        comp = jtc;
        defaultBorder = comp.getBorder();
        // Adding this listener to a specified component:
        comp.getDocument().addDocumentListener(this);
        // Highlight if empty:
        this.maybeHighlight();
    }
 
    public void insertUpdate(DocumentEvent e) {
        maybeHighlight();
    }
 
    public void removeUpdate(DocumentEvent e) {
        maybeHighlight();
    }
 
    public void changedUpdate(DocumentEvent e) {
        maybeHighlight();
    }
 
    private void maybeHighlight() {
        if (comp.getText().trim().length() > 0)
            // if a field is non-empty, switch it to default look
            comp.setBorder(defaultBorder);
        else
            // if a field is empty, highlight it
            comp.setBorder(highlightBorder);
        // ... more actions
    }
}

