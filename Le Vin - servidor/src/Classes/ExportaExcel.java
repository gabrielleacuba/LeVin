/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import View.ExportarClientes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabri
 */
public class ExportaExcel {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    List<Cliente> listaClienteExporta = new ArrayList<Cliente>();
    static String EnderecoArquivo;
    private static String fileName;
    
    public ExportaExcel(){
        conexao = FazendoConexao.conector();
    }
    
    public void RecebeEndereco(final String endereco) {
        EnderecoArquivo = endereco;
        main();
    }

    
    //private static final String fileName = "C:\\Users\\gabri\\Documents\\Teste.xlsx";

    public static void main(){
        fileName = EnderecoArquivo;
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheetCliente = workbook.createSheet("Alunos");
        
        
        List<Cliente> listaCliente = new ArrayList<Cliente>();
        ExportarClientes exportar = new ExportarClientes();
        //Cria um objeto do tipo classe e depois salva os dados do banco em uma lista
        // ao salvar retorna essa lista 
        
        ExportaExcel arruma = new ExportaExcel();
        listaCliente = arruma.ExportandoCliente();
        
        
//        listaCliente.add(new Cliente("Eduardo", "9876525", "22", "666666", "88888", "99999", "16666", "8888", "", "", "", "", "", "", "", "", ""));
        
        //Criando a primeira linha da tabela
        int rownum = 0;
        Row rowAux = sheetCliente.createRow(rownum++);
        int cellnumAux = 0;
        
        Cell cellIdTitulo = rowAux.createCell(cellnumAux++);
        cellIdTitulo.setCellValue("ID Cliente");
        
        Cell cellNomeTitulo = rowAux.createCell(cellnumAux++);
        cellNomeTitulo.setCellValue("Nome");
        
        Cell cellDataTitulo = rowAux.createCell(cellnumAux++);
        cellDataTitulo.setCellValue("DataNascimento");
        
        Cell cellTelefoneTitulo = rowAux.createCell(cellnumAux++);
        cellTelefoneTitulo.setCellValue("Telefone");
        
        Cell cellCelularTitulo = rowAux.createCell(cellnumAux++);
        cellCelularTitulo.setCellValue("Celular");
        
        Cell cellTelRecadoTitulo = rowAux.createCell(cellnumAux++);
        cellTelRecadoTitulo.setCellValue("Telefone Recado");
        
        Cell cellSexoTitulo = rowAux.createCell(cellnumAux++);
        cellSexoTitulo.setCellValue("Sexo");
        
        Cell cellEmailCell = rowAux.createCell(cellnumAux++);
        cellEmailCell.setCellValue("Email");
        
        Cell cellCepTitulo = rowAux.createCell(cellnumAux++);
        cellCepTitulo.setCellValue("Cep");
        
        Cell cellEnderecoTitulo = rowAux.createCell(cellnumAux++);
        cellEnderecoTitulo.setCellValue("Endereco");
        
        Cell cellBairroTitulo = rowAux.createCell(cellnumAux++);
        cellBairroTitulo.setCellValue("Bairro");
        
        Cell cellNumeroTitulo = rowAux.createCell(cellnumAux++);
        cellNumeroTitulo.setCellValue("Número");
        
        Cell cellCidadeTitulo = rowAux.createCell(cellnumAux++);
        cellCidadeTitulo.setCellValue("Cidade");
        
        Cell cellUfTitulo = rowAux.createCell(cellnumAux++);
        cellUfTitulo.setCellValue("Uf - Estado");
        
        Cell cellComplementoTitulo = rowAux.createCell(cellnumAux++);
        cellComplementoTitulo.setCellValue("Complemento");
        
        Cell cellRgTitulo = rowAux.createCell(cellnumAux++);
        cellRgTitulo.setCellValue("Rg - Inscrição Estadual");
        
        Cell cellCpfTitulo = rowAux.createCell(cellnumAux++);
        cellCpfTitulo.setCellValue("Cpf/Cnpj");
        
        for (Cliente aluno : listaCliente) {
            Row row = sheetCliente.createRow(rownum++);
            int cellnum = 0;
            
            Cell cellId = row.createCell(cellnum++);
            cellId.setCellValue(aluno.getId());
            
            Cell cellNome = row.createCell(cellnum++);
            cellNome.setCellValue(aluno.getNome());
            
            Cell cellDataNascimento = row.createCell(cellnum++);
            cellDataNascimento.setCellValue(aluno.getDataNascimento());
            
            Cell cellTelefone = row.createCell(cellnum++);
            cellTelefone.setCellValue(aluno.getTelefone());
            
            Cell cellCelular = row.createCell(cellnum++);
            cellCelular.setCellValue(aluno.getCelular());
            
            Cell cellTelRecado = row.createCell(cellnum++);
            cellTelefone.setCellValue(aluno.getTelefoneRecado());
            
            Cell cellSexo = row.createCell(cellnum++);
            cellSexo.setCellValue(aluno.getSexo());
            
            Cell cellEmail = row.createCell(cellnum++);
            cellEmail.setCellValue(aluno.getEmail());
            
            Cell cellCep = row.createCell(cellnum++);
            cellCep.setCellValue(aluno.getCep());
            
            Cell cellEndereco = row.createCell(cellnum++);
            cellEndereco.setCellValue(aluno.getEndereco());
            
            Cell cellBairro = row.createCell(cellnum++);
            cellBairro.setCellValue(aluno.getBairro());
            
            Cell cellNumero = row.createCell(cellnum++);
            cellNumero.setCellValue(aluno.getNumero());
            
            Cell cellCidade = row.createCell(cellnum++);
            cellBairro.setCellValue(aluno.getCidade());
            
            Cell cellUf = row.createCell(cellnum++);
            cellUf.setCellValue(aluno.getUf());
            
            Cell cellComplemento = row.createCell(cellnum++);
            cellComplemento.setCellValue(aluno.getComplemento());
            
            Cell cellRg = row.createCell(cellnum++);
            cellRg.setCellValue(aluno.getRg());
            
            Cell cellCpf = row.createCell(cellnum++);
            cellCpf.setCellValue(aluno.getCpf_Cnpj());
        }

        try {
            FileOutputStream out = new FileOutputStream(new File(ExportaExcel.fileName));
            workbook.write(out);
            out.close();
            System.out.println("Arquivo Excel criado com sucesso!");
            exportar.ExportouBanco("Exportou");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Arquivo não encontrado!");
            exportar.ExportouBanco("Nao Exportou");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro na edição do arquivo!");
        }

    }

    private List ExportandoCliente() {
      System.out.println("entrei no ExPORTANDO CLIENTE");
        String sql = "select idCliente,Nome,DataNascimento,telefone,celular,telefoneRecado,sexo,email,cep,endereco,bairro,numero,cidade,uf,complemento,rg,cpf_cnpj from clientes";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            //Cliente clienteExporta = new Cliente();
            
            while (rs.next()) {
                Cliente clienteExporta = new Cliente();
                //listaClienteExporta.add(clienteExporta);
                clienteExporta.setId(rs.getString(1));
                clienteExporta.setNome(rs.getString(2));
                clienteExporta.setDataNascimento(rs.getString(3));
                clienteExporta.setTelefone(rs.getString(4));
                clienteExporta.setCelular(rs.getString(5));
                clienteExporta.setTelefoneRecado(rs.getString(6));
                clienteExporta.setSexo(rs.getString(7));
                clienteExporta.setEmail(rs.getString(8));
                clienteExporta.setCep(rs.getString(9));
                clienteExporta.setEndereco(rs.getString(10));
                clienteExporta.setBairro(rs.getString(11));
                clienteExporta.setNumero(rs.getString(12));
                clienteExporta.setCidade(rs.getString(13));
                clienteExporta.setUf(rs.getString(14));
                clienteExporta.setComplemento(rs.getString(15));
                clienteExporta.setRg(rs.getString(16));
                clienteExporta.setCpf_Cnpj(rs.getString(17));
                listaClienteExporta.add(clienteExporta);
            }
            
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
      
        return listaClienteExporta;
    }

}
