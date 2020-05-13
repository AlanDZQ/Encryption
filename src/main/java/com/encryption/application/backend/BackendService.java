package com.encryption.application.backend;

import com.mongodb.*;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
/**
 * The Database Logic component.
 */
@Service
public class BackendService {

    public String saveToDB(User user){
        MongoClient mongoClient = new MongoClient();
        DB database = mongoClient.getDB("Encryption");
        DBCollection collection = database.getCollection("Encryption");
        UserAdaptor userAdaptor = new UserAdaptor();
        DBObject userObject = userAdaptor.toDBObject(user);
        collection.insert(userObject);
        ObjectId id = (ObjectId)userObject.get( "_id" );
        return id.toString();
    }
}
