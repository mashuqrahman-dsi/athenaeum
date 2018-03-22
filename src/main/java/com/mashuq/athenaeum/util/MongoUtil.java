package com.mashuq.athenaeum.util;

import java.util.Collection;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class MongoUtil {

	public static boolean collectionExists(String collectionName,
			MongoDatabase mongoDatabase) {
		for (final String name : mongoDatabase.listCollectionNames()) {
			if (name.equalsIgnoreCase(collectionName)) {
				return true;
			}
		}
		return false;
	}
}
