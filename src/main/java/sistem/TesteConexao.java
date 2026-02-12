
package sistem;
import java.sql.Connection;



public class TesteConexao {
    public static void main(String[] args) {
        try {
            Connection con = ConexaoDB.getConnection();
            System.out.println("CONEXÃO OK 🚀");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    
