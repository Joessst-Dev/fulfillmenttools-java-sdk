package de.joesst.dev.fulfillmenttools.internal.reservations;

record CreateReservationBody(String facilityRef, String tenantArticleId, int quantity) {}
