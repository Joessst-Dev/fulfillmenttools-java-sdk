package de.joesst.dev.fulfillmenttools.internal.http;

import de.joesst.dev.fulfillmenttools.internal.Version;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public final class JdkHttpTransport implements HttpTransport {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);

    private final HttpClient client;

    public JdkHttpTransport() {
        this(HttpClient.newBuilder()
                .connectTimeout(DEFAULT_TIMEOUT)
                .build());
    }

    JdkHttpTransport(HttpClient client) {
        this.client = client;
    }

    @Override
    public SdkHttpResponse execute(SdkHttpRequest request) throws IOException {
        try {
            HttpResponse<byte[]> response = client.send(
                    toJdkRequest(request),
                    HttpResponse.BodyHandlers.ofByteArray()
            );
            return toSdkResponse(response);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Request interrupted", e);
        }
    }

    @Override
    public CompletableFuture<SdkHttpResponse> executeAsync(SdkHttpRequest request) {
        return client.sendAsync(
                toJdkRequest(request),
                HttpResponse.BodyHandlers.ofByteArray()
        ).thenApply(this::toSdkResponse);
    }

    private HttpRequest toJdkRequest(SdkHttpRequest request) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(buildUri(request))
                .timeout(DEFAULT_TIMEOUT)
                .header("User-Agent", Version.USER_AGENT)
                .header("Accept", "application/json");

        request.headers().forEach(builder::header);

        byte[] body = request.body();
        if (body != null) {
            builder.header("Content-Type", "application/json");
        }

        builder.method(
                request.method().name(),
                body != null
                        ? HttpRequest.BodyPublishers.ofByteArray(body)
                        : HttpRequest.BodyPublishers.noBody()
        );

        return builder.build();
    }

    private URI buildUri(SdkHttpRequest request) {
        Map<String, List<String>> queryParams = request.queryParams();
        if (queryParams.isEmpty()) {
            return URI.create(request.url());
        }
        String query = queryParams.entrySet().stream()
                .flatMap(e -> e.getValue().stream()
                        .map(v -> encode(e.getKey()) + "=" + encode(v)))
                .collect(Collectors.joining("&"));
        return URI.create(request.url() + "?" + query);
    }

    private SdkHttpResponse toSdkResponse(HttpResponse<byte[]> response) {
        Map<String, String> headers = new LinkedHashMap<>();
        response.headers().map().forEach((name, values) -> {
            if (!values.isEmpty()) {
                headers.put(name.toLowerCase(Locale.ROOT), values.get(0));
            }
        });
        return new SdkHttpResponse(
                response.statusCode(),
                Collections.unmodifiableMap(headers),
                response.body()
        );
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
