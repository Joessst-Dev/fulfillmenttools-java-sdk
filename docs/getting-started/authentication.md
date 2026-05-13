# Authentication

The SDK supports email/password authentication via Google Identity Toolkit, which automatically refreshes access tokens.

## Quick Start

```java
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsClient;
import de.joesst.dev.fulfillmenttools.auth.EmailPasswordCredentials;

FulfillmenttoolsClient client = FulfillmenttoolsClient.builder()
    .baseUrl("https://your-project.api.fulfillmenttools.com")
    .credentials(new EmailPasswordCredentials(
        "user@example.com",
        "password",
        "your-api-key"
    ))
    .build();
```

## Credentials

### EmailPasswordCredentials

Authenticates using email, password, and API key. The SDK automatically handles token refresh via Google Identity Toolkit.

```java
var credentials = new EmailPasswordCredentials(
    email,
    password,
    apiKey
);
```

The credentials are used only during token exchange—they are not stored or transmitted with each request. Tokens are automatically refreshed 60 seconds before expiry, ensuring seamless operation without manual intervention.

## Custom Token Provider

For advanced use cases, you can provide a custom `TokenProvider` implementation:

```java
public interface TokenProvider {
    String getAccessToken();
    void invalidate();
}
```

Example:

```java
FulfillmenttoolsClient client = FulfillmenttoolsClient.builder()
    .baseUrl("https://your-project.api.fulfillmenttools.com")
    .tokenProvider(new MyCustomTokenProvider())
    .build();
```

This is useful for integrating with external OAuth2 systems or token management services.

## Environment Variables

Store sensitive credentials in environment variables:

```java
FulfillmenttoolsClient client = FulfillmenttoolsClient.builder()
    .baseUrl(System.getenv("FFT_BASE_URL"))
    .credentials(new EmailPasswordCredentials(
        System.getenv("FFT_USERNAME"),
        System.getenv("FFT_PASSWORD"),
        System.getenv("FFT_API_KEY")
    ))
    .build();
```
