package de.joesst.dev.fulfillmenttools.internal.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.inbound.AssignedUserInput;
import de.joesst.dev.fulfillmenttools.inbound.StowLineItemForCreation;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
record CreateStowJobBody(
        FacilityId facilityRef,
        String status,
        List<StowLineItemForCreation> stowLineItems,
        List<AssignedUserInput> assignedUsers,
        CustomAttributes customAttributes,
        Integer priority,
        String shortId,
        Instant targetTime
) {}
