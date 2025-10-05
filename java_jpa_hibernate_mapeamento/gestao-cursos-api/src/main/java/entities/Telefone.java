package entities;

import javax.persistence.*;

@Entity
@Table(name = "telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ddd", length = 3)
    private String ddd;

    @Column(name = "numero", nullable = false, length = 15)
    private String numero;

    @Column(name = "tipo", length = 20)
    private String tipo; // CELULAR, RESIDENCIAL, COMERCIAL

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    // Constructors
    public Telefone() {}

    public Telefone(String ddd, String numero, String tipo) {
        this.ddd = ddd;
        this.numero = numero;
        this.tipo = tipo;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public String toString() {
        return "Telefone{" +
                "id=" + id +
                ", ddd='" + ddd + '\'' +
                ", numero='" + numero + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
