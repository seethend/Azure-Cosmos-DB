package com.college.funondb;

import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.DocumentClient;

public class DocumentClientFactory {
    private static final String HOST = "https://vivek.documents.azure.com:443/";
    private static final String MASTER_KEY = "05bXZsWHmFmf4nST5aPVgTtlbO070l8x0hME7vFS57NdYdd7xL79wmUPPfi1q0uuHxs3VfFoaDLxlODpAnHEcg==";

    private static DocumentClient documentClient = new DocumentClient(HOST, MASTER_KEY,
            ConnectionPolicy.GetDefault(), ConsistencyLevel.Session);

    public static DocumentClient getDocumentClient() {
        return documentClient;
    }

}
