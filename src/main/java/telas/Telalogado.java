
package telas;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistem.CRUD;
import sistem.Usuarios;

/**
 *
 * @author Pedro
 */
public class Telalogado extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Telalogado.class.getName());

    private LocalDate converterParaLocalDate(Object valorData) {

    if (valorData == null) {
        throw new IllegalArgumentException("Data vazia na tabela.");
    }

    if (valorData instanceof LocalDate) {
        return (LocalDate) valorData;
    }

    if (valorData instanceof java.sql.Date) {
        return ((java.sql.Date) valorData).toLocalDate();
    }

    if (valorData instanceof java.util.Date) {
        java.util.Date d = (java.util.Date) valorData;
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // Se for String: tenta os dois formatos comuns
    String s = valorData.toString().trim();
    try {
        return LocalDate.parse(s); // yyyy-MM-dd
    } catch (Exception ignored) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-M-d"); // aceita 2026-2-02
        return LocalDate.parse(s, fmt);
    }
}
    
    private void limparcampos() {
    campo_gasto.setText("");
    campo_valor.setText("");
    campo_dia.setSelectedIndex(0);
    campo_mes.setSelectedIndex(0);
    campo_ano.setSelectedIndex(0);
}

    
    
    
private Usuarios usuarioLogado;

    public Telalogado(Usuarios usuario) {
    initComponents();
    this.usuarioLogado = usuario;
    lblusuario.setText("TENHA O CONTROLE DE SUAS FINANÇAS  " + usuario.getNome());
    mostrarFinancas(); // ✅ agora correto
}
    
    private int trataMesNumero(String mesNome) {
        switch (mesNome) {
            case "Janeiro": return 1;
            case "Fevereiro": return 2;
            case "Março": return 3;
            case "Abril": return 4;
            case "Maio": return 5;
            case "Junho": return 6;
            case "Julho": return 7;
            case "Agosto": return 8;
            case "Setembro": return 9;
            case "Outubro": return 10;
            case "Novembro": return 11;
            case "Dezembro": return 12;
            default:
                throw new IllegalArgumentException("Mês inválido: " + mesNome);
        }
}




    
    private void mostrarFinancas() {

    try {

        List<Object[]> lista = CRUD.listarFinancasPorUsuario(usuarioLogado.getId());

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Gasto");
        modelo.addColumn("Valor");
        modelo.addColumn("Data");

        for (Object[] linha : lista) {
            modelo.addRow(linha);
        }

        tabelafinancas.setModel(modelo);

        tabelafinancas.getColumnModel().getColumn(0).setMinWidth(0);
        tabelafinancas.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelafinancas.getColumnModel().getColumn(0).setWidth(0);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

   



    private void tabelafinancasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelafinancasMouseClicked
        
        int linha = tabelafinancas.getSelectedRow();
    if (linha == -1) return;

    // Agora que tem ID escondido:
    // 0 = ID
    // 1 = Gasto
    // 2 = Valor
    // 3 = Data

    String gasto = String.valueOf(tabelafinancas.getValueAt(linha, 1));
    String valor = String.valueOf(tabelafinancas.getValueAt(linha, 2));
    Object valorData = tabelafinancas.getValueAt(linha, 3); // 🔥 DATA CORRETA

    campo_gasto.setText(gasto);
    campo_valor.setText(valor);

    LocalDate dataSelecionada = (valorData instanceof java.sql.Date)
            ? ((java.sql.Date) valorData).toLocalDate()
            : LocalDate.parse(valorData.toString());

    campo_dia.setSelectedItem(String.format("%02d", dataSelecionada.getDayOfMonth()));
    campo_mes.setSelectedIndex(dataSelecionada.getMonthValue() - 1);
    campo_ano.setSelectedItem(String.valueOf(dataSelecionada.getYear()));
    }//GEN-LAST:event_tabelafinancasMouseClicked

    private void btn_visualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_visualizarActionPerformed
       mostrarFinancas();
       limparcampos();
    }//GEN-LAST:event_btn_visualizarActionPerformed

    private void btn_adicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adicionarActionPerformed
        try {
        String nome = campo_gasto.getText().trim();
        BigDecimal valor = new BigDecimal(campo_valor.getText().trim().replace(",", "."));

        int ano = Integer.parseInt(campo_ano.getSelectedItem().toString());
        int mes = campo_mes.getSelectedIndex() + 1; // Janeiro=0 -> 1
        int dia = Integer.parseInt(campo_dia.getSelectedItem().toString());

        LocalDate data = LocalDate.of(ano, mes, dia);

        CRUD.inserirGasto(usuarioLogado.getId(), nome, valor, data);

        JOptionPane.showMessageDialog(this, "Gasto inserido com sucesso!");
        mostrarFinancas();
        limparcampos();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
    }
    }//GEN-LAST:event_btn_adicionarActionPerformed

    private void btn_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarActionPerformed
         int linha = tabelafinancas.getSelectedRow();
    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um gasto.");
        return;
    }

    int idFinanca = Integer.parseInt(
            tabelafinancas.getValueAt(linha, 0).toString()
    );

    String nome = campo_gasto.getText().trim();
    BigDecimal valor = new BigDecimal(
            campo_valor.getText().trim().replace(",", ".")
    );

    int ano = Integer.parseInt(campo_ano.getSelectedItem().toString());
    int dia = Integer.parseInt(campo_dia.getSelectedItem().toString());

    String mesTexto = campo_mes.getSelectedItem().toString();
    int mes = trataMesNumero(mesTexto);

    LocalDate data = LocalDate.of(ano, mes, dia);

    try {
        CRUD.editarGasto(idFinanca, usuarioLogado.getId(), nome, valor, data);

        JOptionPane.showMessageDialog(this, "Gasto atualizado com sucesso!");
        mostrarFinancas();
        limparcampos();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
    }
    }//GEN-LAST:event_btn_editarActionPerformed

    private void btn_apagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_apagarActionPerformed
        int linha = tabelafinancas.getSelectedRow();

    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um gasto na tabela.");
        return;
    }

    int idGasto = (int) tabelafinancas.getValueAt(linha, 0); // coluna ID escondida

    int opcao = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja apagar este gasto?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
    );

    if (opcao != JOptionPane.YES_OPTION) return;

    try {
        boolean apagou = CRUD.apagarGasto(idGasto, usuarioLogado.getId());

        if (apagou) {
            JOptionPane.showMessageDialog(this, "Gasto apagado com sucesso!");
            mostrarFinancas();  // recarrega a tabela
            limparcampos();     // se você tiver
        } else {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível apagar (gasto não encontrado ou não pertence a você).");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Erro ao apagar gasto:\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btn_apagarActionPerformed

    private void tabelafinancasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelafinancasMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelafinancasMouseEntered

    private void btn_somarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_somarActionPerformed
        try {

        BigDecimal total = CRUD.somarGastosPotUsuario(usuarioLogado.getId());

        DefaultTableModel modelo =
                (DefaultTableModel) tabelafinancas.getModel();

         modelo.addRow(new Object[]{
            null,               // ID vazio
            "TOTAL GASTOS",     // descrição
            total,              // valor total
            null                // data vazia
        });

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(this,
                "Erro ao somar gastos: " + e.getMessage());

    }
        
    }

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_adicionar;
    private javax.swing.JButton btn_apagar;
    private javax.swing.JButton btn_editar;
    private javax.swing.JButton btn_somar;
    private javax.swing.JButton btn_visualizar;
    private javax.swing.JComboBox<String> campo_ano;
    private javax.swing.JComboBox<String> campo_dia;
    private javax.swing.JTextField campo_gasto;
    private javax.swing.JComboBox<String> campo_mes;
    private javax.swing.JTextField campo_valor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblusuario;
    private javax.swing.JTable tabelafinancas;
    // End of variables declaration//GEN-END:variables
}
