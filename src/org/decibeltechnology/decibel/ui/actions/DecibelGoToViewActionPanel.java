package org.decibeltechnology.decibel.ui.actions;

import java.awt.Cursor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import org.netbeans.modules.csl.api.UiUtils;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Ondřej Brejla <ondrej@brejla.cz>
 */
public class DecibelGoToViewActionPanel extends javax.swing.JPanel implements FocusListener {
    
    private final List<FileObject> views;

    private final DecibelGoToViewActionPopup popup;

    /** Creates new form DecibelGoToViewActionPanel */
    public DecibelGoToViewActionPanel(List<FileObject> views, DecibelGoToViewActionPopup popup) {
        this.views = views;
        this.popup = popup;
        
        initComponents();
        
        viewsList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        addFocusListener(this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        titleLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewsList = new javax.swing.JList();

        setFocusCycleRoot(true);
        setLayout(new java.awt.GridBagLayout());

        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText(org.openide.util.NbBundle.getMessage(DecibelGoToViewActionPanel.class, "DecibelGoToViewActionPanel.titleLabel.text")); // NOI18N
        titleLabel.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(titleLabel, gridBagConstraints);

        viewsList.setModel(createListModel());
        viewsList.setSelectedIndex(0);
        viewsList.setVisibleRowCount(views.size());
        viewsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewsListMouseClicked(evt);
            }
        });
        viewsList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                viewsListKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(viewsList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void viewsListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_viewsListKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && evt.getModifiers() == 0) {
            openSelected();
        }
    }//GEN-LAST:event_viewsListKeyPressed

    private void viewsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewsListMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 1) {
            openSelected();
        }
    }//GEN-LAST:event_viewsListMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JList viewsList;
    // End of variables declaration//GEN-END:variables

    private void openSelected() {
        DecibelViewItem view = (DecibelViewItem) viewsList.getSelectedValue();
        
        if (view != null) {
            FileObject fo = view.getFileObject();
            if (fo != null) {
                UiUtils.open(fo, 0);
            }
        }
        
        popup.hide();
    }
    
    private ListModel createListModel() {
        DefaultListModel dlm = new DefaultListModel();
        
        for (FileObject view : views) {
            dlm.addElement(new DecibelViewItem(view));
        }
        
        return dlm;
    }
    
    @Override
    public void focusGained(FocusEvent arg0) {
        viewsList.requestFocus();
        viewsList.requestFocusInWindow();
    }
    
    @Override
    public void focusLost(FocusEvent arg0) {
    }
    
    private class DecibelViewItem {
        
        private FileObject view;
        
        public DecibelViewItem(FileObject view) {
            this.view = view;
        }
        
        public FileObject getFileObject() {
            return view;
        }
        
        @Override
        public String toString() {
            return view.getNameExt();
        }
        
    }
    
}