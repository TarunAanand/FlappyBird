import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

class MongoDB {
    private MongoDatabase database;
    private MongoCollection<Document> usersCollection;

    public MongoDB() {
        // Replace with your MongoDB URI and database name
        String uri = "mongodb://localhost:27017";
        String dbName = "flappybird";  // Replace with your database name

        try {
            MongoClient mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase(dbName);
            usersCollection = database.getCollection("users");
            System.out.println("Connected to MongoDB!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password) {
        // Check if the user already exists
        Document existingUser = usersCollection.find(new Document("username", username)).first();
        if (existingUser != null) {
            return false;  // Username already exists
        }

        // Create a document for the new user
        Document newUser = new Document("username", username)
                .append("password", password);

        // Insert the new user into the collection
        usersCollection.insertOne(newUser);
        return true;  // Registration successful
    }

    public boolean validateUser(String username, String password) {
        // Find user document with matching username and password
        Document userDoc = usersCollection.find(
                new Document("username", username)
                        .append("password", password)
        ).first();

        return userDoc != null;  // Returns true if user exists, false otherwise
    }
}
