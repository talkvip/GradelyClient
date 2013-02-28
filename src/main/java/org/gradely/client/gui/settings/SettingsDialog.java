/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.gui.settings;

import java.awt.Component;
import java.awt.Dimension;
import java.net.URL;
import java.util.LinkedList;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.gradely.client.gui.DirectoryPanel;
import org.gradely.client.gui.GuiUtilities;
import org.gradely.client.gui.PanelAbstractClass;
import org.gradely.client.gui.UserPanel;
import org.gradely.client.logging.Logging;


/**
 * Displays the Main Settings Window and all that other junk.
 * @author Matt
 */
public class SettingsDialog {

    //================= Fields =================================
    
    private static JFrame omnies;
    private static JLabel topImage;
    private static JTree left; //Ha! Left Field. Take me out to the ball game...
    private static JScrollPane leftScroll;
    private static JSplitPane splitter;
    private static PanelAbstractClass right;
    private static BottomButtons buttons;
    
    //================= Constructors ===========================

    //private SettingsDialog() {

    //}

    //================= Methods ================================
    
    /**
     * Actually displays the settings window.
     */
    public static void init() {

        //Set Look and Feel
        GuiUtilities.setLookAndFeel();
        
        //Whole Frame
        //======================================================================================
        omnies = new JFrame(org.gradely.client.config.Constants.formalAppName);
        ImageIcon icon = new ImageIcon(org.gradely.client.config.Configuration.class.getResource("icons/logo.png"));
        omnies.setIconImage(icon.getImage());

        //Top Side 
        //======================================================================================
        topImage = new javax.swing.JLabel();
        topImage.setText("");
        topImage.setIcon(new ImageIcon(org.gradely.client.config.Configuration.class.getResource("icons/topbarlogo.png")));
        
        
        //JTree left side settings selector 
        //======================================================================================
        left = new JTree(settingsPanels());
        left.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); // Allows for only one tree item to be selected at a time
        left.addTreeSelectionListener(null);
        left.setCellRenderer(new SettingsTreeCellRenderer());
        left.setRootVisible(false);
        left.addTreeSelectionListener(new LeftSideListener());

        // Expand all
        for (int i = 0; i < left.getRowCount(); i++) {
            left.expandRow(i);
        }

        //Right Side
        //=====================================================================================
        //To create the right side we load a JPanel from elsewhere
        right = new UserPanel();
        getRight().loadForms();

        
        //We need a Scroll Panel for the left side
        //======================================================================================
        leftScroll = new JScrollPane();
        
        //Need a split panel to seperate the left and right sides
        //======================================================================================
        splitter = new JSplitPane();
        
        //Buttons on the bottom
        //======================================================================================
        buttons = new BottomButtons();
        
        //Put it all togeather
        //======================================================================================
        GroupLayout layout = new GroupLayout(omnies);
        
        //This code was generated by the NetBeans GUI WYSIWYG
//        omnies.setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 506, Short.MAX_VALUE)
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 365, Short.MAX_VALUE)
//        );

        splitter.setRightComponent(getRight());

        leftScroll.setViewportView(left);
        leftScroll.setMinimumSize(new Dimension(200,0));

        splitter.setLeftComponent(leftScroll);

        javax.swing.GroupLayout subLayout = new javax.swing.GroupLayout(omnies.getContentPane()); //omnies?
        omnies.getContentPane().setLayout(subLayout);
        subLayout.setHorizontalGroup(
            subLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttons, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(splitter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
        );
        subLayout.setVerticalGroup(
            subLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subLayout.createSequentialGroup()
                .addComponent(topImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        ));

        //Clean up
        //=======================================================================================
        omnies.pack();
        
        omnies.setVisible(true);
        topImage.setVisible(true);
        leftScroll.setVisible(true);
        left.setVisible(true);
        splitter.setVisible(true);
        getRight().setVisible(true);
        buttons.setVisible(true);
        
        //Set fields
        //========================================================================================

    }
    
    /**
     * Creates a tree model for the left side options and selectors. Each option of the tree corisponds with a JPanel
     * @return a mutable tree node for a JTree
     */
    private static MutableTreeNode settingsPanels()
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        
        for (TreeItem ti:treeItems())
        {
            root.add(new DefaultMutableTreeNode(ti));
        }
        
        return root;
    }
    
    /**
     * This method returns a list of tree items that are then used to populate the left-side tree. Also used for determining which list item was selected. Don't use the same name for multiple entries.
     * @return Properly filled out TreeItems ready for use.
     */
    protected static LinkedList<TreeItem> treeItems()
    {
        LinkedList<TreeItem> ll = new LinkedList<TreeItem>();
        
        //TODO icon icons/usericon.png
        ll.add(new TreeItem("User and Server Settings",org.gradely.client.config.Configuration.class.getResource("icons/blank.png") , new UserPanel())); //org.gradely.client.config.Configuration.class.getResource("icons/usericon.png")
        //TODO icon icons/appicon.png
        ll.add(new TreeItem("Directory Settings",org.gradely.client.config.Configuration.class.getResource("icons/blank.png") , new DirectoryPanel())); //org.gradely.client.config.Configuration.class.getResource("icons/foldericon.png")
        
        return ll;
        
    }
    
    

    /**
     * @return the right
     */
    public static PanelAbstractClass getRight() {
        return right;
    }
    
    
    /**
    * This renders the left-side tree into the appropriate form.
    * @see http://download.oracle.com/javase/tutorial/uiswing/examples/components/TreeIconDemo2Project/src/components/TreeIconDemo2.java
    */
    private static class SettingsTreeCellRenderer extends DefaultTreeCellRenderer {

        public SettingsTreeCellRenderer() {
            super();

            setBorderSelectionColor(null);
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

            Component origComp = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            if (!(value instanceof DefaultMutableTreeNode)) {
                return origComp;
            }

            Object userObj = ((DefaultMutableTreeNode) value).getUserObject();

            if (!(userObj instanceof TreeItem)) {
                return origComp;
            }
            
            TreeItem ti = (TreeItem)userObj;

            
            URL url = ti.getIconFilePath();
            
            if(url != null)
            {
                ImageIcon icon  = new ImageIcon();

                if (icon != null)
                {
                    setIcon(icon);
                }
            }
            
            return this;
        }
    }
    
    /**
     * This class takes care of when the left side settings tree has an item clicked.
     */
    private static class LeftSideListener implements TreeSelectionListener
    {
        @Override
        public void valueChanged(TreeSelectionEvent treeEvent)
        {
            TreePath path = treeEvent.getPath();
            Object last = path.getLastPathComponent();
            
            javax.swing.tree.DefaultMutableTreeNode node = (javax.swing.tree.DefaultMutableTreeNode)last;
            
            TreeItem goodStuff = (TreeItem)node.getUserObject();

            PanelAbstractClass panel = goodStuff.getAssociatedPanel();
            
            if (panel != null)
            {
                SettingsDialog.changeRightPanel(panel);
            }
            else
            {
                Logging.info("A null panel was assoiated with the TreeItem. TreeItem.getDiscription() = "+goodStuff.getDescription());
            }

        }
    }
    
    /**
     * Replaces the right side panel with a new panel. 
     * @param panel the panel the will be displayed on the right
     */
    public static void changeRightPanel(PanelAbstractClass panel)
    {
        if(panel == null)
        {
            Logging.info("Someone passed in a null PanelAbstractClass! Argggg!");
            panel =  (PanelAbstractClass) new JPanel();
        }
        
        right = panel;
        
        splitter.setRightComponent(panel);
        splitter.repaint();
        
        //Clears the "Saved" tip for the next panel.
        buttons.setIsSaved(false);
        
        panel.setVisible(true);
        omnies.revalidate();
        omnies.repaint();
    }
    
    /**
     * Gets rid of the settings dialog
     */
    public static void close()
    {
        omnies.setVisible(false);
        omnies.dispose();
        omnies = null;
        
        topImage = null;
        left = null;
        leftScroll = null;
        splitter = null;
        right = null;
        buttons = null;

    }

    
    //------------------ Getters and Setters -------------------
}
