import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


public class MongoDBConnection {


    // Variáveis de configuração
    private static final String USERNAME = "aluno";
    private static final String PASSWORD = "BN10UNhMxMDZUgEh";
    private static final String CLUSTER_URL = "cluster0.brbyr.mongodb.net"; // Substitua pelo seu cluster se for diferente
    private static final String DATABASE_NAME = "Cluster0"; // Substitua pelo nome do seu banco de dados


    private MongoClient mongoClient;
    private MongoDatabase database;


    public MongoDBConnection() {
        try {
            // String de conexão com credenciais
            String connectionString = String.format("mongodb+srv://"+USERNAME+":"+PASSWORD+"@"+CLUSTER_URL+"/?retryWrites=true&w=majority&appName="+DATABASE_NAME);


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
