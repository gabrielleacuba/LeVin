/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

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
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabri
 */
public class Cliente {

    private String Id;
    private String nome;
    private String dataNascimento;
    private String telefone;
    private String celular;
    private String telefoneRecado;
    private String sexo;
    private String email;
    private String cep;
    private String endereco;
    private String bairro;
    private String numero;
    private String cidade;
    private String uf;
    private String complemento;
    private String rg;
    private String cpf_cnpj;
    private String aux;
    private String aux1;
    private String aux2;
    private String aux3;
    private String aux4;
    public int cont;

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public Cliente(int aux) {
        conexao = FazendoConexao.conector();
        this.cont = 0;
    }
    public Cliente(){
        
    }

    public Cliente(String Id, String nome, String dataNascimento, String telefone, String celular, String telefoneRecado, String sexo, String email, String cep, String endereco, String bairro, String numero, String cidade, String uf, String complemento, String rg, String cpf_cnpj, String aux, String aux1, String aux2, String aux3, String aux4) {
        this.Id = Id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.celular = celular;
        this.telefoneRecado = telefoneRecado;
        this.sexo = sexo;
        this.email = email;
        this.cep = cep;
        this.endereco = endereco;
        this.bairro = bairro;
        this.numero = numero;
        this.cidade = cidade;
        this.uf = uf;
        this.complemento = complemento;
        this.rg = rg;
        this.cpf_cnpj = cpf_cnpj;
        this.aux = aux;
        this.aux1 = aux1;
        this.aux2 = aux2;
        this.aux3 = aux3;
        this.aux4 = aux4;
        conexao = FazendoConexao.conector();
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefoneRecado() {
        return telefoneRecado;
    }

    public void setTelefoneRecado(String telefoneRecado) {
        this.telefoneRecado = telefoneRecado;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String Bairro) {
        this.bairro = Bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf_Cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_Cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public void setAuxCpf(String aux) {
        this.aux = aux;
    }

    public void setAuxRg(String aux) {
        this.aux1 = aux1;
    }

    public void setAuxTelefonePrincipal(String aux) {
        this.aux2 = aux2;
    }

    public void setAuxCelular(String aux) {
        this.aux3 = aux3;
    }

    public void setAuxTelefoneRecado(String aux) {
        this.aux4 = aux4;
    }
    
    public void AbreConexao(){
        
    }
    public void salvandoNoBanco(List<Cliente> listaCliente) {
        //System.out.println("Tamanho da lista é : " + listaCliente.size());
        for (Cliente cliente : listaCliente) {
            
            String sql = "insert into clientes (Nome,DataNascimento,telefone,celular,telefoneRecado,sexo,email,cep,endereco,bairro,numero,cidade,uf,complemento,rg,cpf_cnpj) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            try {
                pst = conexao.prepareStatement(sql);
                
                System.out.println("Salvando banco : " + cont);
                    pst.setString(1, cliente.getNome());
                    pst.setString(2, cliente.getDataNascimento());
                    pst.setString(3, cliente.getTelefone());
                    pst.setString(4, cliente.getCelular());
                    pst.setString(5, cliente.getTelefoneRecado());
                    pst.setString(6, cliente.getSexo());
                    pst.setString(7, cliente.getEmail());
                    pst.setString(8, cliente.getCep());
                    pst.setString(9, cliente.getEndereco());
                    pst.setString(10, cliente.getBairro());
                    pst.setString(11, cliente.getNumero());
                    pst.setString(12, cliente.getCidade());
                    pst.setString(13, cliente.getUf());
                    pst.setString(14, cliente.getComplemento());
                    pst.setString(15, cliente.getRg());
                    pst.setString(16, cliente.getCpf_Cnpj());

                    pst.executeUpdate();
                    cont++;
                    System.out.println("Salvando banco : " + cont);
                
                //return "salvou";
            } catch (Exception e) {
                System.out.println("Nao gravou no banco");
                JOptionPane.showMessageDialog(null, e);
//            }finally {			
//		try {
//			if(pst != null){
//				pst.close();
//			}
//			conexao.close();
//		} catch (SQLException e) {
//			// LOGGING
//			e.printStackTrace();
//		}
//            
            }
	}
    }
    // return "Nao Salvou";
}





