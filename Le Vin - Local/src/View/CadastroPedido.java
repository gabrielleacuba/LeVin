/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.*;
import br.com.Conexao.FazendoConexao;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabri
 */
public class CadastroPedido extends javax.swing.JInternalFrame {

    //essa variavel cliente terá o idCliente 
    String cliente;
    String produto;
    String vendedor;
    double somaTotal;
    public static PesquisaCliente1 filhoCliente;
    public static PesquisaProduto filhoProduto;
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form CadastroPedido
     */
    public CadastroPedido() {
        super();
        initComponents();
        setResizable(false);
        conexao = FazendoConexao.conector();
        listarVendedores();
    }

    public void RecebeClienteSelecionado(String clienteSelecionado) {
        cliente = clienteSelecionado;
        PreenchendoCliente();
    }

    public void RecebeProdutoSelecionado(String produtoSelecionado) {
        produto = produtoSelecionado;
        PreenchendoProduto();
    }

    //esse metodo ira preencher os campos do cliente
    private void PreenchendoCliente() {
        String sql = "select Nome from clientes where idCliente = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cliente);
            rs = pst.executeQuery();

            if (rs.next()) {
                idCliente.setText(cliente);
                nomeCliente.setText(rs.getString(1));

            } else {
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void PreenchendoProduto() {
        String sql = "select NomeProduto,preco from produtos where idVinho = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, produto);
            rs = pst.executeQuery();

            if (rs.next()) {
                idProduto.setText(produto);
                nomeProduto.setText(rs.getString(1));
                precoProduto.setText(rs.getString(2));

            } else {
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    /**
     * Este metódo não deixará adicionar um elemento repetido na tabela
     */
    private boolean VerificacaoIndiceRepetido(String idProduto){

        int qtdLinhas = tabelaVendas.getRowCount();
        int idpAux;
        int idProdutoAux;
        boolean retorno = false;
        String idP;
        
        for (int i = 0; i < qtdLinhas; i++) {
            idP = (String) tabelaVendas.getValueAt(i, 0);
            
            idpAux = Integer.parseInt(idP);
            idProdutoAux = Integer.parseInt(idProduto);

            if (idProdutoAux == idpAux) {
                retorno = true;
                return retorno;
            }else{
                retorno = false;
            }
        }
        
        return retorno;
    }
    //este metodo preenche a tabela, além disso calcula o valor total devido a quantidade
    // e o preço individual de cada produto
    private void PreenchendoTabela() {
        
        //pegando a quantidade e o produto 
        String qtd_Aux = qtdProduto.getText();
        String preco_Aux = precoProduto.getText();
        String indiceProduto = idProduto.getText();

        if (qtd_Aux.isEmpty()) {    //aceitando valores com virgula
            JOptionPane.showMessageDialog(null, "Insira uma quantidade de produto");
        } else {
            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "Br"));
            float Preco = 0;
            try {
                Preco = nf.parse(preco_Aux).floatValue();
            } catch (ParseException ex) {
                Logger.getLogger(CadastroPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
            //convertendo de string para inteiro 
            int qtdP = Integer.valueOf(qtd_Aux);

            //calculo do valor total através da quantidade e do preço individual
            float valorTotal = qtdP * Preco;

            //System.out.println("O valor total é: " + valor);
            try {
                boolean adicionar = VerificacaoIndiceRepetido(indiceProduto);
                if(adicionar == false){
                //adicionando os produtos na tabela 
                    DefaultTableModel dtmProdutos = (DefaultTableModel) tabelaVendas.getModel();
                    Object[] dados = {idProduto.getText(), nomeProduto.getText(), qtd_Aux, preco_Aux, valorTotal};
                    dtmProdutos.addRow(dados);
                }else{
                    JOptionPane.showMessageDialog(null, "Já possuí este produto no pedido !");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    //metodo que irá aplicar o desconto onde deve ser aplicado
    private void Desconto() {
        float desconto;
        if (descontoProduto.getText().isEmpty()) {
            desconto = 0;
        } else {
            desconto = Float.parseFloat(descontoProduto.getText());
            float valorTotal = Float.parseFloat(valor1.getText());
            float sub = valorTotal - desconto;
            valor1.setText(String.valueOf(sub));
        }
    }

    //soma todos os valores dos produtos
    private void SomandoValorProdutos() {

        float soma = 0;
        float valor;
        //pega a quantidade de linhas da tabela
        int cont = tabelaVendas.getRowCount();
        System.out.println(cont);
        for (int i = 0; i < cont; i++) {
            valor = (float) tabelaVendas.getValueAt(i, 4);
            soma = soma + valor;
        }
        valor1.setText(String.valueOf(soma));
        Desconto();

    }

    //preenche o Combobox vendedores
    private void listarVendedores() {
        String sql = "Select Nome from vendedores";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                vendedores.addItem(rs.getString("Nome"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * pegando o id do vendedor para salvar no bd
     */
    private void Id_Vendedor() {
        String vendedorAux;
        vendedorAux = vendedores.getSelectedItem().toString();

        String sql = "Select idVendedor from vendedores where Nome like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, vendedorAux);
            rs = pst.executeQuery();
            if (rs.next()) {
                vendedor = rs.getString(1);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            JOptionPane.showMessageDialog(null, e);
        }

    }

    /**
     * Esse metodo irá preencher os dados da tabela Produtos do banco de dados
     */
    private void Cadastrar_Pedido() {
        Id_Vendedor();
        String sql = "insert into pedido (idFaturamento,data_Venda,desconto,precoTotal,idCliente,vendedores_idVendedor,NomeCliente,NomeVendedor,dataEntrega)values(?,?,?,?,?,?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, numeroPedido.getText());
            pst.setString(2, dataVenda.getText());
            pst.setString(3, descontoProduto.getText());
            pst.setString(4, valor1.getText());
            pst.setString(5, idCliente.getText());
            pst.setString(6, vendedor);
            pst.setString(7, nomeCliente.getText());
            pst.setString(8, vendedores.getSelectedItem().toString());
            pst.setString(9, dataEntrega.getText());

            //validação dos campos obrigatórios
            if (numeroPedido.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
            }
            // a linha abaixo atualiza a tabela com os dados do formulário
            int aux = pst.executeUpdate();

            if (aux > 0) {
                Produtos_tabela();
                JOptionPane.showMessageDialog(null, "Pedido adicionado com sucesso");
                
                //Limpa os campos do formulário
                numeroPedido.setText(null);
                dataVenda.setText(null);
                descontoProduto.setText(null);
                valor1.setText(null);
                idCliente.setText(null);
                vendedores.setSelectedItem(null);
                nomeCliente.setText(null);
                nomeProduto.setText(null);
                idProduto.setText(null);
                qtdProduto.setText(null);
                precoProduto.setText(null);
                dataEntrega.setText(null);
                int qtdLinhas = tabelaVendas.getRowCount();
                DefaultTableModel dtmProdutos = (DefaultTableModel) tabelaVendas.getModel();
                for (int i = 0; i < qtdLinhas; i++) {
                    dtmProdutos.removeRow(0);
                }
            }
        //falta fazer o tratamento de erro
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println(e);
        }
    }

    /**
     * Essa classe irá salvar os produtos que estão na tabela na tabela pedidos
     */
    private void Produtos_tabela() {
        //pega a quantidade de linha da tabela
        int qtdLinhas = tabelaVendas.getRowCount();
        String codPro;
        String qtdProduto;
        String valorUnitario;
        float valorTotal;
        String nomeProduto;
        String NumeroNota = numeroPedido.getText();

        for (int i = 0; i < qtdLinhas; i++) {
            codPro = (String) tabelaVendas.getValueAt(i, 0);
            nomeProduto = (String) tabelaVendas.getValueAt(i, 1);
            qtdProduto = (String) tabelaVendas.getValueAt(i, 2);
            valorUnitario = (String) tabelaVendas.getValueAt(i, 3);
            valorTotal = (float) tabelaVendas.getValueAt(i, 4);

            //INSERINDO OS VALORES NO BANCO DE DADOS
            String sql = "insert into pedidoprodutos (idPedido, idVinho, quantidade, precoUnitario, nome,precoTotal ) values (?,?,?,?,?,?)";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, NumeroNota);
                pst.setString(2, codPro);
                pst.setString(3, qtdProduto);
                pst.setString(4, valorUnitario);
                pst.setString(5, nomeProduto);
                pst.setFloat(6, valorTotal);

                pst.executeUpdate();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    private void SubtraiValor(int indiceSelecionado){
        float valorTotal = Float.parseFloat(valor1.getText());
        float valor = (float) tabelaVendas.getValueAt(indiceSelecionado, 4);
        float valorSub;
        
        valorSub = valorTotal - valor;
        valor1.setText(String.valueOf(valorSub));
    }
    
    private void Remover_Linha() {
        //Pega o indice da linha da tabela
        int indiceTabela = tabelaVendas.getSelectedRow();
        
        if (indiceTabela == -1) {
            //ou seja não há nenhum usuário selecionado 
            JOptionPane.showMessageDialog(null, "Por favor selecione um produto para remover");
        } else {
            //apaga uma linha da tabela
            SubtraiValor(indiceTabela);
            DefaultTableModel dtmProdutos = (DefaultTableModel) tabelaVendas.getModel();
            dtmProdutos.removeRow(indiceTabela);
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

        jPanel1 = new javax.swing.JPanel();
        nomeCliente = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        idCliente = new javax.swing.JTextField();
        btnBuscarCliente = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        idProduto = new javax.swing.JTextField();
        nomeProduto = new javax.swing.JTextField();
        qtdProduto = new javax.swing.JTextField();
        precoProduto = new javax.swing.JTextField();
        btnBuscarProduto = new javax.swing.JButton();
        btnAdicionarProduto = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaVendas = new javax.swing.JTable();
        btnSalvarPedido = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        valor1 = new javax.swing.JTextField();
        descontoProduto = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        numeroPedido = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        vendedores = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        dataVenda = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        dataEntrega = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setTitle("Faturamento");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));
        jPanel1.setToolTipText("");

        nomeCliente.setEditable(false);

        jLabel1.setText("Nome : ");

        idCliente.setEditable(false);
        idCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idClienteActionPerformed(evt);
            }
        });

        btnBuscarCliente.setBackground(new java.awt.Color(102, 102, 102));
        btnBuscarCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBuscarCliente.setForeground(new java.awt.Color(0, 204, 255));
        btnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        btnBuscarCliente.setText("Buscar");
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });

        jLabel2.setText("IdCliente:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarCliente)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Produtos"));

        idProduto.setEditable(false);

        nomeProduto.setEditable(false);

        btnBuscarProduto.setBackground(new java.awt.Color(102, 102, 102));
        btnBuscarProduto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBuscarProduto.setForeground(new java.awt.Color(0, 204, 255));
        btnBuscarProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        btnBuscarProduto.setText("Buscar");
        btnBuscarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProdutoActionPerformed(evt);
            }
        });

        btnAdicionarProduto.setBackground(new java.awt.Color(102, 102, 102));
        btnAdicionarProduto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAdicionarProduto.setForeground(new java.awt.Color(0, 204, 255));
        btnAdicionarProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Simbolo-Mais.png"))); // NOI18N
        btnAdicionarProduto.setText("Adicionar");
        btnAdicionarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarProdutoActionPerformed(evt);
            }
        });

        jLabel3.setText("Cod. Pro.");

        jLabel4.setText("Nome:");

        jLabel5.setText("Preço:");

        jLabel6.setText("Quantidade:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(4, 4, 4)
                        .addComponent(idProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(btnBuscarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(qtdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(precoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdicionarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nomeProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                        .addComponent(btnBuscarProduto))
                    .addComponent(idProduto)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionarProduto)
                    .addComponent(precoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qtdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)))
        );

        tabelaVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Prod.", "Nome", "Qtd", "Preço Uni.", "Preço Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaVendas);
        if (tabelaVendas.getColumnModel().getColumnCount() > 0) {
            tabelaVendas.getColumnModel().getColumn(0).setMinWidth(80);
            tabelaVendas.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabelaVendas.getColumnModel().getColumn(0).setMaxWidth(80);
            tabelaVendas.getColumnModel().getColumn(1).setMinWidth(50);
            tabelaVendas.getColumnModel().getColumn(2).setMinWidth(80);
            tabelaVendas.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabelaVendas.getColumnModel().getColumn(2).setMaxWidth(80);
            tabelaVendas.getColumnModel().getColumn(3).setMinWidth(95);
            tabelaVendas.getColumnModel().getColumn(3).setPreferredWidth(95);
            tabelaVendas.getColumnModel().getColumn(3).setMaxWidth(95);
            tabelaVendas.getColumnModel().getColumn(4).setMinWidth(95);
            tabelaVendas.getColumnModel().getColumn(4).setPreferredWidth(95);
            tabelaVendas.getColumnModel().getColumn(4).setMaxWidth(95);
        }

        btnSalvarPedido.setBackground(new java.awt.Color(102, 102, 102));
        btnSalvarPedido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSalvarPedido.setForeground(new java.awt.Color(0, 204, 255));
        btnSalvarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/right.png"))); // NOI18N
        btnSalvarPedido.setText("Salvar");
        btnSalvarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarPedidoActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(102, 102, 102));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 204, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cross.png"))); // NOI18N
        jButton5.setText("Apagar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        valor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valor1ActionPerformed(evt);
            }
        });

        descontoProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                descontoProdutoFocusLost(evt);
            }
        });

        jLabel7.setText("Descontos:");

        jLabel8.setText("Valor Total:");

        numeroPedido.setBackground(new java.awt.Color(153, 255, 153));

        jLabel9.setText("*Número do Pedido:");

        jLabel10.setText("Vendedor:");

        vendedores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        jLabel11.setText("Data da Venda:");

        jLabel12.setText("*Dados obrigatórios");

        jLabel13.setText("Data Entrega:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(valor1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnSalvarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dataVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dataEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descontoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(149, 149, 149))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(numeroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(vendedores, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(descontoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5)
                    .addComponent(dataEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(numeroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vendedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addGap(0, 13, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalvarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        filhoCliente = new PesquisaCliente1(this, true);
        filhoCliente.setVisible(true);

//        PesquisaCliente pesquisaCliente = new PesquisaCliente();
//        Tela_Principal.fundoArea.add(pesquisaCliente);
//        pesquisaCliente.setVisible(true);
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void idClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idClienteActionPerformed

    private void btnAdicionarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarProdutoActionPerformed
        PreenchendoTabela();
        SomandoValorProdutos();
    }//GEN-LAST:event_btnAdicionarProdutoActionPerformed

    private void valor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valor1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valor1ActionPerformed

    private void btnBuscarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProdutoActionPerformed
        filhoProduto = new PesquisaProduto(this, true);
        filhoProduto.setVisible(true);
    }//GEN-LAST:event_btnBuscarProdutoActionPerformed

    private void descontoProdutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_descontoProdutoFocusLost
        SomandoValorProdutos();
    }//GEN-LAST:event_descontoProdutoFocusLost

    private void btnSalvarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarPedidoActionPerformed
        Cadastrar_Pedido();
    }//GEN-LAST:event_btnSalvarPedidoActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Remover_Linha();
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarProduto;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarProduto;
    private javax.swing.JButton btnSalvarPedido;
    private javax.swing.JTextField dataEntrega;
    private javax.swing.JTextField dataVenda;
    private javax.swing.JTextField descontoProduto;
    private javax.swing.JTextField idCliente;
    private javax.swing.JTextField idProduto;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nomeCliente;
    private javax.swing.JTextField nomeProduto;
    private javax.swing.JTextField numeroPedido;
    private javax.swing.JTextField precoProduto;
    private javax.swing.JTextField qtdProduto;
    private javax.swing.JTable tabelaVendas;
    private javax.swing.JTextField valor1;
    private javax.swing.JComboBox<String> vendedores;
    // End of variables declaration//GEN-END:variables
}
