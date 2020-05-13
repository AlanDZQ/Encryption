package com.encryption.application.backend;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
/**
 * The Class for changing Java object to DB Object.
 */
public class UserAdaptor {
    public DBObject toDBObject(User user) {
        return new BasicDBObject("key", user.getKey())
                .append("IV", user.getIV())
                .append("cipherText", user.getCipherText())
                .append("padding", user.getPadding())
                .append("algorithm", user.getAlgorithm());
    }
}
