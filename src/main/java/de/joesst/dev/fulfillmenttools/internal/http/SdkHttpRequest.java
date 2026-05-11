package de.joesst.dev.fulfillmenttools.internal.http;

import java.util.*;

public final class SdkHttpRequest {

    private final HttpMethod method;
    private final String url;
    private final Map<String, String> headers;
    private final Map<String, List<String>> queryParams;
    private final byte[] body;

    private SdkHttpRequest(Builder builder) {
        this.method = Objects.requireNonNull(builder.method, "method must not be null");
        this.url = Objects.requireNonNull(builder.url, "url must not be null");
        this.headers = Collections.unmodifiableMap(new LinkedHashMap<>(builder.headers));
        this.queryParams = Collections.unmodifiableMap(deepCopy(builder.queryParams));
        this.body = builder.body;
    }

    public HttpMethod method() { return method; }
    public String url() { return url; }
    public Map<String, String> headers() { return headers; }
    public Map<String, List<String>> queryParams() { return queryParams; }
    public byte[] body() { return body; }

    public static Builder builder() { return new Builder(); }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.method = this.method;
        b.url = this.url;
        b.headers.putAll(this.headers);
        this.queryParams.forEach((k, v) -> b.queryParams.put(k, new ArrayList<>(v)));
        b.body = this.body;
        return b;
    }

    private static Map<String, List<String>> deepCopy(Map<String, List<String>> source) {
        Map<String, List<String>> copy = new LinkedHashMap<>();
        source.forEach((k, v) -> copy.put(k, List.copyOf(v)));
        return copy;
    }

    public static final class Builder {
        private HttpMethod method;
        private String url;
        private final Map<String, String> headers = new LinkedHashMap<>();
        private final Map<String, List<String>> queryParams = new LinkedHashMap<>();
        private byte[] body;

        public Builder method(HttpMethod method) { this.method = method; return this; }
        public Builder url(String url) { this.url = url; return this; }

        public Builder header(String name, String value) {
            headers.put(name, value);
            return this;
        }

        public Builder queryParam(String name, String value) {
            queryParams.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
            return this;
        }

        public Builder body(byte[] body) { this.body = body; return this; }

        public SdkHttpRequest build() { return new SdkHttpRequest(this); }
    }
}
