package com.college.funondb;

import com.google.gson.Gson;
import com.microsoft.azure.documentdb.Database;
import com.microsoft.azure.documentdb.Document;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;
import com.microsoft.azure.documentdb.DocumentCollection;

import java.util.ArrayList;
import java.util.List;

import com.college.funondb.DocumentClientFactory;
import com.college.model.Columns;

public class ColumnDbDao {
	
	private static final String DATABASE_ID = "colleges_list";
	
	// The name of our collection.
	private static final String COLLECTION_ID = "listofcol";
	
	// We'll use Gson for POJO <=> JSON serialization for this example.
	private static Gson gson = new Gson();
	
	// The DocumentDB Client
	private static DocumentClient documentClient = DocumentClientFactory
	        .getDocumentClient();
	
	// Cache for the database object, so we don't have to query for it to
	// retrieve self links.
	private static Database databaseCache;
	
	private static ColumnDbDao columnDbDao;
	
	public static ColumnDbDao getInstance(){
		if(columnDbDao == null){
			columnDbDao = new ColumnDbDao();
		}
		return columnDbDao;
	}
	
	// Cache for the collection object, so we don't have to query for it to
	// retrieve self links.
	private static DocumentCollection collectionCache;

//	@Override
	public List<Columns> UpdateCollegesList(String loc, int tut) {
		System.out.println("In Db operations \n loc: "+loc+"-tut: "+tut);
		List<Columns> columns = new ArrayList<Columns>();
		System.out.println("collections self: "+getTodoCollection().getSelfLink());
		List<Document> columnsList = documentClient
				.queryDocuments(
						getTodoCollection().getSelfLink(), 
						"SELECT r.COLLEGE_NAME, r.COLLEGE_LOCATION, r.OTHER_COURSES, r.TUTION_FEES FROM root r where r.COLLEGE_LOCATION='"+loc+"' and r.TUTION_FEES>"+tut+" and r.IS_BIGDATA=1", null)
				.getQueryIterable().toList();
		System.out.println("Columns JSON:   " + columnsList);
		for(Document columnItemDocument : columnsList ){
			columns.add(gson.fromJson(columnItemDocument.toString(),Columns.class));
		}
		
		return columns;
	}
	
	private Database getTodoDatabase() {
        if (databaseCache == null) {
            // Get the database if it exists
            List<Database> databaseList = documentClient
                    .queryDatabases(
                            "SELECT * FROM root r WHERE r.id='" + DATABASE_ID
                                    + "'", null).getQueryIterable().toList();
            System.out.println("Database JSON:   " + databaseList);
            if (databaseList.size() > 0) {
                // Cache the database object so we won't have to query for it
                // later to retrieve the selfLink.
                databaseCache = databaseList.get(0);
            } else {
                // Create the database if it doesn't exist.
                try {
                    Database databaseDefinition = new Database();
                    databaseDefinition.setId(DATABASE_ID);

                    databaseCache = documentClient.createDatabase(
                            databaseDefinition, null).getResource();
                } catch (DocumentClientException e) {
                    // TODO: Something has gone terribly wrong - the app wasn't
                    // able to query or create the collection.
                    // Verify your connection, endpoint, and key.
                    e.printStackTrace();
                }
            }
        }

        return databaseCache;
    }

    private DocumentCollection getTodoCollection() {
        if (collectionCache == null) {
            // Get the collection if it exists.
            List<DocumentCollection> collectionList = documentClient
                    .queryCollections(
                            getTodoDatabase().getSelfLink(),
                            "SELECT * FROM root r WHERE r.id='" + COLLECTION_ID
                                    + "'", null).getQueryIterable().toList();
            System.out.println("database self: "+getTodoDatabase().getSelfLink());
            System.out.println("Tables JSON:   " + collectionList);
            if (collectionList.size() > 0) {
                // Cache the collection object so we won't have to query for it
                // later to retrieve the selfLink.
                collectionCache = collectionList.get(0);
            } else {
                // Create the collection if it doesn't exist.
                try {
                    DocumentCollection collectionDefinition = new DocumentCollection();
                    collectionDefinition.setId(COLLECTION_ID);

                    collectionCache = documentClient.createCollection(
                            getTodoDatabase().getSelfLink(),
                            collectionDefinition, null).getResource();
                } catch (DocumentClientException e) {
                    // TODO: Something has gone terribly wrong - the app wasn't
                    // able to query or create the collection.
                    // Verify your connection, endpoint, and key.
                    e.printStackTrace();
                }
            }
        }

        return collectionCache;
    }
	
	

}
