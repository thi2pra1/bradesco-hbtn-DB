import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


public class UsuarioOperations {

    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public UsuarioOperations() {
        MongoDBConnection connection = new MongoDBConnection();
        this.database = connection.getDatabase();

        if (this.database != null) {
            this.collection = this.database.getCollection("usuarios");
            System.out.println("Coleção 'usuarios' inicializada com sucesso!");
        } else {
            System.err.println("Erro: Não foi possível conectar ao banco de dados!");
        }
    }

    // Método para inserir um único usuário
    public void insertOne(Usuario usuario) {
        try {
            Document doc = usuario.toDocument();
            collection.insertOne(doc);
            System.out.println("✅ Usuário inserido com sucesso: " + usuario);
        } catch (Exception e) {
            System.err.println("❌ Erro ao inserir usuário: " + e.getMessage());
        }
    }

    // Método para inserir múltiplos usuários
    public void insertMany(List<Usuario> usuarios) {
        try {
            List<Document> documents = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                documents.add(usuario.toDocument());
            }
            collection.insertMany(documents);
            System.out.println("✅ " + usuarios.size() + " usuários inseridos com sucesso!");
            for (Usuario usuario : usuarios) {
                System.out.println("   - " + usuario);
            }
        } catch (Exception e) {
            System.err.println("❌ Erro ao inserir usuários: " + e.getMessage());
        }
    }

    // Método para consultar todos os registros
    public void consultarTodos() {
        try {
            System.out.println("\n📋 CONSULTA DE TODOS OS USUÁRIOS:");
            System.out.println("=".repeat(40));

            MongoCursor<Document> cursor = collection.find().iterator();
            int count = 0;

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Usuario usuario = Usuario.fromDocument(doc);
                System.out.println((++count) + ". " + usuario);
            }

            if (count == 0) {
                System.out.println("Nenhum usuário encontrado na coleção.");
            } else {
                System.out.println("Total de usuários: " + count);
            }

            cursor.close();
            System.out.println("=".repeat(40) + "\n");
        } catch (Exception e) {
            System.err.println("❌ Erro ao consultar usuários: " + e.getMessage());
        }
    }

    // Método para atualizar a idade de um usuário pelo nome
    public void atualizarIdade(String nome, int novaIdade) {
        try {
            UpdateResult result = collection.updateOne(
                eq("nome", nome),
                set("idade", novaIdade)
            );

            if (result.getMatchedCount() > 0) {
                System.out.println("✅ Idade do usuário '" + nome + "' atualizada para " + novaIdade + " anos");
                System.out.println("   Documentos modificados: " + result.getModifiedCount());
            } else {
                System.out.println("⚠️ Usuário '" + nome + "' não encontrado para atualização");
            }
        } catch (Exception e) {
            System.err.println("❌ Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    // Método para deletar um usuário pelo nome
    public void deletarUsuario(String nome) {
        try {
            DeleteResult result = collection.deleteOne(eq("nome", nome));

            if (result.getDeletedCount() > 0) {
                System.out.println("✅ Usuário '" + nome + "' removido com sucesso");
                System.out.println("   Documentos removidos: " + result.getDeletedCount());
            } else {
                System.out.println("⚠️ Usuário '" + nome + "' não encontrado para remoção");
            }
        } catch (Exception e) {
            System.err.println("❌ Erro ao deletar usuário: " + e.getMessage());
        }
    }

    // Método para limpar toda a coleção (útil para testes)
    public void limparColecao() {
        try {
            DeleteResult result = collection.deleteMany(new Document());
            System.out.println("🧹 Coleção limpa: " + result.getDeletedCount() + " documentos removidos");
        } catch (Exception e) {
            System.err.println("❌ Erro ao limpar coleção: " + e.getMessage());
        }
    }

    // Método principal para executar todas as operações solicitadas
    public static void main(String[] args) {
        System.out.println("=== INICIANDO OPERAÇÕES MONGODB ===\n");

        UsuarioOperations operations = new UsuarioOperations();

        if (operations.collection == null) {
            System.err.println("Não foi possível inicializar as operações. Encerrando...");
            return;
        }

        // Limpar coleção para começar do zero (opcional)
        operations.limparColecao();

        // 1. INSERIR 3 REGISTROS
        System.out.println("🔸 PASSO 1: INSERINDO 3 USUÁRIOS");
        System.out.println("-".repeat(30));

        // Método 1: Inserir um por vez usando insertOne
        operations.insertOne(new Usuario("Alice", 25));
        operations.insertOne(new Usuario("Bob", 30));
        operations.insertOne(new Usuario("Charlie", 35));

        // Alternativa usando insertMany (comentado para usar insertOne como solicitado)
        /*
        List<Usuario> usuarios = Arrays.asList(
            new Usuario("Alice", 25),
            new Usuario("Bob", 30),
            new Usuario("Charlie", 35)
        );
        operations.insertMany(usuarios);
        */

        try {
            Thread.sleep(1000); // Pausa para garantir que as operações sejam processadas
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 2. CONSULTAR OS REGISTROS
        System.out.println("\n🔸 PASSO 2: CONSULTANDO REGISTROS APÓS INSERÇÃO");
        operations.consultarTodos();

        // 3. ALTERAR A IDADE DE BOB PARA 32 ANOS
        System.out.println("🔸 PASSO 3: ALTERANDO IDADE DE BOB PARA 32 ANOS");
        System.out.println("-".repeat(30));
        operations.atualizarIdade("Bob", 32);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4. CONSULTAR OS REGISTROS APÓS ATUALIZAÇÃO
        System.out.println("\n🔸 PASSO 4: CONSULTANDO REGISTROS APÓS ATUALIZAÇÃO");
        operations.consultarTodos();

        // 5. APAGAR O REGISTRO CHARLIE
        System.out.println("🔸 PASSO 5: REMOVENDO O USUÁRIO CHARLIE");
        System.out.println("-".repeat(30));
        operations.deletarUsuario("Charlie");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 6. CONSULTAR OS REGISTROS FINAIS
        System.out.println("\n🔸 PASSO 6: CONSULTANDO REGISTROS FINAIS");
        operations.consultarTodos();

        System.out.println("=== OPERAÇÕES MONGODB CONCLUÍDAS ===");
    }
}
