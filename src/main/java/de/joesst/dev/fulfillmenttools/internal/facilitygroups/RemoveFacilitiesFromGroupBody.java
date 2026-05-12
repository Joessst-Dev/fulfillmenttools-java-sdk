package de.joesst.dev.fulfillmenttools.internal.facilitygroups;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
record RemoveFacilitiesFromGroupBody(String name, List<String> facilitiesToRemove, Integer version) {}
