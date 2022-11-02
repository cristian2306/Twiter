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
import java.util.HashMap;

public class DBConnection {

    private static MongoCollection<Document> messages;
    private static FindIterable<Document> iterable;
    public static MongoClient mongoClient;

    public static void main(String[] args) {
        HashMap<String, String> data = new HashMap<>();
        HashMap<String, String> data1 = new HashMap<>();
        HashMap<String, HashMap> threa_d = new HashMap<>();
        data.put("User", "User");
        data.put("Message", "Se supone que esto es una prueba");
        data1.put("User", "Usuario");
        data1.put("Message", "Se supone que esto es una prueba para users");
        threa_d.put("1", data);
        threa_d.put("2", data);
        connect();
        insertIntoDB(data, "test");
        insertIntoDB(data1, "test");
        insertIntoDB(threa_d, "test");

    }

    public static MongoClient connect() {
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

        mongoClient = MongoClients.create(settings);
        //Crea una instancia del cliente mongo conectado a la base de datos
        MongoDatabase db = mongoClient.getDatabase("Messages");
        return MongoClients.create(settings);
//        messages = db.getCollection("cadenas");
    }

    /**
     * Funcion encargada de ingresar el valor de la cadena a la base de datos
     * retorna un JSON con los ultimos 10 valores de
     *
     * @param valueChain
     * @return
     */
    public static String insertIntoDB(HashMap data, String collection) {
        MongoDatabase database = mongoClient.getDatabase("AREP-DOCKER-TALLER1");
        MongoCollection<Document> chains = database.getCollection(collection);

        //Crea un documento BSON con el cliente
        Document chain = new Document("_id", new ObjectId());
        data.forEach((k, v) -> chain.append((String) k, v));
        chain.append("createdAt", LocalDateTime.now());

        //Agrega el nuevo cliente a la colección
        chains.insertOne(chain);
        System.out.println("SE SUPONE ESTO ES TODO LO DE CHAINS");
        System.out.println(chains.find());
        //Creacion del orden de la coleccion
        Bson orderBySort = orderBy(descending("createdAt"));

        //Creacion de JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(printChains(chains));
    }

    /**
     * Funcion generada para imprimir los ultimos 10 registros de la coleccion
     * cadenas
     *
     * @param chains
     */
    public static ArrayList<Document> printChains(MongoCollection<Document> chains) {
        ArrayList<Document> jsonObjects = new ArrayList<>();
        FindIterable<Document> iterable = chains.find();
        for (Document d : iterable) {
            System.out.println(d);
            jsonObjects.add(d);
        }
        return jsonObjects;
    }
}
