package demo;

import entities.*;
import models.AlunoModel;
import models.CursoModel;

import java.time.LocalDate;
import java.util.List;

public class GestaoCursosMain {

    public static void main(String[] args) {
        System.out.println("=== Iniciando Sistema de Gestão de Cursos ===\n");

        // Populando o banco de dados com dados de teste
        popularBancoDeDados();

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Testando operações CRUD
        testarOperacoesCRUD();

        System.out.println("\n=== Finalizando Sistema de Gestão de Cursos ===");
    }

    private static void popularBancoDeDados() {
        System.out.println(">>> POPULANDO BANCO DE DADOS <<<\n");

        AlunoModel alunoModel = new AlunoModel();
        CursoModel cursoModel = new CursoModel();

        // 1. Criando um aluno com endereço e telefone
        System.out.println("1. CRIANDO ALUNO COM ENDEREÇO E TELEFONE:");

        Aluno aluno1 = new Aluno();
        aluno1.setNome("João Silva Santos");
        aluno1.setEmail("joao.silva@email.com");
        aluno1.setCpf("12345678901");
        aluno1.setDataNascimento(LocalDate.of(1995, 3, 15));
        aluno1.setMatricula("ALU001");

        // Adicionando endereço
        Endereco endereco1 = new Endereco();
        endereco1.setRua("Rua das Flores");
        endereco1.setNumero("123");
        endereco1.setBairro("Centro");
        endereco1.setCidade("São Paulo");
        endereco1.setEstado("SP");
        endereco1.setCep("01000-000");
        aluno1.addEndereco(endereco1);

        // Adicionando telefone
        Telefone telefone1 = new Telefone();
        telefone1.setDdd("11");
        telefone1.setNumero("999887766");
        telefone1.setTipo("CELULAR");
        aluno1.addTelefone(telefone1);

        alunoModel.create(aluno1);

        // 2. Criando um professor
        System.out.println("\n2. CRIANDO PROFESSOR:");

        Professor professor1 = new Professor();
        professor1.setNome("Dr. Maria Oliveira");
        professor1.setEmail("maria.oliveira@universidade.edu");
        professor1.setCpf("98765432100");
        professor1.setDataNascimento(LocalDate.of(1980, 7, 22));
        professor1.setEspecializacao("Ciência da Computação");
        professor1.setRegistroProfessor("PROF001");

        // 3. Criando material do curso
        System.out.println("\n3. CRIANDO MATERIAL DO CURSO:");

        MaterialCurso material1 = new MaterialCurso();
        material1.setTitulo("Introdução à Programação Java");
        material1.setDescricao("Material completo sobre fundamentos de Java");
        material1.setTipoMaterial("LIVRO");
        material1.setUrlRecurso("https://exemplo.com/java-basico");
        material1.setAutor("Prof. Maria Oliveira");

        // 4. Criando curso com professor, material e aluno
        System.out.println("\n4. CRIANDO CURSO COM PROFESSOR, MATERIAL E ALUNO:");

        Curso curso1 = new Curso();
        curso1.setNome("Programação Java Básica");
        curso1.setDescricao("Curso introdutório de programação em Java");
        curso1.setCargaHoraria(80);
        curso1.setDataInicio(LocalDate.of(2024, 2, 1));
        curso1.setDataFim(LocalDate.of(2024, 5, 30));
        curso1.setCodigoCurso("JAVA001");

        // Relacionamentos
        curso1.setProfessor(professor1);
        curso1.setMaterial(material1);
        curso1.addAluno(aluno1);

        cursoModel.create(curso1);

        System.out.println("\n✅ Banco de dados populado com sucesso!");
        System.out.println("- 1 Aluno (com 1 endereço e 1 telefone)");
        System.out.println("- 1 Professor");
        System.out.println("- 1 Curso (com 1 professor, 1 material e 1 aluno)");
        System.out.println("- 1 Material do Curso");
    }

    private static void testarOperacoesCRUD() {
        System.out.println(">>> TESTANDO OPERAÇÕES CRUD <<<\n");

        AlunoModel alunoModel = new AlunoModel();
        CursoModel cursoModel = new CursoModel();

        // TESTANDO CRUD DE ALUNOS
        System.out.println("=== TESTE CRUD - ALUNOS ===");

        // 1. Listando todos os alunos
        System.out.println("\n1. LISTANDO TODOS OS ALUNOS:");
        List<Aluno> alunos = alunoModel.findAll();
        for (Aluno aluno : alunos) {
            System.out.println("- " + aluno);
            System.out.println("  Endereços: " + aluno.getEnderecos().size());
            System.out.println("  Telefones: " + aluno.getTelefones().size());
            System.out.println("  Cursos: " + aluno.getCursos().size());
        }

        // 2. Buscando aluno por ID
        if (!alunos.isEmpty()) {
            System.out.println("\n2. BUSCANDO ALUNO POR ID:");
            Long alunoId = alunos.get(0).getId();
            Aluno alunoEncontrado = alunoModel.findById(alunoId);
            if (alunoEncontrado != null) {
                System.out.println("Aluno encontrado: " + alunoEncontrado);
                System.out.println("Endereços: ");
                for (Endereco endereco : alunoEncontrado.getEnderecos()) {
                    System.out.println("  - " + endereco);
                }
                System.out.println("Telefones: ");
                for (Telefone telefone : alunoEncontrado.getTelefones()) {
                    System.out.println("  - " + telefone);
                }
            }

            // 3. Atualizando aluno
            System.out.println("\n3. ATUALIZANDO ALUNO:");
            alunoEncontrado.setEmail("joao.silva.novo@email.com");

            // Adicionando um segundo endereço
            Endereco endereco2 = new Endereco();
            endereco2.setRua("Avenida Paulista");
            endereco2.setNumero("1000");
            endereco2.setBairro("Bela Vista");
            endereco2.setCidade("São Paulo");
            endereco2.setEstado("SP");
            endereco2.setCep("01310-100");
            alunoEncontrado.addEndereco(endereco2);

            alunoModel.update(alunoEncontrado);

            // Verificando atualização
            Aluno alunoAtualizado = alunoModel.findById(alunoId);
            System.out.println("Aluno após atualização: " + alunoAtualizado);
            System.out.println("Total de endereços: " + alunoAtualizado.getEnderecos().size());
        }

        System.out.println("\n" + "=".repeat(50));

        // TESTANDO CRUD DE CURSOS
        System.out.println("\n=== TESTE CRUD - CURSOS ===");

        // 1. Listando todos os cursos
        System.out.println("\n1. LISTANDO TODOS OS CURSOS:");
        List<Curso> cursos = cursoModel.findAll();
        for (Curso curso : cursos) {
            System.out.println("- " + curso);
            if (curso.getProfessor() != null) {
                System.out.println("  Professor: " + curso.getProfessor().getNome());
            }
            if (curso.getMaterial() != null) {
                System.out.println("  Material: " + curso.getMaterial().getTitulo());
            }
            System.out.println("  Alunos matriculados: " + curso.getAlunos().size());
        }

        // 2. Buscando curso por ID
        if (!cursos.isEmpty()) {
            System.out.println("\n2. BUSCANDO CURSO POR ID:");
            Long cursoId = cursos.get(0).getId();
            Curso cursoEncontrado = cursoModel.findById(cursoId);
            if (cursoEncontrado != null) {
                System.out.println("Curso encontrado: " + cursoEncontrado);
                System.out.println("Professor: " + cursoEncontrado.getProfessor().getNome());
                System.out.println("Material: " + cursoEncontrado.getMaterial().getTitulo());
                System.out.println("Alunos matriculados:");
                for (Aluno aluno : cursoEncontrado.getAlunos()) {
                    System.out.println("  - " + aluno.getNome() + " (" + aluno.getMatricula() + ")");
                }
            }

            // 3. Atualizando curso
            System.out.println("\n3. ATUALIZANDO CURSO:");
            cursoEncontrado.setCargaHoraria(100);
            cursoEncontrado.setDescricao("Curso introdutório de programação em Java - ATUALIZADO");
            cursoModel.update(cursoEncontrado);

            // Verificando atualização
            Curso cursoAtualizado = cursoModel.findById(cursoId);
            System.out.println("Curso após atualização: " + cursoAtualizado);
            System.out.println("Nova carga horária: " + cursoAtualizado.getCargaHoraria() + "h");
        }

        // 4. Criando um segundo aluno e adicionando ao curso
        System.out.println("\n4. CRIANDO SEGUNDO ALUNO E ADICIONANDO AO CURSO:");

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Ana Costa Lima");
        aluno2.setEmail("ana.costa@email.com");
        aluno2.setCpf("11122233344");
        aluno2.setDataNascimento(LocalDate.of(1992, 8, 10));
        aluno2.setMatricula("ALU002");

        // Adicionando endereço para o segundo aluno
        Endereco endereco3 = new Endereco();
        endereco3.setRua("Rua Augusta");
        endereco3.setNumero("500");
        endereco3.setBairro("Consolação");
        endereco3.setCidade("São Paulo");
        endereco3.setEstado("SP");
        endereco3.setCep("01305-000");
        aluno2.addEndereco(endereco3);

        // Adicionando telefone para o segundo aluno
        Telefone telefone2 = new Telefone();
        telefone2.setDdd("11");
        telefone2.setNumero("888776655");
        telefone2.setTipo("CELULAR");
        aluno2.addTelefone(telefone2);

        alunoModel.create(aluno2);

        // Adicionando o segundo aluno ao curso existente
        if (!cursos.isEmpty()) {
            Curso cursoParaAtualizar = cursos.get(0);
            cursoParaAtualizar.addAluno(aluno2);
            cursoModel.update(cursoParaAtualizar);

            System.out.println("Segundo aluno adicionado ao curso com sucesso!");

            // Verificando a atualização
            Curso cursoComNovosAlunos = cursoModel.findById(cursoParaAtualizar.getId());
            System.out.println("Total de alunos no curso: " + cursoComNovosAlunos.getAlunos().size());
        }

        System.out.println("\n✅ Todos os testes CRUD executados com sucesso!");
    }
}
