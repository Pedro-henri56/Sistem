
package sistem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Pedro
 */
public class CRUD {
    
    //Cadastra usuario
    public static void cadastrarUsuarios(Usuarios usuarios) throws SQLException {
       String sql = "INSERT INTO usuarios (nome, email, senha, adm) VALUES (?, ?, ?, ?)";
       
       
        try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            
            stmt.setString(1, usuarios.getNome());
            stmt.setString(2, usuarios.getEmail());
            stmt.setString(3, usuarios.getSenha());
            stmt.setInt(4, usuarios.isAdm() ? 1 : 0);
            
            stmt.executeUpdate();  
            
        } catch (SQLException e) {
        throw new RuntimeException("Erro ao salvar usuario: " + e.getMessage(), e);
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
            u.setAdm(rs.getBoolean("adm"));
            return u;
        }
    }

    return null;
}


    
    
    
    
   //Validaçao de login usuario ou adm
    
public static Usuarios validarLogin(String email, String senha) throws SQLException {

    String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

Connection con = ConexaoDB.getConnection();
PreparedStatement stmt = con.prepareStatement(sql);

stmt.setString(1, email);
stmt.setString(2, senha);

ResultSet rs = stmt.executeQuery();

if (rs.next()) {
    Usuarios u = new Usuarios();
    u.setId(rs.getInt("id"));
    u.setNome(rs.getString("nome"));
    u.setAdm(rs.getBoolean("adm"));
    return u;
}
        return null;

}

    //Listar usuarios na tabela
    public static List<Usuarios> listarUsuarios() throws SQLException {

    List<Usuarios> lista = new ArrayList<>();

    String sql = "SELECT id, nome, email, senha, adm  FROM usuarios";

    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Usuarios u = new Usuarios();
            u.setId(rs.getInt("id"));
            u.setNome(rs.getString("nome"));
            u.setEmail(rs.getString("email"));
            u.setSenha(rs.getString("senha"));
            u.setAdm(rs.getBoolean("adm"));

            lista.add(u);
        }
    }

    return lista;
}

    //editar usuario
    public static void editarUsuario(Usuarios usuario) throws SQLException {

    String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ?, adm = ? WHERE id = ?";

    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getEmail());
        stmt.setString(3, usuario.getSenha());
        stmt.setInt(4, usuario.isAdm() ? 1 : 0);
        stmt.setInt(5, usuario.getId());

        stmt.executeUpdate();
    }
}

    
    //Listar Finanças
    
    public static List<Object[]> listarFinancas() throws SQLException {

    List<Object[]> lista = new ArrayList<>();

    String sql = """
        SELECT u.nome, f.valor
        FROM usuarios u
        JOIN financas f ON u.id = f.usuario_id
    """;

    try (Connection conn = ConexaoDB.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {

            Object[] linha = {
                rs.getString("nome"),
                rs.getBigDecimal("valor")
            };

            lista.add(linha);
        }
    }

    return lista;
}

    public static List<Object[]> listarFinancasPorUsuario(int usuarioId) throws SQLException {

    List<Object[]> lista = new java.util.ArrayList<>();

    String sql = """
        SELECT u.nome, f.valor
        FROM usuarios u
        JOIN financas f ON u.id = f.usuario_id
        WHERE u.id = ?
    """;

    try (java.sql.Connection conn = ConexaoDB.getConnection();
         java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, usuarioId);

        try (java.sql.ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString("nome"),
                    rs.getBigDecimal("valor")
                });
            }
        }
    }

    return lista;
}


}
