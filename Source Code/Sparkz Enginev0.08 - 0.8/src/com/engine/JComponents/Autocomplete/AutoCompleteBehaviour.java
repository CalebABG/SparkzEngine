/**
 * Copyright 2017 Tobias Gierke <tobias.gierke@code-sourcery.de>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.engine.JComponents.Autocomplete;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.FocusManager;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 * Implements an auto-complete popup that may be attached
 * to any Swing {@link JTextComponent}.
 *
 * <p>Pressing CTRL-SPACE will show the auto-complete
 * popup. While the popup is shown, all key events
 * related to the following keys are consumed
 * and used to control the proposal selection:</p>
 *
 * <ul>
 *  <li>ESC   - cancels auto-completion and hides the popup</li>
 *  <li>SPACE / ENTER - inserts the currently selected auto-complete proposal into the text component</li>
 *  <li>CURSOR UP - selects the previous auto-complete proposal</li>
 *  <li>CURSOR DOWN - selects the next auto-complete proposal</li>
 * </ul>
 *
 * @author tobias.gierke@code-sourcery.de
 */
public class AutoCompleteBehaviour<T> {
    /**
     * Implementations provide auto-complete proposals.
     *
     * @author tobias.gierke@code-sourcery.de
     */
    public interface IAutoCompleteCallback<X> {
        /**
         * Returns auto-complete proposals.
         *
         * @param input the user input for which to find proposals
         * @return auto-complete proposals
         */
        List<X> getProposals(String input);

        /**
         * Returns the string that should be inserted
         * into the test component.
         *
         * @param value model value
         * @return the string
         */
        String getStringToInsert(X value);

        /**
         * Get user input for initial proposal.
         *
         * @param component text component
         * @param caretPosition caret position where the auto-complete proposal has been triggered
         * @return user input to use for fetching the initial set of proposals or <code>NULL</code> if no
         * proposals are available
         */
        InitialUserInput getInitialUserInput(JTextComponent component, int caretPosition);
    }

    public static abstract class DefaultAutoCompleteCallback<T> implements IAutoCompleteCallback<T> {
        public InitialUserInput getInitialUserInput(JTextComponent editor, int caretPosition) {
            final String text = editor.getText();
            final StringBuilder buffer = new StringBuilder();

            int start = caretPosition;
            while ((start - 1) >= 0 && !isSeparatorChar(text.charAt(start - 1))) {
                buffer.insert(0, text.charAt(start - 1));
                start--;
            }

            int end = editor.getCaretPosition();
            while (end < text.length() && !isSeparatorChar(text.charAt(end))) {
                buffer.append(text.charAt(end));
                end++;
            }

            return new InitialUserInput(start, buffer.toString());
        }

        protected boolean isSeparatorChar(char c) {
            return Character.isWhitespace(c);
        }
    }

    public static final class InitialUserInput {
        public final int caretPosition;
        public final String userInput;

        public InitialUserInput(int caretPosition, String userInput) {
            this.caretPosition = caretPosition;
            this.userInput = userInput;
        }
    }

    // popup window
    private Window popup;

    // editor this behaviour is attached to 
    public JTextComponent editor;

    // the list to display the auto-complete proposals
    private final JList<T> list = new JList<>();

    // custom list renderer to use
    private ListCellRenderer<? super T> listCellRenderer;

    // scroll pane holding the list 
    private JScrollPane scrollPane;

    private final AutoCompleteBuffer buffer = new AutoCompleteBuffer();

    // how many proposals to display
    // before scrollbars are being shown
    private int visibleRowCount = 3;

    // whether this behaviour is currently active
    // and we need to intercept keyboard events 
    private boolean autoCompleteActive;

    // time when this behaviour got activated,
    // needed to skip key event
    // for space key triggered by activation
    private long activationTimestamp;

    // initial size of the proposal window
    private Dimension initialPopupSize = new Dimension(100, 300);

    private final MyKeyDispatcher keyInterceptor = new MyKeyDispatcher();

    private final MyListModel listModel = new MyListModel();

    private final ChangeListener viewportListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            if (autoCompleteActive) {
                hidePopup(null);
            }
        }
    };

    private final CaretListener caretListener = new CaretListener() {
        public void caretUpdate(CaretEvent e) {
            if (autoCompleteActive) {
                if (!buffer.isValidOffset(e.getDot())) {
                    hidePopup(null);
                }
            }
        }
    };

    private final PropertyChangeListener focusListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent ev) {
            if (autoCompleteActive) {
                final Object newValue = ev.getNewValue();
                if (newValue instanceof Component && "focusOwner".equals(ev.getPropertyName())) {
                    if (!(isChildOf(editor, (Component) newValue) || isChildOf(popup, (Component) newValue))) {
                        hidePopup(null);
                    }
                }
            }
        }
    };

    protected final class MyListModel extends AbstractListModel<T> {
        private final List<T> data = new ArrayList<>();

        public MyListModel() {}

        public boolean isEmpty() {
            return data.isEmpty();
        }

        public void setData(List<T> newData) {
            this.data.clear();
            this.data.addAll(newData);
            fireContentsChanged(this, 0, this.data.size());
        }

        public int getSize() {
            return data.size();
        }
        public T getElementAt(int index) {
            return data.get(index);
        }
    }

    private final class MyKeyDispatcher implements KeyEventDispatcher {
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (!autoCompleteActive) {
                return false;
            }
            boolean eventHandled = false;
            if (isKeyTyped(e)) {

                switch (e.getKeyChar()) {
                    case ' ':
                    case 0x10:
                    case 0x0a:
                    case 0x1b: // ESC
                        e.consume();
                        eventHandled = true;
                        break;
                    default:
                }
            } else if (isKeyPress(e)) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_ENTER:
                    case KeyEvent.VK_SPACE:
                    case KeyEvent.VK_ESCAPE:
                        e.consume();
                        eventHandled = true;
                        break;
                    default:
                }
            } else if (isKeyRelease(e)) {
                final int selectedIndex = list.getSelectedIndex();
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    e.consume();
                    eventHandled = true;
                    hidePopup(null);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    e.consume();
                    eventHandled = true;
                    setSelectedIndex(selectedIndex + 1);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    e.consume();
                    eventHandled = true;
                    setSelectedIndex(selectedIndex - 1);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
                    e.consume();
                    eventHandled = true;
                    final boolean isSpace = e.getKeyCode() == KeyEvent.VK_SPACE;
                    final boolean ctrlDown = isCtrlDown(e);
                    final long timeSinceActivationMillis = timeSinceActivationMillis(e);
                    final boolean spaceTrigger = !ctrlDown && isSpace && timeSinceActivationMillis > 200;
                    if (e.getKeyCode() == KeyEvent.VK_ENTER || spaceTrigger) {
                        final int idx = list.getSelectedIndex();
                        final T selectedValue;
                        if (idx >= listModel.getSize()) {
                            selectedValue = null;
                        } else {
                            selectedValue = list.getSelectedValue();
                        }
                        // = list.getSelectedValue();
                        hidePopup(selectedValue);
                    }
                }
            }
            return eventHandled;
        }
    }

    private IAutoCompleteCallback<T> callback = new DefaultAutoCompleteCallback<T>() {
        public List<T> getProposals(String input) {
            return Collections.emptyList();
        }
        public String getStringToInsert(T item) {
            return item == null ? "" : item.toString();
        }
    };

    private final class AutoCompleteBuffer {
        private final StringBuilder buffer = new StringBuilder();
        private int autoCompleteCaretStartPosition;

        public void setInitialContent(String initialInput, int autoCompleteCaretStartPosition) {
            this.autoCompleteCaretStartPosition = autoCompleteCaretStartPosition;
            buffer.setLength(0);
            buffer.insert(localOffset(autoCompleteCaretStartPosition), initialInput);
        }

        private int localOffset(int input) {
            return input - autoCompleteCaretStartPosition;
        }

        public void insert(String s, int editorCaretPosition) {
            if (isValidOffset(editorCaretPosition)) {
                buffer.insert(localOffset(editorCaretPosition), s);
                updateProposals();
            } else {
                hidePopup(null);
            }
        }

        public boolean isValidOffset(int offset) {
            return offset >= autoCompleteCaretStartPosition && offset <= (autoCompleteCaretStartPosition + buffer.length());
        }

        public void remove(int len, int offset) {
            if (isValidOffset(offset)) {
                final int start = localOffset(offset);
                final int end = start + len;
                buffer.delete(start, end);
                updateProposals();
            } else {
                hidePopup(null);
            }
        }

        public int length() {
            return buffer.length();
        }
        public String getValue() {
            return buffer.toString();
        }
    }

    private final KeyAdapter keyListener = new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
            // CTRL+SPACE is hotkey
            if (e.getKeyCode() == KeyEvent.VK_SPACE && isCtrlDown(e)) {
                try {
                    final InitialUserInput p = callback.getInitialUserInput(editor, editor.getCaretPosition());
                    final Rectangle rect = editor.modelToView(p.caretPosition);
                    showPopup(rect, e.getWhen(), p.userInput, p.caretPosition);
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }
        }

    };

    private DocumentListener documentListener = new DocumentListener() {
        @Override
        public void removeUpdate(DocumentEvent e) {
            if (autoCompleteActive) {
                buffer.remove(e.getLength(), e.getOffset());
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (autoCompleteActive) {
                final int len = e.getLength();
                final int offset = e.getOffset();
                final String text = editor.getText().substring(offset, offset + len);
                buffer.insert(text, offset);
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) { /* NOP */ }
    };

    public AutoCompleteBehaviour() {
        list.setModel(listModel);
    }

    protected void showPopup(Rectangle rect, long activationTimestamp, String initialContent, int autoCompleteStartCaretPosition) {
        if (!autoCompleteActive) {
            buffer.setInitialContent(initialContent, autoCompleteStartCaretPosition);
            final List<T> choices = createProposals();
            if (choices.isEmpty()) {
                return;
            }

            autoCompleteActive = true;

            this.activationTimestamp = activationTimestamp;

            final Window parentContainer = (Window) editor.getTopLevelAncestor();
            if (parentContainer == null) {
                throw new IllegalStateException("Editor has not been added to a container ?");
            }

            popup = createPopupWindow(parentContainer);

            if (listCellRenderer != null) {
                list.setCellRenderer(listCellRenderer);
            }
            list.setVisibleRowCount(this.visibleRowCount);

            list.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                        int idx = list.locationToIndex(e.getPoint());
                        if (idx >= 0 && idx < listModel.getSize()) {
                            hidePopup(listModel.data.get(idx));
                        }
                    }
                }

            });
            scrollPane = new JScrollPane(list);
            list.setFont(new Font("Times", Font.PLAIN, 17));
            popup.add(scrollPane);

            final Point loc = editor.getLocationOnScreen();
            loc.x += rect.x;
            loc.y += rect.y + editor.getFontMetrics(editor.getFont()).getHeight();

            popup.setLocation(loc);
            popup.pack();
            popup.setVisible(true);

            setProposals(choices);
        }
    }

    protected Window createPopupWindow(Window parentContainer) {
        Window popup = new Window(parentContainer);
        popup.setFocusable(false);
        popup.setAutoRequestFocus(false);
        popup.setSize(initialPopupSize);
        return popup;
    }

    protected void hidePopup(T selection) {
        if (autoCompleteActive) {
            autoCompleteActive = false;
            popup.setVisible(false);
            popup.dispose();

            if (selection != null) {
                final String userChoice = callback.getStringToInsert(selection);
                if (userChoice != null && userChoice.length() > 0) {
                    final Document doc = editor.getDocument();
                    try {
                        doc.remove(buffer.autoCompleteCaretStartPosition, buffer.length());
                        doc.insertString(buffer.autoCompleteCaretStartPosition, userChoice, null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Enable auto-complete on a {@link JTextComponent}.
     *
     * <p>Make sure you set a custom {@link IAutoCompleteCallback callback} , otherwise
     * it will look like auto-completion is not working.</p>
     * <p>This behaviour may only be attached to at most text component at any
     * time. Use {@link #detach()} to detach it from its current editor component.</p> 
     *
     * @param editor the text component that should support auto-completion
     * @see #isAttached()
     * @see #detach()
     */
    public void attachTo(JTextComponent editor) {
        if (!SwingUtilities.isEventDispatchThread()) {
            // required because FocusManager.getCurrentManager() returns a thread-local            
            throw new IllegalStateException("This method must be called from the EDT");
        }
        if (isAttached()) {
            throw new IllegalStateException("Already attached to " + this.editor);
        }
        this.editor = editor;

        if (editor.getParent() instanceof JViewport) {
            ((JViewport) editor.getParent()).addChangeListener(viewportListener);
        }

        final FocusManager focusManager = FocusManager.getCurrentManager();
        focusManager.addPropertyChangeListener(focusListener);

        editor.getDocument().addDocumentListener(documentListener);
        editor.addKeyListener(keyListener);

        editor.addCaretListener(caretListener);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyInterceptor);
    }

    /**
     * Detaches this behaviour from its current editor.
     *
     * <p>If this behaviour is currently not attached, nothing (bad) happens.</p>
     *
     * @see #isAttached()
     * @see #attachTo(JTextComponent)
     */
    public void detach() {
        if (!SwingUtilities.isEventDispatchThread()) {
            // required because FocusManager.getCurrentManager() returns a thread-local
            throw new IllegalStateException("This method must be called from the EDT");
        }

        if (isAttached()) {
            hidePopup(null);

            if (editor.getParent() instanceof JViewport) {
                ((JViewport) editor.getParent()).removeChangeListener(viewportListener);
            }

            editor.getDocument().removeDocumentListener(documentListener);
            editor.removeKeyListener(keyListener);
            editor.removeCaretListener(caretListener);

            final FocusManager focusManager = FocusManager.getCurrentManager();
            focusManager.removePropertyChangeListener(focusListener);

            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyInterceptor);
            this.editor = null;
        }
    }


    /**
     * Check whether this behaviour is currently attached to a text component.
     *
     * @return <code>true</code> if this behaviour is attached to a text component
     * @see #attachTo(JTextComponent)
     * @see #detach()
     */
    public boolean isAttached() {
        return this.editor != null;
    }

    /**
     * Sets the callback that provides auto-complete proposals.
     *
     * @param callback callback
     */
    public void setCallback(IAutoCompleteCallback<T> callback) {
        this.callback = callback;
    }

    protected void updateProposals() {
        setProposals(createProposals());
    }

    protected List<T> createProposals() {
        return callback.getProposals(buffer.getValue());
    }

    protected void setProposals(List<T> choicesArray) {
        listModel.setData(choicesArray);
        if (!choicesArray.isEmpty()) {
            list.setSelectedIndex(0);
        }
    }

    /**
     * Sets the max. number of proposals that should be visible.
     *
     * If the callback returns more proposals, a scroll bar will be shown.
     *
     * @param elementsToShow number of elements to show
     */
    public void setVisibleRowCount(int elementsToShow) {
        if (elementsToShow < 1) {
            throw new IllegalArgumentException("elementsToShow needs to be >= 1");
        }
        this.visibleRowCount = elementsToShow;
    }

    protected void setSelectedIndex(int selectedIndex) {
        if (selectedIndex >= 0 && selectedIndex < list.getModel().getSize()) {
            list.setSelectedIndex(selectedIndex);
            list.ensureIndexIsVisible(selectedIndex);
        }
    }

    /**
     * Sets the initial size of the proposal popup.
     *
     * @param initialPopupSize initial size
     */
    public void setInitialPopupSize(Dimension initialPopupSize) {
        if (initialPopupSize == null) {
            throw new IllegalArgumentException("Popup size cannot be NULL");
        }
        this.initialPopupSize = new Dimension(initialPopupSize);
    }

    /**
     * Sets the {@link ListCellRenderer} to use when rendering poposals.
     *
     * @param listRenderer renderer to use
     */
    public void setListCellRenderer(ListCellRenderer<? super T> listRenderer) {
        this.listCellRenderer = listRenderer;
    }
    
    /*
     * Various helper methods.
     */

    protected T getCurrentSelection() {
        final int idx = list.getSelectedIndex();
        final T selectedValue;
        if (idx >= listModel.getSize()) {
            selectedValue = null;
        } else {
            selectedValue = list.getSelectedValue();
        }
        return selectedValue;
    }

    protected static boolean isKeyPress(KeyEvent e) {
        return e.getID() == KeyEvent.KEY_PRESSED;
    }

    protected static boolean isKeyRelease(KeyEvent e) {
        return e.getID() == KeyEvent.KEY_RELEASED;
    }

    protected static boolean isKeyTyped(KeyEvent e) {
        return e.getID() == KeyEvent.KEY_TYPED;
    }

    protected static boolean isCtrlDown(KeyEvent e) {
        return (e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0;
    }

    protected long timeSinceActivationMillis(KeyEvent e) {
        return e.getWhen() - activationTimestamp;
    }

    private static boolean isChildOf(Component parent, Component potentialChild) {
        Component current = potentialChild;
        while (current != null && current != parent) {
            current = current.getParent();
        }
        return current == parent;
    }
}