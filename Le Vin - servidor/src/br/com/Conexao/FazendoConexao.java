/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Conexao;

import java.sql.*;
import java.sql.DriverManager;

/**
 *
 * @author gabri
 */
public class FazendoConexao {
    //método responsavel por estabelecer a conexão com o banco
    public static Connection conector(){
        java.sql.Connection conexao = null;
        //a linha abaixo chama o driver
        String driver = "com.mysql.cj.jdbc.Driver";
        // Armazenando informacoes referentes ao banco
        String url = "jdbc:mysql://localhost:3306/mydb?useTimezone=true&serverTimezone=UTC";
        String user = "root";
        String password = "futebolgol";
        //Estabelecendo a conexao com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            //para sair todos os erros
             e.printStackTrace();
            return null;
        }
    }
}
