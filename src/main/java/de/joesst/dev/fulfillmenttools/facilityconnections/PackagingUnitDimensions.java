package de.joesst.dev.fulfillmenttools.facilityconnections;

/**
 * Physical dimensions and weight limit for a packaging unit.
 *
 * @param length          length in centimetres
 * @param width           width in centimetres
 * @param height          height in centimetres
 * @param maxWeightInGram maximum allowed weight in grams
 */
public record PackagingUnitDimensions(Double length, Double width, Double height, Double maxWeightInGram) {}
