/*
 * ContactPanel.java
 *
 * Created on 3. říjen 2007, 18:48
 */

package esmska.gui;

import esmska.data.Contact;
import esmska.persistence.PersistenceManager;
import esmska.utils.ActionEventSupport;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.jvnet.substance.SubstanceLookAndFeel;

/** Contact list panel
 *
 * @author  ripper
 */
public class ContactPanel extends javax.swing.JPanel {
    public static final int ACTION_CONTACT_SELECTION_CHANGED = 0;
    public static final int ACTION_CONTACT_CHOSEN = 1;
    
    private static final String RES = "/esmska/resources/";
    private TreeSet<Contact> contacts = PersistenceManager.getContacs();
    
    private Action addContactAction = new AddContactAction();
    private Action editContactAction = new EditContactAction();
    private Action removeContactAction = new RemoveContactAction();
    private Action chooseContactAction = new ChooseContactAction();
    private SearchContactAction searchContactAction = new SearchContactAction();
    private ContactListModel contactListModel = new ContactListModel();
    private ContactDialog contactDialog = new ContactDialog();

    // <editor-fold defaultstate="collapsed" desc="ActionEvent support">
    private ActionEventSupport actionSupport = new ActionEventSupport(this);
    public void addActionListener(ActionListener actionListener) {
        actionSupport.addActionListener(actionListener);
    }
    
    public void removeActionListener(ActionListener actionListener) {
        actionSupport.removeActionListener(actionListener);
    }
    // </editor-fold>
    
    /** Creates new form ContactPanel */
    public ContactPanel() {
        initComponents();
    }
    
    /** clear selection of contact list */
    public void clearSelection() {
        contactList.clearSelection();
    }
    
    /** set selected contact in contact list */
    public void setSelectedContact(Contact contact) {
        contactList.setSelectedValue(contact, true);
    }
    
    /** Return selected contacts
     * @return Collection of selected contacts. Zero length collection if noone selected.
     */
    public HashSet<Contact> getSelectedContacts() {
        HashSet<Contact> selectedContacts = new HashSet<Contact>();
        for (Object o : contactList.getSelectedValues())
            selectedContacts.add((Contact) o);
        return selectedContacts;
    }
    
    /** add new contacts to the contact list */
    public void addContacts(Collection<Contact> contacts) {
        contactListModel.addAll(contacts);
    }
    
    /** select first contact in contact list, if possible and no other contact selected */
    public void ensureContactSelected() {
        if (contactList.getSelectedIndex() < 0 && contactListModel.getSize() > 0)
            contactList.setSelectedIndex(0);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addContactButton = new javax.swing.JButton();
        removeContactButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        contactList = new javax.swing.JList();
        editContactButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Kontakty"));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        addContactButton.setAction(addContactAction);
        addContactButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        addContactButton.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);

        removeContactButton.setAction(removeContactAction);
        removeContactButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        removeContactButton.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);

        contactList.setModel(contactListModel);
        contactList.setToolTipText("Seznam kontaktů (Alt+K)");
        contactList.setCellRenderer(new ContactListRenderer());
        //key shortcuts
        String command = "choose contact";
        contactList.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), command);
        contactList.getActionMap().put(command, chooseContactAction);

        command = "focus contacts";
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_K,KeyEvent.ALT_DOWN_MASK), command);
        getActionMap().put(command, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                contactList.requestFocusInWindow();
            }
        });
        contactList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contactListMouseClicked(evt);
            }
        });
        contactList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                contactListValueChanged(evt);
            }
        });
        contactList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contactListKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                contactListKeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(contactList);

        editContactButton.setAction(editContactAction);
        editContactButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        editContactButton.putClientProperty(SubstanceLookAndFeel.FLAT_PROPERTY, Boolean.TRUE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addContactButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editContactButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeContactButton))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addContactButton)
                    .addComponent(editContactButton)
                    .addComponent(removeContactButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void contactListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_contactListValueChanged
        if (evt.getValueIsAdjusting())
            return;
        
        // update components
        int count = contactList.getSelectedIndices().length;
        removeContactAction.setEnabled(count != 0);
        editContactAction.setEnabled(count == 1);
        
        //fire event
        actionSupport.fireActionPerformed(ACTION_CONTACT_SELECTION_CHANGED, null);
    }//GEN-LAST:event_contactListValueChanged

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        contactList.requestFocusInWindow();
    }//GEN-LAST:event_formFocusGained

    private void contactListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contactListMouseClicked
        //transfer focus everytime but on single left click
        if (evt.getButton() != MouseEvent.BUTTON1 || evt.getClickCount() > 1)
            chooseContactAction.actionPerformed(null);
    }//GEN-LAST:event_contactListMouseClicked

    private void contactListKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactListKeyTyped
        //do not catch keyboard shortcuts
        if (evt.isActionKey() || evt.isAltDown() || evt.isAltGraphDown() ||
                evt.isControlDown() || evt.isMetaDown()) {
            return;
        }
        //skip when not letter nor digit nor whitespace
        if (! Character.isLetterOrDigit(evt.getKeyChar()) && ! Character.isWhitespace(evt.getKeyChar())) {
            return;
        }
    
        String searchString = searchContactAction.getSearchString();
        searchString += Character.toLowerCase(evt.getKeyChar());
        searchContactAction.setSearchString(searchString);
        searchContactAction.actionPerformed(null);
    }//GEN-LAST:event_contactListKeyTyped

    private void contactListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contactListKeyPressed
        //process backspace
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            String searchString = searchContactAction.getSearchString();
            if (searchString.length() > 0) {
                searchString = searchString.substring(0, searchString.length() - 1);
                searchContactAction.setSearchString(searchString);
                searchContactAction.actionPerformed(null);
            }
            return;
        }

        //move to another matching contact when searching and using arrows (and prolong the delay)
        if ((evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) &&
                !searchContactAction.getSearchString().equals("")) {
            int index = Math.max(contactList.getSelectedIndex(), 0);
            if (evt.getKeyCode() == KeyEvent.VK_DOWN) { //go to next matching contact
                index++;
                for (; index < contactListModel.getSize(); index++) {
                    Contact contact = contactListModel.getElementAt(index);
                    if (searchContactAction.isContactMatched(contact)) {
                        contactList.setSelectedValue(contact, true);
                        break;
                    }
                }
            } else { //go to previous matching contact
                index--;
                for (; index >= 0; index--) {
                    Contact contact = contactListModel.getElementAt(index);
                    if (searchContactAction.isContactMatched(contact)) {
                        contactList.setSelectedValue(contact, true);
                        break;
                    }
                }
            }
            evt.consume();
            searchContactAction.restartTimer();
        }
    }//GEN-LAST:event_contactListKeyPressed
    
    /** Add contact to contact list */
    private class AddContactAction extends AbstractAction {
        public AddContactAction() {
            super(null,new ImageIcon(ContactPanel.class.getResource(RES + "add.png")));
            this.putValue(SHORT_DESCRIPTION,"Přidat nový kontakt");
        }
        public void actionPerformed(ActionEvent e) {
            contactList.requestFocusInWindow(); //always transfer focus
            contactDialog.setTitle("Nový kontakt");
            contactDialog.show(null);
            Contact c = contactDialog.getContact();
            if (c == null)
                return;
            contactListModel.add(c);
            
            contactList.clearSelection();
            contactList.setSelectedValue(c, true);
        }
    }
    
    /** Edit contact from contact list */
    private class EditContactAction extends AbstractAction {
        public EditContactAction() {
            super(null,new ImageIcon(ContactPanel.class.getResource(RES + "edit.png")));
            this.putValue(SHORT_DESCRIPTION,"Upravit označený kontakt");
            this.setEnabled(false);
        }
        public void actionPerformed(ActionEvent e) {
            contactList.requestFocusInWindow(); //always transfer focus
            Contact contact = (Contact)contactList.getSelectedValue();
            contactDialog.setTitle("Upravit kontakt");
            contactDialog.show(contact);
            Contact c = contactDialog.getContact();
            if (c == null)
                return;
            contactListModel.remove(contact);
            contactListModel.add(c);
            
            contactList.clearSelection();
            contactList.setSelectedValue(c, true);
        }
    }
    
    /** Remove contact from contact list */
    private class RemoveContactAction extends AbstractAction {
        public RemoveContactAction() {
            super(null,new ImageIcon(ContactPanel.class.getResource(RES + "remove.png")));
            this.putValue(SHORT_DESCRIPTION,"Odstranit označené kontakty");
            this.setEnabled(false);
        }
        public void actionPerformed(ActionEvent e) {
            contactList.requestFocusInWindow(); //always transfer focus
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            JLabel label = new JLabel("<html><b>Opravdu smazat následující kontakty?</b></html>");
            JTextArea area = new JTextArea();
            area.setEditable(false);
            area.setRows(5);
            for (Object o : contactList.getSelectedValues())
                area.append(((Contact)o).getName() + "\n");
            area.setCaretPosition(0);
            panel.add(label, BorderLayout.PAGE_START);
            panel.add(new JScrollPane(area), BorderLayout.CENTER);
            //confirm
            int result = JOptionPane.showOptionDialog(MainFrame.getInstance(),panel,"Opravdu smazat?",
                    JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,null,JOptionPane.NO_OPTION);
            if (result != JOptionPane.YES_OPTION)
                return;
            //delete
            List<Object> list = Arrays.asList(contactList.getSelectedValues());
            contactListModel.removeAll(list);
        }
    }
    
    /** Choose contact in contact list by keyboard or mouse */
    private class ChooseContactAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            actionSupport.fireActionPerformed(ACTION_CONTACT_CHOSEN, null);
        }
    }
    
     /** Search for contact in contact list */
    private class SearchContactAction extends AbstractAction {
        private String searchString = "";
        /** "forgetting" timer, time to forget the searched string */
        private Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchString = "";
                SearchContactAction.this.actionPerformed(null);
            }
        });
        public SearchContactAction() {
            timer.setRepeats(false);
        }
        /** update the graphical highlighting */
        private void updateRendering() {
            ListCellRenderer renderer = contactList.getCellRenderer();
            contactList.setCellRenderer(null);
            contactList.setCellRenderer(renderer);
        }
        /** do the search */
        public void actionPerformed(ActionEvent e) {
            if (searchString.equals("")) {
                updateRendering();
                return;
            }
            for (int i = 0; i < contactListModel.getSize(); i++) {
                Contact contact = contactListModel.getElementAt(i);
                if (isContactMatched(contact)) {
                    contactList.setSelectedIndex(i);
                    //let 5 contacts be visible before and after the selected contact
                    for (int j = i - 5; j <= i + 5; j++) {
                        if (j >= 0 && j < contactListModel.getSize())
                            contactList.ensureIndexIsVisible(j);
                    }
                    contactList.ensureIndexIsVisible(i);
                    break;
                }
            }
            updateRendering();
            restartTimer();
        }
        /** @return true if contact is matched by search string, false otherwise */
        public boolean isContactMatched(Contact contact) {
            if (searchString.equals(""))
                return true;
            return (contact.getName().toLowerCase().contains(searchString) ||
                        contact.getCountryCode().concat(contact.getNumber()).contains(searchString));
        }
        /** set string to be searched in contact list */
        public void setSearchString(String searchString) {
            this.searchString = searchString;
        }
        /** get string searched in contact list */
        public String getSearchString() {
            return searchString;
        }
        /** force the search timer to restart (therefore prolong the delay) */
        public void restartTimer() {
            timer.restart();
        }
    }
    
    /** Model for contact list */
    private class ContactListModel extends AbstractListModel {
        public int getSize() {
            return contacts.size();
        }
        public Contact getElementAt(int index) {
            return contacts.toArray(new Contact[0])[index];
        }
        public int indexOf(Contact element) {
            return new ArrayList<Contact>(contacts).indexOf(element);
        }
        public void add(Contact element) {
            if (contacts.add(element)) {
                int index = indexOf(element);
                fireIntervalAdded(this, index, index);
            }
        }
        public boolean contains(Contact element) {
            return contacts.contains(element);
        }
        public boolean remove(Contact element) {
            int index = indexOf(element);
            boolean removed = contacts.remove(element);
            if (removed) {
                fireIntervalRemoved(this, index, index);
            }
            return removed;
        }
        public void removeAll(Collection<Object> elements) {
//            for (Object o : elements)
//                remove((Contact)o); //TODO fix 'out of memory' when using remove()
            int size = getSize();
            contacts.removeAll(elements);
            fireIntervalRemoved(this, 0, size);
        }
        public void addAll(Collection<Contact> elements) {
            contacts.addAll(elements);
            fireContentsChanged(this, 0, getSize());
        }
        @Override
        protected void fireIntervalRemoved(Object source, int index0, int index1) {
            super.fireIntervalRemoved(source, index0, index1);
        }
        @Override
        protected void fireIntervalAdded(Object source, int index0, int index1) {
            super.fireIntervalAdded(source, index0, index1);
        }
        @Override
        protected void fireContentsChanged(Object source, int index0, int index1) {
            super.fireContentsChanged(source, index0, index1);
        }
    }
    
    /** dialog for creating and editing contact */
    private class ContactDialog extends JDialog implements PropertyChangeListener {
        private final ImageIcon contactIcon = new ImageIcon(ContactPanel.class.getResource(RES + "contact-48.png"));
        private EditContactPanel panel;
        private JOptionPane optionPane;
        private Contact contact;
        public ContactDialog() {
            super((JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, ContactPanel.this),
                    "Kontakt", true);
            init();
            setDefaultCloseOperation(HIDE_ON_CLOSE);
        }
        private void init() {
            panel = new EditContactPanel();
            optionPane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.OK_CANCEL_OPTION, contactIcon);
            optionPane.addPropertyChangeListener(this);
            setContentPane(optionPane);
            pack();
        }
        public void show(Contact c) {
            init();
            setLocationRelativeTo(MainFrame.getInstance());
            optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
            panel.setContact(c);
            panel.prepareForShow();
            setVisible(true);
        }
        public void propertyChange(PropertyChangeEvent e) {
            String prop = e.getPropertyName();
            
            if (isVisible()
            && (e.getSource() == optionPane)
            && (JOptionPane.VALUE_PROPERTY.equals(prop))) {
                Object value = optionPane.getValue();
                
                if (value == JOptionPane.UNINITIALIZED_VALUE) {
                    //ignore reset
                    return;
                }
                if ((Integer)value != JOptionPane.OK_OPTION) {
                    setVisible(false);
                    return;
                }
                
                //verify inputs
                if (!panel.validateForm()) {
                    optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
                    return;
                }
                //inputs verified, all ok
                contact = panel.getContact();
                setVisible(false);
            }
        }
        public Contact getContact() {
            return contact;
        }
    }
    
    /** Renderer for items in contact list */
    private class ContactListRenderer implements ListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = (new DefaultListCellRenderer()).getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
            Contact contact = (Contact)value;
            JLabel label = ((JLabel)c);
            //add operator logo
            label.setIcon(contact.getOperator().getIcon());
            //set tooltip
            label.setToolTipText(contact.getNumber());
            //set background on non-matching contacts when searching
            if (!searchContactAction.getSearchString().equals("") &&
                    !searchContactAction.isContactMatched(contact)) {
                label.setBackground(label.getBackground().darker());
                label.setForeground(label.getForeground().darker());
            }
            return label;
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addContactButton;
    private javax.swing.JList contactList;
    private javax.swing.JButton editContactButton;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton removeContactButton;
    // End of variables declaration//GEN-END:variables
    
}
