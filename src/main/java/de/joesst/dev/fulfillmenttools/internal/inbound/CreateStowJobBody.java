package de.joesst.dev.fulfillmenttools.internal.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.inbound.AssignedUserInput;
import de.joesst.dev.fulfillmenttools.inbound.StowLineItemForCreation;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
record CreateStowJobBody(
        String facilityRef,
        String status,
        List<StowLineItemForCreation> stowLineItems,
        List<AssignedUserInput> assignedUsers,
        Map<String, Object> customAttributes,
        Integer priority,
        String shortId,
        Instant targetTime
) {}
