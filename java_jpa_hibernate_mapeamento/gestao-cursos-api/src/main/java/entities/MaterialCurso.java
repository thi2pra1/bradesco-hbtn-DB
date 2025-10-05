package entities;

import javax.persistence.*;

@Entity
@Table(name = "material_curso")
public class MaterialCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @Column(name = "tipo_material", length = 50)
    private String tipoMaterial; // LIVRO, VIDEO, PDF, SLIDES, etc.

    @Column(name = "url_recurso", length = 300)
    private String urlRecurso;

    @Column(name = "autor", length = 100)
    private String autor;

    // Um curso tem apenas um material, e o material pertence apenas a um curso (one-to-one)
    @OneToOne(mappedBy = "material")
    private Curso curso;

    // Constructors
    public MaterialCurso() {}

    public MaterialCurso(String titulo, String descricao, String tipoMaterial, String urlRecurso, String autor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipoMaterial = tipoMaterial;
        this.urlRecurso = urlRecurso;
        this.autor = autor;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public String getUrlRecurso() {
        return urlRecurso;
    }

    public void setUrlRecurso(String urlRecurso) {
        this.urlRecurso = urlRecurso;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "MaterialCurso{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", tipoMaterial='" + tipoMaterial + '\'' +
                ", urlRecurso='" + urlRecurso + '\'' +
                ", autor='" + autor + '\'' +
                '}';
    }
}
