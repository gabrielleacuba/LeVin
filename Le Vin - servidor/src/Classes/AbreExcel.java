/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import View.ImportarExcel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabri Essa classe irá abrir e ler o arquivo do excel
 *
 */
public class AbreExcel {

    static String EnderecoArquivo;
    private static String fileName;

    public void RecebeEndereco(final String endereco) {
        EnderecoArquivo = endereco;
        main();
    }

    public static void main() {
        int cont = 0;
        int aux = 0;
        String banco;
        fileName = EnderecoArquivo;
        List<Cliente> listaCliente = new ArrayList<Cliente>();

        try {
            FileInputStream arquivo = new FileInputStream(new File(AbreExcel.fileName));

            //cria um workbook = planilha com todas as abas
            XSSFWorkbook workbook = new XSSFWorkbook(arquivo);

            //Recuperamos apenas a primeira planilha
            XSSFSheet sheetCliente = workbook.getSheetAt(0);

            //retorna linhas da planilha 0
            Iterator<Row> rowIterator = sheetCliente.iterator();
            Cliente cliente1 = new Cliente(aux);

            while (rowIterator.hasNext()) {
                cont++;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Cliente cliente = new Cliente();
                listaCliente.add(cliente);

                while (cellIterator.hasNext()) {
                    System.out.println("Contador" + cont);
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            System.out.println("0");
                            cliente.setNome(cell.getStringCellValue());
                            break;

                        case 1:
                            System.out.println("1");
                            cliente.setDataNascimento(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case 2:
                            System.out.println("2");
                            cell.getCellType();
                            break;
                        case 3:
                            System.out.println("3");
                            cliente.setTelefone(cell.getStringCellValue());
                            break;
                        case 4:
                            System.out.println("4");
                            cell.getCellType();
                            break;
                        case 5:
                            System.out.println("5");
                            cliente.setCelular(cell.getStringCellValue());
                            break;
                        case 6:
                            System.out.println("6");
                            cell.getCellType();
                            break;
                        case 7:
                            System.out.println("7");
                            cliente.setTelefoneRecado(cell.getStringCellValue());
                            break;
                        case 8:
                            System.out.println("8");
                            cliente.setSexo(cell.getStringCellValue());
                            break;
                        case 9:
                            System.out.println("9");
                            cliente.setEmail(cell.getStringCellValue());
                            break;
                        case 10:
                            System.out.println("10");
                            cliente.setCep(cell.getStringCellValue());
                            break;
                        case 11:
                            System.out.println("11");
                            cliente.setEndereco(cell.getStringCellValue());
                            break;
                        case 12:
                            System.out.println("12");
                            System.out.println(cell.getStringCellValue());
                            cliente.setBairro(cell.getStringCellValue());
                            System.out.println(cliente.getBairro());
                            break;
                        case 13:
                            System.out.println("13");
                            cliente.setNumero(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case 14:
                            System.out.println("14");
                            cliente.setCidade(cell.getStringCellValue());
                            break;
                        case 15:
                            System.out.println("15");
                            cliente.setUf(cell.getStringCellValue());
                            break;
                        case 16:
                            System.out.println("16");
                            cell.getCellType();
                            break;
                        case 17:
                            System.out.println("17");
                            cliente.setComplemento(cell.getStringCellValue());
                            break;
                        case 18:
                            System.out.println("18");
                            cell.getCellType();
                            break;
                        case 19:
                            System.out.println("19");
                            cliente.setRg(cell.getStringCellValue());
                            break;
                        case 20:
                            System.out.println("20");
                            cliente.setAuxCpf(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case 21:
                            System.out.println("21");
                            cliente.setCpf_Cnpj(cell.getStringCellValue());
                            break;
                    }
                }
            }
            System.out.println("Contadoo" + cont);
            cliente1.salvandoNoBanco(listaCliente);

            arquivo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Arquivo Excel não encontrado");
        } catch (IOException ex) {
            Logger.getLogger(AbreExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImportarExcel importa = new ImportarExcel();

        if (listaCliente.size() == 0) {
            System.out.println("Nenhum cliente encontrado!");
            banco = "Nao salvou";
        } else {
            banco = "Salvou";
            for (Cliente cliente : listaCliente) {
                System.out.println("Cliente: " + cliente.getNome() + " Telefone: "
                        + cliente.getTelefone() + "Bairro" + cliente.getBairro());
            }
        }
        importa.SalvouBanco(banco);
    }

}
