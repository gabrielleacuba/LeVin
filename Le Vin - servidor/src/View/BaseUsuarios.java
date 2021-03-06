/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.sql.*;
import br.com.Conexao.FazendoConexao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

//importa os recursos da biblioteca re2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 * @author gabri
 */
public class BaseUsuarios extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form Usuarios
     */
    public BaseUsuarios() {
        initComponents();
        conexao = FazendoConexao.conector();
    }

    //metódo que busca os usuários cadastrados no sistema
    public void Busca_Usuarios() {
        String sqlBusca = "select idUser, usuario, login,fone from usuarios  where usuario like ? ";
        try {
            pst = conexao.prepareStatement(sqlBusca);
            //passando o conteudo da caixa de pesquisa para o interroga 
            //atencao ao % que é a continuacao da string busca sql
            pst.setString(1, buscarUsuario.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca para preeencher a tabela
            tabelaUsuario.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void RemovendoUsuario() {
        //pega o id do Usuario na tabela 
        int indiceTabela = tabelaUsuario.getSelectedRow();
        if (indiceTabela == -1) {
            //ou seja não há nenhum usuário selecionado 
            JOptionPane.showMessageDialog(null, "Por favor selecione um usuário para remover");
        } else {

            //pegando o indice do cliente selecionado
            String indiceUsuario = tabelaUsuario.getModel().getValueAt(indiceTabela, 0).toString();

            //caixa de dialogo para confirmar se realmente quer excluir o cliente
            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o usuario selecionado", "Atenção", JOptionPane.YES_NO_OPTION);
            //excluindo cliente do banco de dados
            if (confirma == JOptionPane.YES_OPTION) {
                String sql = "delete from usuarios where idUser = ?";
                try {
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, indiceUsuario);
                    int apagado = pst.executeUpdate();

                    if (apagado > 0) {
                        JOptionPane.showMessageDialog(null, "Usuario excluído com sucesso");

                        //Removendo a linha da tabela
                        DefaultTableModel dtmProdutos = (DefaultTableModel) tabelaUsuario.getModel();
                        dtmProdutos.removeRow(indiceTabela);
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }

    public void passarIndiceLinha(){
        //pega o id do Cliente na tabela 
        int indiceTabela = tabelaUsuario.getSelectedRow();
                
        if(indiceTabela == -1){
            //ou seja não há nenhum cliente selecionado 
            JOptionPane.showMessageDialog(null, "Por favor selecione um Usuario para editar");
        }else{
            //pegando o indice do cliente selecionado
            String indiceCliente  = tabelaUsuario.getModel().getValueAt(indiceTabela, 0).toString();
            
            AlterarUsuarios passaIndice = new AlterarUsuarios();
            Tela_Principal.fundoArea.add(passaIndice);
            passaIndice.setVisible(true);
            passaIndice.recebendoDados(indiceCliente);
        }
                
    } 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jToggleButton1 = new javax.swing.JToggleButton();
        jScrollBar1 = new javax.swing.JScrollBar();
        jPanel1 = new javax.swing.JPanel();
        buscarUsuario = new javax.swing.JTextField();
        btnBuscarUsuario = new javax.swing.JButton();
        btnNovoUsuario = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaUsuario = new javax.swing.JTable();
        btnExluirUsuario = new javax.swing.JButton();
        btnEditarUsuario = new javax.swing.JButton();

        jToolBar1.setRollover(true);

        jToggleButton1.setText("jToggleButton1");

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Le Vin -Tela  do Usuário (Acesso Exlusivo para adiministradores)");
        setPreferredSize(new java.awt.Dimension(800, 560));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buscarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarUsuarioActionPerformed(evt);
            }
        });
        buscarUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarUsuarioKeyReleased(evt);
            }
        });

        btnBuscarUsuario.setBackground(new java.awt.Color(0, 153, 153));
        btnBuscarUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBuscarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/procurarAdmin.png"))); // NOI18N
        btnBuscarUsuario.setText("Buscar");
        btnBuscarUsuario.setBorder(null);
        btnBuscarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuarioActionPerformed(evt);
            }
        });

        btnNovoUsuario.setBackground(new java.awt.Color(0, 153, 153));
        btnNovoUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnNovoUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnNovoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/MaisBranco.png"))); // NOI18N
        btnNovoUsuario.setText("Novo Usuário");
        btnNovoUsuario.setBorder(null);
        btnNovoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(buscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                .addComponent(btnNovoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNovoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        tabelaUsuario = new javax.swing.JTable(){
            public boolean isCellEditable (int rowIndex, int colIndex ){
                return false;
            }
        };
        tabelaUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdUsuario", "Nome Usuário", "Login", "Telefone"
            }
        ));
        tabelaUsuario.setRowHeight(25);
        tabelaUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaUsuario);

        btnExluirUsuario.setBackground(new java.awt.Color(0, 153, 153));
        btnExluirUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnExluirUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnExluirUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cross.png"))); // NOI18N
        btnExluirUsuario.setText("Excluir");
        btnExluirUsuario.setBorder(null);
        btnExluirUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExluirUsuarioActionPerformed(evt);
            }
        });

        btnEditarUsuario.setBackground(new java.awt.Color(0, 153, 153));
        btnEditarUsuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEditarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/alterar.png"))); // NOI18N
        btnEditarUsuario.setText("Editar");
        btnEditarUsuario.setBorder(null);
        btnEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnExluirUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnExluirUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(btnEditarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buscarUsuarioActionPerformed

    private void btnBuscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuarioActionPerformed
        Busca_Usuarios();
    }//GEN-LAST:event_btnBuscarUsuarioActionPerformed

    private void btnNovoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoUsuarioActionPerformed
        CadastroUsuarios telaCadastroUser = new CadastroUsuarios();
        Tela_Principal.fundoArea.add(telaCadastroUser);
        telaCadastroUser.setVisible(true);
    }//GEN-LAST:event_btnNovoUsuarioActionPerformed

    private void btnExluirUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExluirUsuarioActionPerformed
        RemovendoUsuario();
    }//GEN-LAST:event_btnExluirUsuarioActionPerformed

    private void btnEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUsuarioActionPerformed
       passarIndiceLinha();
    }//GEN-LAST:event_btnEditarUsuarioActionPerformed

    private void tabelaUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaUsuarioMouseClicked
       //pegar dados com dois clicks
        if (evt.getClickCount() == 2 && evt.getButton() == evt.BUTTON1) {
            passarIndiceLinha();
        }
    }//GEN-LAST:event_tabelaUsuarioMouseClicked

    private void buscarUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarUsuarioKeyReleased
        Busca_Usuarios();
    }//GEN-LAST:event_buscarUsuarioKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarUsuario;
    private javax.swing.JButton btnEditarUsuario;
    private javax.swing.JButton btnExluirUsuario;
    private javax.swing.JButton btnNovoUsuario;
    private javax.swing.JTextField buscarUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tabelaUsuario;
    // End of variables declaration//GEN-END:variables
}
