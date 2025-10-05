package models;

import entities.Curso;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CursoModel {

    public void create(Curso curso) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.persist(curso);
            em.getTransaction().commit();
            System.out.println("Curso criado com sucesso !!!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao criar um curso !!!" + e.getMessage());
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando a transação");
        }
    }

    public Curso findById(Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
        EntityManager em = emf.createEntityManager();
        Curso curso = null;

        try {
            curso = em.find(Curso.class, id);
            if (curso != null) {
                // Force loading of lazy collections and relationships
                curso.getAlunos().size();
                if (curso.getProfessor() != null) {
                    curso.getProfessor().getNome();
                }
                if (curso.getMaterial() != null) {
                    curso.getMaterial().getTitulo();
                }
                System.out.println("Curso encontrado: " + curso.getNome());
            } else {
                System.out.println("Curso com ID " + id + " não encontrado !!!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar curso por ID !!!" + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
        return curso;
    }

    public List<Curso> findAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
        EntityManager em = emf.createEntityManager();
        List<Curso> cursos = new ArrayList<>();

        try {
            TypedQuery<Curso> query = em.createQuery(
                "SELECT DISTINCT c FROM Curso c LEFT JOIN FETCH c.professor LEFT JOIN FETCH c.material LEFT JOIN FETCH c.alunos",
                Curso.class
            );
            cursos = query.getResultList();
            System.out.println("Encontrados " + cursos.size() + " cursos na base de dados");
        } catch (Exception e) {
            System.err.println("Erro ao buscar todos os cursos !!!" + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
        return cursos;
    }

    public void update(Curso curso) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando a transação de atualização");
            em.getTransaction().begin();
            em.merge(curso);
            em.getTransaction().commit();
            System.out.println("Curso atualizado com sucesso !!!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao atualizar o curso !!!" + e.getMessage());
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando a transação de atualização");
        }
    }

    public void delete(Curso curso) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando a transação de exclusão");
            em.getTransaction().begin();
            Curso cursoParaRemover = em.find(Curso.class, curso.getId());
            if (cursoParaRemover != null) {
                em.remove(cursoParaRemover);
                em.getTransaction().commit();
                System.out.println("Curso removido com sucesso !!!");
            } else {
                System.out.println("Curso não encontrado para remoção !!!");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao remover o curso !!!" + e.getMessage());
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando a transação de exclusão");
        }
    }
}
