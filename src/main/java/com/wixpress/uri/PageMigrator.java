package com.wixpress.uri;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.io.IOException;
import java.sql.*;

/**
* Created with IntelliJ IDEA.
* User: uri
* Date: 5/26/14
* Time: 3:05 PM
* To change this template use File | Settings | File Templates.
*/
public class PageMigrator {
    private final StaticsAPI staticsAPI;
    private final String machineName;
    private int alreadyOnStatics;
    private int failedToUpload;
    private int succeededToUpload;

    public PageMigrator(String machineName) {
        this.machineName = machineName;
        staticsAPI = new StaticsAPI();
    }

    public void migrate() throws SQLException, IOException {
        DateTime start = new DateTime();
        final Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" +
                        machineName +
                        ".wixpress.com:3306/mysql", "wix", "wix");

        // find data size
        // while accumulator < data size do:
        // read next chunk of rows
        // add chunk of rows to accumulator
        // do stuff with data
        // define accumulator

        try {
            Statement stmt = connection.createStatement();
            int dataSize = findDataSize(stmt);
            int chunkSize = 100;
            int accumulator = 0;
            System.out.println("Found " + dataSize + " pages");
            while (accumulator < dataSize) {
                System.out.println(String.format("Processing %d of %d ......",accumulator,dataSize));
                ResultSet rs = readNextChunkOfRows(stmt, chunkSize, accumulator);
                accumulator += chunkSize;
                doStuff(rs);
            }

//            ResultSet rs = stmt.executeQuery("SELECT page_id,digest FROM wix_html_public.html_public_pages");
//            doStuff(rs);

            // rsummary related stuff...
            DateTime stop = new DateTime();
            System.out.println("");
            System.out.println("Summary:");
            Period period = new Period(start, stop);
            System.out.println(String.format("Elapsed time : %s:%s:%s", period.getHours(), period.getMinutes(), period.getSeconds()));
            System.out.println("already on statics:" + alreadyOnStatics);
            System.out.println("succeeded to upload:" + succeededToUpload);
            System.out.println("failed to upload:" + failedToUpload);
        } finally {
            connection.close();
        }

    }

    private int findDataSize(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM wix_html_public.html_public_pages");
        rs.next();
        return rs.getInt(1);

    }

    private ResultSet readNextChunkOfRows(Statement stmt, int chunkSize, int accumulator) throws SQLException {
        return stmt.executeQuery(
                String.format("SELECT page_id,digest FROM wix_html_public.html_public_pages LIMIT %d, %d",accumulator,chunkSize));
    }

    private  void doStuff(ResultSet rs) throws SQLException, IOException {
        while (rs.next()) {
            System.out.print(".");
            String pageId = rs.getString("page_id");
            String json = rs.getString("digest");
            uploadToStatics(pageId, json);
        }
    }

    private void uploadToStatics(String pageId, String json) throws IOException {
        StaticsPage staticsPage = new StaticsPage(pageId,json, machineName);
        if (!staticsPage.isLoaded(staticsAPI)) {
            try {
                staticsPage.upload(staticsAPI);
                succeededToUpload++;
            } catch (RuntimeException e) {
                failedToUpload++;
            }
        } else {
            alreadyOnStatics++;
        }
    }
}
