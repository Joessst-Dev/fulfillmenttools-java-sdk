package de.joesst.dev.fulfillmenttools.internal;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VersionTest {

    @Test
    void sdkVersionIsNotUnknown() {
        assertThat(Version.SDK_VERSION).isNotEqualTo("unknown");
    }

    @Test
    void userAgentContainsVersion() {
        assertThat(Version.USER_AGENT)
                .startsWith("fulfillmenttools-java-sdk/")
                .doesNotContain("unknown");
    }
}
