package de.joesst.dev.fulfillmenttools;

/**
 * Structured error details returned by the fulfillmenttools API.
 *
 * @param description A detailed error message describing what went wrong.
 * @param summary A human-readable error summary.
 * @param requestVersion The request format version used in the error response.
 * @param version The error response format version.
 */
public record ApiError(
        String description,
        String summary,
        int requestVersion,
        int version
) {}
