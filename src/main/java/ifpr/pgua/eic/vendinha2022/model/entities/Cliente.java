package ifpr.pgua.eic.vendinha2022.model.entities;

public class Cliente {
    
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    public Cliente(int id, String nome, String cpf, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }

    public Cliente(String nome, String cpf, String email, String telefone) {
        this(-1,nome,cpf,email,telefone);
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }



    public String getCpf() {
        return cpf;
    }



    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String toString(){
        return this.nome;
    }

    
}
