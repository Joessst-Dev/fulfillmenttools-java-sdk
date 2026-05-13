package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Tolerance configuration for quantity deviations during picking.
 *
 * <p>Maps to the {@code MeasurementValidation} schema in the fulfillmenttools OpenAPI spec.
 * All tolerance values are expressed as percentages (0–100 for short-pick tolerances;
 * no upper bound for over-pick tolerances).
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param overPickHardTolerancePercentage  Allowed hard over-pick deviation (no upper bound).
 * @param overPickSoftTolerancePercentage  Allowed soft over-pick deviation (no upper bound).
 * @param shortPickHardTolerancePercentage Allowed hard short-pick deviation (0–100).
 * @param shortPickSoftTolerancePercentage Allowed soft short-pick deviation (0–100).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MeasurementValidation(
        Double overPickHardTolerancePercentage,
        Double overPickSoftTolerancePercentage,
        Double shortPickHardTolerancePercentage,
        Double shortPickSoftTolerancePercentage
) {}
