package sistem;

public class Usuarios {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private boolean adm;

    public Usuarios() {
    }

    public Usuarios(int id) {
        this.id = id;
    }

    public Usuarios(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuarios(String nome, String email, String senha, boolean adm) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.adm = adm;
    }

    public Usuarios(int id, String nome, String email, String senha, boolean adm) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.adm = adm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdm() {
        return adm;
    }

    public void setAdm(boolean adm) {
        this.adm = adm;
    }
}
