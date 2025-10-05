import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


public class MongoDBConnection {


    // Variáveis de configuração para MongoDB Local
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String HOST = "127.0.0.1";
    private static final String PORT = "27017";
    private static final String DATABASE_NAME = "cursojava";


    private MongoClient mongoClient;
    private MongoDatabase database;


    public MongoDBConnection() {
        try {
            // String de conexão para MongoDB local com autenticação
            String connectionString = String.format("mongodb://%s:%s@%s:%s/%s?authSource=admin",
                USERNAME, PASSWORD, HOST, PORT, DATABASE_NAME);


            // Configuração do cliente MongoDB
            ConnectionString connString = new ConnectionString(connectionString);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .build();


            // Criando o cliente
            mongoClient = MongoClients.create(settings);


            // Selecionando o banco de dados
            database = mongoClient.getDatabase(DATABASE_NAME);


            System.out.println("Conexão estabelecida com sucesso ao MongoDB!");
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public MongoDatabase getDatabase() {
        return database;
    }


    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexão encerrada com sucesso.");
        }
    }


    public static void main(String[] args) {
        MongoDBConnection connection = new MongoDBConnection();


        // Exemplo de uso
        if (connection.getDatabase() != null) {
            System.out.println("Banco de dados: " + connection.getDatabase().getName());
        }


        try {
            // Aguarde para garantir que os processos internos sejam concluídos
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        connection.closeConnection();
    }


}
