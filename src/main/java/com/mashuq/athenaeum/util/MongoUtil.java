package com.mashuq.athenaeum.util;

import com.mongodb.client.MongoDatabase;

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
