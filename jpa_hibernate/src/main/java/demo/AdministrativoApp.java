package demo;

import entities.Pessoa;
import entities.Produto;
import models.PessoaModel;
import models.ProdutoModel;

import java.time.LocalDate;
import java.util.List;

public class AdministrativoApp {

    public static void main(String[] args) {
        System.out.println("=== Iniciando Aplicação Administrativa ===\n");

        // Testando CRUD de Produtos
        testProdutoCRUD();

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Testando CRUD de Pessoas
        testPessoaCRUD();

        System.out.println("\n=== Finalizando Aplicação Administrativa ===");
    }

    private static void testProdutoCRUD() {
        System.out.println(">>> TESTANDO CRUD DE PRODUTOS <<<\n");

        ProdutoModel produtoModel = new ProdutoModel();

        // 1) Criando produtos
        System.out.println("1. CRIANDO PRODUTOS:");
        Produto p1 = new Produto();
        p1.setNome("TV");
        p1.setPreco(300.0);
        p1.setQuantidade(100);
        p1.setStatus(true);
        produtoModel.create(p1);

        Produto p2 = new Produto("Notebook", 50, 1500.0, true);
        produtoModel.create(p2);

        Produto p3 = new Produto("Mouse", 200, 25.0, true);
        produtoModel.create(p3);

        System.out.println("\n2. BUSCANDO TODOS OS PRODUTOS:");
        List<Produto> produtos = produtoModel.findAll();
        System.out.println("Qtde de produtos encontrados: " + produtos.size());
        for (Produto produto : produtos) {
            System.out.println("- " + produto);
        }

        // 3) Buscando produto por ID
        if (!produtos.isEmpty()) {
            System.out.println("\n3. BUSCANDO PRODUTO POR ID:");
            Long primeiroId = produtos.get(0).getId();
            Produto produtoEncontrado = produtoModel.findById(primeiroId);
            if (produtoEncontrado != null) {
                System.out.println("Produto encontrado: " + produtoEncontrado);
            }

            // 4) Atualizando produto
            System.out.println("\n4. ATUALIZANDO PRODUTO:");
            produtoEncontrado.setPreco(350.0);
            produtoEncontrado.setQuantidade(80);
            produtoModel.update(produtoEncontrado);

            // 5) Verificando atualização
            Produto produtoAtualizado = produtoModel.findById(primeiroId);
            System.out.println("Produto após atualização: " + produtoAtualizado);

            // 6) Deletando produto (último da lista)
            if (produtos.size() > 1) {
                System.out.println("\n5. DELETANDO PRODUTO:");
                Produto produtoParaDeletar = produtos.get(produtos.size() - 1);
                produtoModel.delete(produtoParaDeletar);

                // Verificando exclusão
                List<Produto> produtosAposDelecao = produtoModel.findAll();
                System.out.println("Produtos restantes após exclusão: " + produtosAposDelecao.size());
            }
        }
    }

    private static void testPessoaCRUD() {
        System.out.println(">>> TESTANDO CRUD DE PESSOAS <<<\n");

        PessoaModel pessoaModel = new PessoaModel();

        // 1) Criando pessoas
        System.out.println("1. CRIANDO PESSOAS:");
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("João Silva");
        pessoa1.setEmail("joao.silva@email.com");
        pessoa1.setIdade(30);
        pessoa1.setCpf("12345678901");
        pessoa1.setDataNascimento(LocalDate.of(1993, 5, 15));
        pessoaModel.create(pessoa1);

        Pessoa pessoa2 = new Pessoa("Maria Santos", "maria.santos@email.com", 25, "98765432100", LocalDate.of(1998, 8, 20));
        pessoaModel.create(pessoa2);

        Pessoa pessoa3 = new Pessoa("Carlos Oliveira", "carlos.oliveira@email.com", 35, "11122233344", LocalDate.of(1988, 12, 10));
        pessoaModel.create(pessoa3);

        System.out.println("\n2. BUSCANDO TODAS AS PESSOAS:");
        List<Pessoa> pessoas = pessoaModel.findAll();
        System.out.println("Qtde de pessoas encontradas: " + pessoas.size());
        for (Pessoa pessoa : pessoas) {
            System.out.println("- " + pessoa);
        }

        // 3) Buscando pessoa por ID
        if (!pessoas.isEmpty()) {
            System.out.println("\n3. BUSCANDO PESSOA POR ID:");
            Long primeiroId = pessoas.get(0).getId();
            Pessoa pessoaEncontrada = pessoaModel.findById(primeiroId);
            if (pessoaEncontrada != null) {
                System.out.println("Pessoa encontrada: " + pessoaEncontrada);
            }

            // 4) Atualizando pessoa
            System.out.println("\n4. ATUALIZANDO PESSOA:");
            pessoaEncontrada.setEmail("joao.silva.novo@email.com");
            pessoaEncontrada.setIdade(31);
            pessoaModel.update(pessoaEncontrada);

            // 5) Verificando atualização
            Pessoa pessoaAtualizada = pessoaModel.findById(primeiroId);
            System.out.println("Pessoa após atualização: " + pessoaAtualizada);

            // 6) Deletando pessoa (última da lista)
            if (pessoas.size() > 1) {
                System.out.println("\n5. DELETANDO PESSOA:");
                Pessoa pessoaParaDeletar = pessoas.get(pessoas.size() - 1);
                pessoaModel.delete(pessoaParaDeletar);

                // Verificando exclusão
                List<Pessoa> pessoasAposDelecao = pessoaModel.findAll();
                System.out.println("Pessoas restantes após exclusão: " + pessoasAposDelecao.size());
            }
        }
    }
}
