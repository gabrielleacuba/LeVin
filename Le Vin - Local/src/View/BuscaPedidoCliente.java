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
public class BuscaPedidoCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    AlterarPedido NumPedido;

    /**
     * Creates new form BuscaPedidoCliente
     */
    public BuscaPedidoCliente() {
        initComponents();
        conexao = FazendoConexao.conector();
    }

    public void Busca_Pedidos() {
        String sqlBusca = "select idFaturamento, data_Venda, precoTotal from pedido  where NomeCliente like ? ";
        try {
            pst = conexao.prepareStatement(sqlBusca);
            //passando o conteudo da caixa de pesquisa para o interroga 
            //atencao ao % que é a continuacao da string busca sql
            pst.setString(1, buscarNota.getText() + "%");
            rs = pst.executeQuery();
            //a linha abaixo usa a biblioteca para preeencher a tabela
            tabelaNota.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //esta classe pegará o indice da nota selecionado e passará para tela AlterarPedido
    public void setar_campos() {
        //pega o id da Nota na tabela 
        int indiceTabela = tabelaNota.getSelectedRow();

        if (indiceTabela == -1) {
            //ou seja não há nenhuma nota selecionada 
            JOptionPane.showMessageDialog(null, "Por favor selecione um Pedido para editar");
        } else {
            //pegando o indice do cliente selecionado
            String indiceNota = tabelaNota.getModel().getValueAt(indiceTabela, 0).toString();

            NumPedido = new AlterarPedido();
            Tela_Principal.fundoArea.add(NumPedido);
            NumPedido.setVisible(true);
            NumPedido.recebendoDados(indiceNota);
        }

    }

    /**
     * Este metodo remove o pedido selecionado da tabela
     */
    private void Removendo_Pedido() {
        boolean verifica = false;
        //pega a linha selecionada
        int indiceTabela = tabelaNota.getSelectedRow();

        if (indiceTabela == -1) {
            //ou seja não há nenhum pedido selecionado 
            JOptionPane.showMessageDialog(null, "Por favor selecione um pedido para remover.");
        } else {

            //pegando o indice do pedido selecionado
            String indicePedido = tabelaNota.getModel().getValueAt(indiceTabela, 0).toString();

            //caixa de dialogo para confirmar se realmente quer excluir o pedido
            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o pedido selecionado ?", "Atenção", JOptionPane.YES_NO_OPTION);

            //excluindo pedido do banco de dados
            if (confirma == JOptionPane.YES_OPTION) {
                verifica = ExcluiPedidoProdutos(indicePedido);
                if(verifica == true){
                    String sql = "delete from pedido where idFaturamento = ?";
                    try {
                        pst = conexao.prepareStatement(sql);
                        pst.setString(1, indicePedido);
                        int apagado = pst.executeUpdate();

                        if (apagado > 0) {
                            JOptionPane.showMessageDialog(null, "Pedido excluído com sucesso !");

                            //Removendo a linha da tabela
                            DefaultTableModel dtmProdutos = (DefaultTableModel) tabelaNota.getModel();
                            dtmProdutos.removeRow(indiceTabela);
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Não foi possível excluir o produto");
                }
            }
        }
    }

    private boolean ExcluiPedidoProdutos(String indiceProduto) {
        boolean verifica = false;
        String sql = "delete from pedidoprodutos where idPedido = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, indiceProduto);
            int apagado = pst.executeUpdate();

            if (apagado > 0) {
                verifica = true;
                return verifica;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            verifica = false;
            return verifica;
        }
        return verifica;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buscarNota = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaNota = new javax.swing.JTable();
        btnExcluirProduto = new javax.swing.JButton();
        btnEditarProduto = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Le Vin - Consultar Pedido");
        setToolTipText("");

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));

        buscarNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarNotaKeyReleased(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(102, 102, 102));
        btnBuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(0, 204, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search-magnifier.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(buscarNota, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarNota, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tabelaNota = new javax.swing.JTable(){
            public boolean isCellEditable (int rowIndex, int colIndex ){
                return false;
            }
        };
        tabelaNota.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabelaNota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idCliente", "Nome", "Email", "Perfil"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaNota.setRowHeight(23);
        tabelaNota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaNotaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaNota);

        btnExcluirProduto.setBackground(new java.awt.Color(102, 102, 102));
        btnExcluirProduto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnExcluirProduto.setForeground(new java.awt.Color(0, 204, 255));
        btnExcluirProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cross.png"))); // NOI18N
        btnExcluirProduto.setText("Excluir");
        btnExcluirProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirProdutoActionPerformed(evt);
            }
        });

        btnEditarProduto.setBackground(new java.awt.Color(102, 102, 102));
        btnEditarProduto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEditarProduto.setForeground(new java.awt.Color(0, 204, 255));
        btnEditarProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/alterar.png"))); // NOI18N
        btnEditarProduto.setText("Editar");
        btnEditarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProdutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnExcluirProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnEditarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExcluirProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        Busca_Pedidos();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnExcluirProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirProdutoActionPerformed
        Removendo_Pedido();
    }//GEN-LAST:event_btnExcluirProdutoActionPerformed

    private void btnEditarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProdutoActionPerformed
        setar_campos();
    }//GEN-LAST:event_btnEditarProdutoActionPerformed

    private void tabelaNotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaNotaMouseClicked
        //pegar dados com dois clicks
        if (evt.getClickCount() == 2 && evt.getButton() == evt.BUTTON1) {
            setar_campos();
        }
    }//GEN-LAST:event_tabelaNotaMouseClicked

    private void buscarNotaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscarNotaKeyReleased
        Busca_Pedidos();
    }//GEN-LAST:event_buscarNotaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditarProduto;
    private javax.swing.JButton btnExcluirProduto;
    private javax.swing.JTextField buscarNota;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaNota;
    // End of variables declaration//GEN-END:variables
}
