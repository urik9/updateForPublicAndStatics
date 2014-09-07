package com.wixpress.uri;

import com.wixpress.framework.domain.UserGuid;
import com.wixpress.framework.security.DefaultSecurityService;
import com.wixpress.framework.security.WixSession;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.joda.time.Duration;

import java.io.IOException;

/**
* Created with IntelliJ IDEA.
* User: uri
* Date: 5/26/14
* Time: 3:19 PM
* To change this template use File | Settings | File Templates.
*/
public class StaticsAPI {

    private final HttpClient httpClient;


    public StaticsAPI() {
        httpClient = new HttpClient();
    }

    public int get(String loadedToStaticsUrl) throws IOException {
        GetMethod getMethod = new GetMethod(loadedToStaticsUrl);
        getMethod.setRequestHeader("Accept-Encoding", "gzip");
        return httpClient.executeMethod(getMethod);
    }

    public void upload(String uploadToStaticsUrl, String json, String pageId) throws IOException {
        WixSession session = WixSession.create(UserGuid.random(), Duration.standardDays(1));
        DefaultSecurityService securityService = new DefaultSecurityService();
        String sessionToken = securityService.toStringToken(session);
        PutMethod method = new PutMethod(uploadToStaticsUrl);
        method.setRequestBody(json);

        method.setRequestHeader("X-Prospero-Filename", pageId);
        method.setRequestHeader("X-Prospero-Mediasource", "sites");
        method.setRequestHeader("Cookie", "wixSession=" + sessionToken);
        if (this.httpClient.executeMethod(method) != 200)
            throw new RuntimeException("Failed to upload to statics pageId: " + pageId);
    }
}
