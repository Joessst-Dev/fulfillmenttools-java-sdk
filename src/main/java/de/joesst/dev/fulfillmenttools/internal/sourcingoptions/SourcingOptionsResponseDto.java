package de.joesst.dev.fulfillmenttools.internal.sourcingoptions;

import de.joesst.dev.fulfillmenttools.sourcingoptions.SourcingOption;

import java.util.List;

record SourcingOptionsResponseDto(String id, SourcingOptionsResultDto result) {
    record SourcingOptionsResultDto(List<SourcingOption> options) {}
}
