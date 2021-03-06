/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gradely.client.gui.wizards;

import javax.swing.ImageIcon;
import org.gradely.client.gui.GuiUtilities;
import org.gradely.client.gui.PanelAbstractClass;
import org.gradely.client.gui.errors.ErrorPrompt;
import org.gradely.client.logging.Logging;

/**
 * This class displays the root wizard frame. PanelAbstractClass provide all the functionality.
 * @author Matt
 */
public class WizardDialog {


    private javax.swing.JFrame omnies;
    private javax.swing.JLabel left;
    private PanelAbstractClass right;
    private javax.swing.JPanel buttons;
    private WizardProgressionInterface gandolf;

    //================= Constructors ================================
    
    public WizardDialog(WizardProgressionInterface gandolf){
        this.gandolf = gandolf;
    }
    
    //================= Methods ================================
    
    public void init(){

        //Set Look and Feel
        GuiUtilities.setLookAndFeel();
        
        omnies = new javax.swing.JFrame();
        left = new javax.swing.JLabel();
        right = gandolf.getNextPanel();
        buttons = new javax.swing.JPanel();

        omnies.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        //Set icon
        ImageIcon icon = new ImageIcon(org.gradely.client.config.Configuration.class.getResource("logo.png"));
        omnies.setIconImage(icon.getImage());

        javax.swing.GroupLayout rightLayout = new javax.swing.GroupLayout(right);
        right.setLayout(rightLayout);
        rightLayout.setHorizontalGroup(
            rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        rightLayout.setVerticalGroup(
            rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout buttonsLayout = new javax.swing.GroupLayout(buttons);
        buttons.setLayout(buttonsLayout);
        buttonsLayout.setHorizontalGroup(
            buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 451, Short.MAX_VALUE)
        );
        buttonsLayout.setVerticalGroup(
            buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(omnies.getContentPane());
        omnies.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(left, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(right, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(left, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(right, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        omnies.pack();
        
        omnies.setVisible(true);
        left.setVisible(true);
        right.setVisible(true);
        buttons.setVisible(true);
    }
    
    /**
     * Gets rid of itself.
     */
    public void dispose()
    {
        if  (omnies != null)
        {
            omnies.setVisible(false);
            omnies.dispose();
        }
        
        left = null;
        right = null;
        buttons = null;
    }
    
    //=================== Sub-Classes ====================================================
    
    /**
     * This class contains the "Next" and "Back" buttons. It is a subclass so that it can access its container - WizardDialog. 
     */
    public class WizardButtons extends javax.swing.JPanel {

        /**
         * Creates new form WizardButtons
         */
        public WizardButtons(WizardDialog wd) {

            initComponents();
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            jButton1 = new javax.swing.JButton();
            jButton2 = new javax.swing.JButton();

            jButton1.setText("Next");
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });

            jButton2.setText("Back");
            jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(325, Short.MAX_VALUE)
                    .addComponent(jButton2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton1)
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(16, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2))
                    .addContainerGap())
            );
        }// </editor-fold>                        

        /**
         * This fires when ever the back button is clicked.
         * @param evt 
         */
        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
            
            //Load the previous form
            try
            {
                WizardDialog.this.right = WizardDialog.this.gandolf.getNextPanel();
            }
            catch (IndexOutOfBoundsException ex)
            {
                Logging.warning("Turns out there is not a previous panel in the wizard. I wonder why that happened.", ex);
                throw ex;
            }
            
            if (WizardDialog.this.gandolf.isFinalPanel())
            {
                WizardDialog.this.buttons = new FinalButton();
                Logging.warning("Apparently this is a final panel, but we just moved backwards.!?");
            }
            
        }                                        

        /**
         * Fires whenever the next button is clicked
         * @param evt 
         */
        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
            // TODO add your handling code here:
            //Save the form
            try
            {
                WizardDialog.this.right.saveForms();
            }
            catch(Exception e)
            {
                Logging.warning("The JPanel cannot be saved.", e);
                ErrorPrompt.init("Your settings could not be saved. (Message: "+e.getMessage()+")");
            }
            
            //Load the next form
            try
            {
                WizardDialog.this.right = WizardDialog.this.gandolf.getNextPanel();
            }
            catch (IndexOutOfBoundsException ex)
            {
                Logging.warning("Turns out ther eis not another panel in the wizard. I wonder why that happened.", ex);
                throw ex;
            }
            
            if (WizardDialog.this.gandolf.isFinalPanel())
            {
                WizardDialog.this.buttons = new FinalButton();
            }
            
            
        }                                        

        // Variables declaration - do not modify                     
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        // End of variables declaration                   
    }
    
    /**
     * Like the WizardButtons subclass this subclass instead contains the "Finish" button.
     */
    public class FinalButton extends javax.swing.JPanel {

        /**
         * Creates new form FinalButton
         */
        public FinalButton() {
            initComponents();
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">
        private void initComponents() {

            jButton1 = new javax.swing.JButton();

            jButton1.setText("Finish");
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(492, Short.MAX_VALUE)
                    .addComponent(jButton1)
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jButton1)
                    .addContainerGap(16, Short.MAX_VALUE))
            );
        }// </editor-fold>

        /**
         * When the user hits finish.
         * @param evt 
         */
        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
            // Save the page
            
            try
            {
                WizardDialog.this.right.saveForms();
            }
            catch(Exception e)
            {
                Logging.warning("The JPanel cannot be saved.", e);
                ErrorPrompt.init("Your settings could not be saved. (Message: "+e.getMessage()+")");
            }
            
            
            // Get rid of it
            WizardDialog.this.dispose();
            

        }

        // Variables declaration - do not modify
        private javax.swing.JButton jButton1;
        // End of variables declaration
    }
    
    
}
