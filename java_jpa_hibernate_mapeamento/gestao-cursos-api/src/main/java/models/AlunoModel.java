package models;

import entities.Aluno;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class AlunoModel {

    public void create(Aluno aluno) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.persist(aluno);
            em.getTransaction().commit();
            System.out.println("Aluno criado com sucesso !!!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao criar um aluno !!!" + e.getMessage());
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando a transação");
        }
    }

    public Aluno findById(Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
        EntityManager em = emf.createEntityManager();
        Aluno aluno = null;

        try {
            aluno = em.find(Aluno.class, id);
            if (aluno != null) {
                // Force loading of lazy collections
                aluno.getEnderecos().size();
                aluno.getTelefones().size();
                aluno.getCursos().size();
                System.out.println("Aluno encontrado: " + aluno.getNome());
            } else {
                System.out.println("Aluno com ID " + id + " não encontrado !!!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar aluno por ID !!!" + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
        return aluno;
    }

    public List<Aluno> findAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
        EntityManager em = emf.createEntityManager();
        List<Aluno> alunos = new ArrayList<>();

        try {
            TypedQuery<Aluno> query = em.createQuery(
                "SELECT DISTINCT a FROM Aluno a LEFT JOIN FETCH a.enderecos LEFT JOIN FETCH a.telefones",
                Aluno.class
            );
            alunos = query.getResultList();
            System.out.println("Encontrados " + alunos.size() + " alunos na base de dados");
        } catch (Exception e) {
            System.err.println("Erro ao buscar todos os alunos !!!" + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
        return alunos;
    }

    public void update(Aluno aluno) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando a transação de atualização");
            em.getTransaction().begin();
            em.merge(aluno);
            em.getTransaction().commit();
            System.out.println("Aluno atualizado com sucesso !!!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao atualizar o aluno !!!" + e.getMessage());
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando a transação de atualização");
        }
    }

    public void delete(Aluno aluno) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Iniciando a transação de exclusão");
            em.getTransaction().begin();
            Aluno alunoParaRemover = em.find(Aluno.class, aluno.getId());
            if (alunoParaRemover != null) {
                em.remove(alunoParaRemover);
                em.getTransaction().commit();
                System.out.println("Aluno removido com sucesso !!!");
            } else {
                System.out.println("Aluno não encontrado para remoção !!!");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao remover o aluno !!!" + e.getMessage());
        } finally {
            em.close();
            emf.close();
            System.out.println("Finalizando a transação de exclusão");
        }
    }
}
