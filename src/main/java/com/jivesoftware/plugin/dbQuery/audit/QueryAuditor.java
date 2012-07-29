package com.jivesoftware.plugin.dbQuery.audit;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 7/17/12
 * Time: 10:50 PM
 */
public class QueryAuditor {
    private String username;
    private String databaseUsed;
    private String queryPerformed;
    private long timePerformed;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDatabaseUsed() {
        return databaseUsed;
    }

    public void setDatabaseUsed(String databaseUsed) {
        this.databaseUsed = databaseUsed;
    }

    public String getQueryPerformed() {
        return queryPerformed;
    }

    public void setQueryPerformed(String queryPerformed) {
        this.queryPerformed = queryPerformed;
    }

    public long getTimePerformed() {
        return timePerformed;
    }

    public void setTimePerformed(long timePerformed) {
        this.timePerformed = timePerformed;
    }

    /**
     * Constructor for QueryAuditor object
     * @param username The username of the user that performed the query.
     * @param databaseUsed The database that was used to perform the query.
     * @param queryPerformed The query that was performed.
     * @param timePerformed The time that the user was performed.
     */
    public QueryAuditor(String username, String databaseUsed, String queryPerformed, long timePerformed){
        this.username = username;
        this.databaseUsed = databaseUsed;
        this.queryPerformed = queryPerformed;
        this.timePerformed = timePerformed;
    }

    public QueryAuditor(){
    }
}
