package com.wixpress.uri;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: uri
 * Date: 5/25/14
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class StaticsPage {
    private static final String EXTENSION = ".z";
    private final String pageId;
    private final String json;
    private final String machineName;


    public StaticsPage(final String pageId, final String json, String machineName) {
        this.pageId = pageId;
        this.json = json;
        this.machineName = machineName;
    }

    private String getStaticsBaseUrl() {
        return "http://static." +
                machineName +
                ".wixpress.com/";
    }

    public boolean isLoaded(StaticsAPI staticsAPI) throws IOException {
        String isLoadedToStaticsUrl = getStaticsBaseUrl() + "/sites/" + this.pageId + EXTENSION;
        int status = staticsAPI.get(isLoadedToStaticsUrl);
        return (status == 200);
    }


    public void upload(StaticsAPI staticsAPI) throws IOException {
        String uploadToStaticsUrl = getStaticsBaseUrl() + "api/add_file_named2";
        String json = this.json;
        String pageId = this.pageId;
        staticsAPI.upload(uploadToStaticsUrl, json, pageId);
    }

    @Override
    public String toString() {
        return "StaticsPage{" +
                "pageId='" + this.pageId + '\'' +
                ", json='" + this.json + '\'' +
                '}';
    }

}
