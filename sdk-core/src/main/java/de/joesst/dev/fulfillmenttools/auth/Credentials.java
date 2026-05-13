package de.joesst.dev.fulfillmenttools.auth;

/**
 * Marker interface for authentication credentials used with the fulfillmenttools SDK.
 * Implementations provide the credentials needed to authenticate with the fulfillmenttools platform.
 */
public sealed interface Credentials permits EmailPasswordCredentials {}
