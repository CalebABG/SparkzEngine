package com.engine.JComponents;

import com.engine.GUIWindows.EException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;

//Credit to: http://stackabuse.com/example-adding-autocomplete-to-jtextfield/

public class AutoComplete implements DocumentListener {
    private enum Mode {INSERT, COMPLETION}

    private JTextField textField;
    private final List<String> keywords;
    private Mode mode = Mode.INSERT;

    public AutoComplete(JTextField textField, List<String> keywords) {
        this.textField = textField;
        this.keywords = keywords;
        Collections.sort(keywords);
    }

    public void changedUpdate(DocumentEvent ev) {}
    public void removeUpdate(DocumentEvent ev) {}

    public void insertUpdate(DocumentEvent ev) {
        if (ev.getLength() != 1)
            return;

        int pos = ev.getOffset();
        String content = null;
        try {
            content = textField.getText(0, pos + 1);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        // Find where the word starts
        int w;
        for (w = pos; w >= 0; w--) {
            if (!Character.isLetter(content.charAt(w))) {
                break;
            }
        }

        // Too few chars
        if (pos - w < 2)
            return;

        String prefix = content.substring(w + 1).toLowerCase();
        int n = Collections.binarySearch(keywords, prefix);
        if (n < 0 && -n <= keywords.size()) {
            String match = keywords.get(-n - 1);
            if (match.startsWith(prefix)) {
                // A completion is found
                String completion = match.substring(pos - w);
                // We cannot modify Document from within notification,
                // so we submit a task that does the change later
                SwingUtilities.invokeLater(new CompletionTask(completion, pos + 1));
            }
        } else {
            // Nothing found
            mode = Mode.INSERT;
        }
    }

    public class CommitAction extends AbstractAction {
        private static final long serialVersionUID = 5794543109646743416L;

        public void actionPerformed(ActionEvent ev) {
            if (mode == Mode.COMPLETION) {
                int pos = textField.getSelectionEnd();
                StringBuilder sb = new StringBuilder(textField.getText());
                sb.insert(pos, " ");
                textField.setText(sb.toString());
                textField.setCaretPosition(pos + 1);
                textField.setText(textField.getText().trim());
                mode = Mode.INSERT;
            } else {
                textField.replaceSelection("\t");
            }
        }
    }

    private class CompletionTask implements Runnable {
        private String completion;
        private int position;

        CompletionTask(String completion, int position) {
            this.completion = completion;
            this.position = position;
        }

        public void run() {
            StringBuilder sb = new StringBuilder(textField.getText());
            sb.insert(position, completion);
            textField.setText(sb.toString());
            textField.setCaretPosition(position + completion.length());
            textField.moveCaretPosition(position);
            mode = Mode.COMPLETION;
        }
    }

    //Credit for Undo functionality: http://stackoverflow.com/questions/10532286/how-to-use-ctrlz-and-ctrly-with-all-text-components
    public static void makeUndoable(JTextComponent pTextComponent) {
        final UndoManager undoMgr = new UndoManager();

        // Add listener for undoable events
        pTextComponent.getDocument().addUndoableEditListener(evt -> undoMgr.addEdit(evt.getEdit()));

        // Add undo/redo actions
        pTextComponent.getActionMap().put("undoKeystroke", new AbstractAction("undoKeystroke") {
            public void actionPerformed(ActionEvent evt) {try {if (undoMgr.canUndo()) {undoMgr.undo();}} catch (Exception e) {EException.append(e);}}});

        pTextComponent.getActionMap().put("redoKeystroke", new AbstractAction("redoKeystroke") {
            public void actionPerformed(ActionEvent evt) {try {if (undoMgr.canRedo()) {undoMgr.redo();}} catch (Exception e) {EException.append(e);}}});

        // Create keyboard accelerators for undo/redo actions (Ctrl+Z/Ctrl+Y)
        pTextComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "undoKeystroke");
        pTextComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), "redoKeystroke");
    }
}