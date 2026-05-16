package de.joesst.dev.fulfillmenttools.internal.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.inbound.AssignedUserInput;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
record UpdateStowJobBody(
        Integer version,
        Integer priority,
        Instant targetTime,
        List<AssignedUserInput> assignedUsers,
        CustomAttributes customAttributes
) {}
