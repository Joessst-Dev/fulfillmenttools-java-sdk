package de.joesst.dev.fulfillmenttools.internal.http;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public record SdkHttpResponse(int statusCode, Map<String, String> headers, byte[] body) {

    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }

    public String bodyAsString() {
        return body != null ? new String(body, StandardCharsets.UTF_8) : "";
    }

    public String header(String name) {
        return headers.get(name.toLowerCase());
    }
}
