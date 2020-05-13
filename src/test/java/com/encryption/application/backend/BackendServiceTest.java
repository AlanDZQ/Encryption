package com.encryption.application.backend;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.junit.Test;

public class BackendServiceTest {
    @Test
    public void DBTest() {
        MongoClient mongoClient = new MongoClient();
        User user = new User("4","1","1","1","1");
        DB database = mongoClient.getDB("Encryption");
        DBCollection collection = database.getCollection("Encryption");
        UserAdaptor userAdaptor = new UserAdaptor();
        DBObject userObject = userAdaptor.toDBObject(user);
        collection.insert(userObject);
        ObjectId id = (ObjectId)userObject.get( "_id" );
        System.out.println(id.toString());
    }
}
