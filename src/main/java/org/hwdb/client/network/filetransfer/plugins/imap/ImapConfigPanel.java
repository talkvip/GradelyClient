/*
 * Syncany, www.syncany.org
 * Copyright (C) 2011 Philipp C. Heckel <philipp.heckel@gmail.com> 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * ImapConnectionPanel.java
 *
 * Created on Mar 26, 2011, 3:08:24 PM
 */

package org.hwdb.client.network.filetransfer.plugins.imap;

import java.util.ResourceBundle;

import org.syncany.config.Config;
import org.hwdb.client.network.filetransfer.plugins.ConfigPanel;

/**
 *
 * @author Philipp C. Heckel <philipp.heckel@gmail.com>
 */
public class ImapConfigPanel extends ConfigPanel {

	private ResourceBundle resourceBundle; 
	
    /** Creates new form ImapConnectionPanel */
    public ImapConfigPanel(ImapConnection connection) {
        super(connection);
        resourceBundle = Config.getInstance().getResourceBundle(); 
        initComponents();
    }

    @Override
    public void load() {
        txtHost.setText(getConnection().getHost());
        spnPort.setValue(new Integer(getConnection().getPort()));
        txtUsername.setText(getConnection().getUsername());
        txtPassword.setText(getConnection().getPassword());
        txtFolder.setText(getConnection().getFolder());  
        
        // TODO do this differently
        if (getConnection().getSecurity() != null) {
            switch (getConnection().getSecurity()) {
                case NONE: cmbSecurity.setSelectedIndex(0); break;
                case STARTTLS: cmbSecurity.setSelectedIndex(1); break;
                case SSL: cmbSecurity.setSelectedIndex(2); break;
            }
        }

        else {
            cmbSecurity.setSelectedIndex(0);
        }
    }

    @Override
    public void save() {
        getConnection().setHost(txtHost.getText());
        getConnection().setPort((Integer) spnPort.getValue());
        getConnection().setUsername(txtUsername.getText());
        getConnection().setPassword(new String(txtPassword.getPassword()));
        getConnection().setFolder(txtFolder.getText());

        // TODO do this differently
        switch (cmbSecurity.getSelectedIndex()) {
            case 1: getConnection().setSecurity(ImapConnection.Security.STARTTLS); break;
            case 2: getConnection().setSecurity(ImapConnection.Security.SSL); break;
            default: getConnection().setSecurity(ImapConnection.Security.NONE); break;
        }
    }

    @Override
    public ImapConnection getConnection() {
        return (ImapConnection) super.getConnection();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jLabel1 = new javax.swing.JLabel();
                txtHost = new javax.swing.JTextField();
                jLabel2 = new javax.swing.JLabel();
                txtUsername = new javax.swing.JTextField();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                spnPort = new javax.swing.JSpinner();
                txtPassword = new javax.swing.JPasswordField();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                cmbSecurity = new javax.swing.JComboBox();
                jLabel7 = new javax.swing.JLabel();
                txtFolder = new javax.swing.JTextField();

                jLabel1.setText(resourceBundle.getString("imap_server_name"));
                jLabel1.setName("jLabel1"); // NOI18N

                txtHost.setName("txtHost"); // NOI18N

                jLabel2.setText(resourceBundle.getString("imap_username"));
                jLabel2.setName("jLabel2"); // NOI18N

                txtUsername.setName("txtUsername"); // NOI18N

                jLabel3.setText(resourceBundle.getString("imap_password"));
                jLabel3.setName("jLabel3"); // NOI18N

                jLabel4.setText(resourceBundle.getString("imap_port"));
                jLabel4.setName("jLabel4"); // NOI18N

                spnPort.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(143), Integer.valueOf(1), null, Integer.valueOf(1)));
                spnPort.setName("spnPort"); // NOI18N

                txtPassword.setName("txtPassword"); // NOI18N

                jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getStyle() | java.awt.Font.BOLD));
                jLabel5.setText(resourceBundle.getString("imap_security_settings"));
                jLabel5.setName("jLabel5"); // NOI18N

                jLabel6.setText(resourceBundle.getString("imap_connection_security"));
                jLabel6.setName("jLabel6"); // NOI18N

                cmbSecurity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "None", "STARTTLS", "SSL/TLS" }));
                cmbSecurity.setName("cmbSecurity"); // NOI18N

                jLabel7.setText(resourceBundle.getString("imap_folder"));
                jLabel7.setName("jLabel7"); // NOI18N

                txtFolder.setName("txtFolder"); // NOI18N

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(56, 56, 56)
                                                .addComponent(txtHost, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel4)
                                                .addGap(14, 14, 14)
                                                .addComponent(spnPort, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(67, 67, 67)
                                                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(76, 76, 76)
                                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(64, 64, 64)
                                                .addComponent(txtFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel5)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cmbSecurity, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(txtHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel1))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(spnPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel4)))
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7))
                                .addGap(15, 15, 15)
                                .addComponent(jLabel5)
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbSecurity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        }// </editor-fold>//GEN-END:initComponents


        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JComboBox cmbSecurity;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JSpinner spnPort;
        private javax.swing.JTextField txtFolder;
        private javax.swing.JTextField txtHost;
        private javax.swing.JPasswordField txtPassword;
        private javax.swing.JTextField txtUsername;
        // End of variables declaration//GEN-END:variables

}