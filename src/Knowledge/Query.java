package Knowledge;

import java.util.ArrayList;

/**
 * Static class query that store the query list
 */
public class Query {
    // Static list of query
    private static ArrayList<String> queryList;

    // Initialize the static list
    static {
        queryList = new ArrayList<>();
    }

    /**
     * Add query to the list
     * @param query query
     */
    public static void addQuery(String query) {
        queryList.add(query);
    }

    /**
     * Get the query list
     * @return static query list
     */
    public static ArrayList<String> getQueryList() { return queryList; }
}
