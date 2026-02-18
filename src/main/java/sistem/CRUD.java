            
package sistem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Pedro
 */
public class CRUD {
    
    //Cadastra usuario
    public static void cadastrarUsuarios(Usuarios usuarios) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, cpf, senha, adm) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, usuarios.getNome());
        stmt.setString(2, usuarios.getEmail());
        stmt.setString(3, usuarios.getCpf());
        stmt.setString(4, usuarios.getSenha());
        stmt.setBoolean(5, usuarios.isAdm());

        stmt.executeUpdate();
    }
    }
    
    // delete no banco de dados
    
    public static void deletarUsuario(int id) throws SQLException {

    String sql = "DELETE FROM usuarios WHERE id = ? AND adm = false";

    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        stmt.executeUpdate();

    }
}
    
    public static boolean apagarGasto(int idFinancas, int usuarioId) throws SQLException {
    String sql = "DELETE FROM financas WHERE id = ? AND usuario_id = ?";

    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idFinancas);
        stmt.setInt(2, usuarioId);

        return stmt.executeUpdate() > 0;
    }
}
    
    public static int editarGasto(int idFinanca, int usuarioId, String nomeGasto, BigDecimal valor, LocalDate data) throws SQLException {

    String sql = "UPDATE financas SET nome_gasto = ?, valor = ?, data_gasto = ? " +
                 "WHERE id = ? AND usuario_id = ?";

    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nomeGasto);
        stmt.setBigDecimal(2, valor);
        stmt.setDate(3, java.sql.Date.valueOf(data));
        stmt.setInt(4, idFinanca);
        stmt.setInt(5, usuarioId);

        return stmt.executeUpdate();
    }
}


    
   public static void inserirGasto(int usuarioId, String nomeGasto, BigDecimal valor, LocalDate data) throws SQLException {

    String sql = "INSERT INTO financas (nome_gasto, valor, data_gasto, usuario_id) VALUES (?, ?, ?, ?)";

    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nomeGasto);
        stmt.setBigDecimal(2, valor);
        stmt.setDate(3, java.sql.Date.valueOf(data));
        stmt.setInt(4, usuarioId);

        stmt.executeUpdate();
    }
}

    
    //Buscar id no banco de dados
    
    public static Usuarios buscarPorId(int id) throws SQLException {

    String sql = "SELECT * FROM usuarios WHERE id = ?";

    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Usuarios u = new Usuarios();
            u.setId(rs.getInt("id"));
            u.setNome(rs.getString("nome"));
            u.setEmail(rs.getString("email"));
            u.setCpf(rs.getString("cpf"));
            u.setAdm(rs.getBoolean("adm"));
            return u;
        }
    }

    return null;
}


    
    
    
    
   //Validaçao de login usuario ou adm
    
public static Usuarios validarLogin(String email, String senha) throws SQLException {

     String sql = "SELECT id, nome, email, cpf, adm FROM usuarios WHERE email = ? AND senha = ?";

    try (Connection con = ConexaoDB.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, email);
        stmt.setString(2, senha);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Usuarios u = new Usuarios();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setCpf(rs.getString("cpf"));
                u.setAdm(rs.getBoolean("adm"));
                return u;
            }
        }
    }

    return null;
}

    //Listar usuarios na tabela
   public static List<Usuarios> listarUsuarios() throws SQLException {
   List<Usuarios> lista = new ArrayList<>();

    String sql = "SELECT id, nome, email, cpf, senha, adm FROM usuarios";

    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Usuarios u = new Usuarios();
            u.setId(rs.getInt("id"));
            u.setNome(rs.getString("nome"));
            u.setEmail(rs.getString("email"));
            u.setCpf(rs.getString("cpf"));
            u.setSenha(rs.getString("senha"));
            u.setAdm(rs.getBoolean("adm"));
            lista.add(u);
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao listar usuários: " + e.getMessage(), e);
    }

    return lista;
}


    //editar usuario
    public static void editarUsuario(Usuarios usuario) throws SQLException {

    String sql = "UPDATE usuarios SET nome = ?, email = ?, cpf = ?, senha = ?, adm = ? WHERE id = ?";

    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getCpf());
        stmt.setString(4, usuario.getSenha());
        stmt.setInt(5, usuario.isAdm() ? 1 : 0);
        stmt.setInt(6, usuario.getId());

        stmt.executeUpdate();
    }
  
}

    
    //Listar Finanças
    
    public static List<Object[]> listarFinancas() throws SQLException {

    List<Object[]> lista = new ArrayList<>();

    String sql = "SELECT f.nome_gasto, f.valor, f.data_gasto " +
             "FROM financas f WHERE f.usuario_id = ?";


    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {

            Object[] linha = {
                rs.getString("nome_gasto"),
                rs.getBigDecimal("nome_valor"),
                rs.getDate("data_gasto")
            };

            lista.add(linha);
        }
    }

    return lista;
}

    public static List<Object[]> listarFinancasPorUsuario(int usuarioId) throws SQLException {

    List<Object[]> lista = new ArrayList<>();

    String sql = "SELECT id, nome_gasto, valor, data_gasto FROM financas WHERE usuario_id = ?";

    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Object[] linha = {
                rs.getInt("id"),
                rs.getString("nome_gasto"),
                rs.getBigDecimal("valor"),
                rs.getDate("data_gasto")
            };

            lista.add(linha);
        }
    }

    return lista;
}

public static boolean usuarioExiste(String nome, String email) throws SQLException {
    String sql = "SELECT id FROM usuarios WHERE nome = ? OR email = ?";

    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, nome);
        stmt.setString(2, email);

        ResultSet rs = stmt.executeQuery();
        return rs.next(); // se encontrou, já existe
    }
}

    
}
