package edu.eci.arem.conection;

import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Sorts.*;

public class DBConnection {
    
    private static MongoCollection<Document> messages;
    private static FindIterable<Document> iterable;
    public static MongoClient mongoClient;
    
    public static void main(String[] args) {
        connect();
        insertIntoDB("Prueba BD1");
    }
    
    public static void connect() {
        // Conexión a base de datos mongodb

        //URL para Atlasdb en la nube
        String connstr = "mongodb+srv://admin:admin@messages.evhe9ot.mongodb.net/?retryWrites=true&w=majority";

        //URL para conexión local
        //String connstr ="mongodb://localhost:40000/?retryWrites=true&w=majority";

        //Crea objeto de tipo ConnectionString
        ConnectionString connectionString = new ConnectionString(connstr);

        //Crea objeto con configuraciones para el cliente mongo
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();

        //Crea una instancia del cliente mongo conectado a la base de datos
        mongoClient = MongoClients.create(settings);    
        MongoDatabase db = mongoClient.getDatabase("Messages");
        messages = db.getCollection("cadenas");
        

    }

    /**
     * Funcion encargada de ingresar el valor de la cadena a la base de datos
     * retorna un JSON con los ultimos 10 valores de 
     * @param valueChain
     * @return
     */
    public static String insertIntoDB(String valueChain) {
        MongoDatabase database = mongoClient.getDatabase("AREP-DOCKER-TALLER1");
        MongoCollection<Document> chains = database.getCollection("cadenas");

        //Crea un documento BSON con el cliente
        Document chain = new Document("_id", new ObjectId());
        chain.append("Value", valueChain);
        chain.append("createdAt", LocalDateTime.now());

        //Agrega el nuevo cliente a la colección
        chains.insertOne(chain);
        System.out.println("SE SUPONE ESTO ES TODO LO DE CHAINS");
        System.out.println(chains.find());
        //Creacion del orden de la coleccion
        Bson orderBySort = orderBy(descending("createdAt"));

        //Creacion de JSON
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(printChains(chains));
    }

    /**
     * Funcion generada para imprimir los ultimos 10 registros de la coleccion cadenas
     * @param chains
     */
    public static ArrayList<Document> printChains(MongoCollection<Document> chains){
        ArrayList<Document> jsonObjects = new ArrayList<>();
        FindIterable<Document> iterable = chains.find();
        for (Document d : iterable) {
            System.out.println(d);
            jsonObjects.add(d);
        }
        return jsonObjects;
    }
}