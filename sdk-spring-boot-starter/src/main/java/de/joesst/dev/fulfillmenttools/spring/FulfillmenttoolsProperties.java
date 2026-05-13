package de.joesst.dev.fulfillmenttools.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the fulfillmenttools SDK Spring Boot auto-configuration.
 *
 * <p>All properties are bound from the {@code fulfillmenttools.*} namespace in
 * {@code application.properties} / {@code application.yml}.
 *
 * <p>Example {@code application.properties}:
 * <pre>{@code
 * fulfillmenttools.project-id=ocff-abc123-pre
 * fulfillmenttools.api-key=AIza...
 * fulfillmenttools.username=service@ocff-abc123-pre.com
 * fulfillmenttools.password=s3cr3t
 * }</pre>
 *
 * <p>This class is intentionally mutable (not a record) to support Spring Boot's
 * JavaBean-style property binding.
 */
@ConfigurationProperties(prefix = "fulfillmenttools")
public class FulfillmenttoolsProperties {

    /**
     * fulfillmenttools project ID (e.g. {@code ocff-abc123-pre}).
     * Used to derive the API base URL: {@code https://<projectId>.api.fulfillmenttools.com}.
     */
    private String projectId;

    /**
     * API key for Google Identity Toolkit authentication.
     */
    private String apiKey;

    /**
     * Service account email for authentication.
     */
    private String username;

    /**
     * Service account password for authentication.
     */
    private String password;

    /**
     * Returns the project ID.
     *
     * @return the project ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Sets the project ID.
     *
     * @param projectId the project ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    /**
     * Returns the API key.
     *
     * @return the API key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets the API key.
     *
     * @param apiKey the API key
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Returns the service account username (email).
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the service account username (email).
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the service account password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the service account password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
