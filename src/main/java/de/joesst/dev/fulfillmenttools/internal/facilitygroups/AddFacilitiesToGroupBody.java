package de.joesst.dev.fulfillmenttools.internal.facilitygroups;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
record AddFacilitiesToGroupBody(String name, List<String> facilitiesToAdd, Integer version) {}
