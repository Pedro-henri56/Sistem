package sistem;

import java.time.LocalDate;

public class Usuarios {

    /**
     * @return the gasto
     */
    public String getGasto() {
        return gasto;
    }

    /**
     * @param gasto the gasto to set
     */
    public void setGasto(String gasto) {
        this.gasto = gasto;
    }

    /**
     * @return the valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * @return the data
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * @return the cpf
     */
    

    /**
     * @param cpf the cpf to set
     */
   

    private int id;
    private String nome;
    private String email;
    private String cpf;
    private String senha;
    private boolean adm;
    private String gasto;
    private int valor;
    private LocalDate data;
    

    public Usuarios(int id) {
        this.id = id;
    }
    public Usuarios() {
}


    public Usuarios(String nome, String email, String cpf, String senha) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
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
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
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
