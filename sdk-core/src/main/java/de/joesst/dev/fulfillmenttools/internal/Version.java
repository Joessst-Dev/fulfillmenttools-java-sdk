package de.joesst.dev.fulfillmenttools.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Version {

    public static final String SDK_VERSION;
    public static final String USER_AGENT;

    static {
        String v = "unknown";
        try (InputStream in = Version.class.getResourceAsStream("version.properties")) {
            if (in != null) {
                Properties props = new Properties();
                props.load(in);
                v = props.getProperty("version", "unknown");
            }
        } catch (IOException ignored) {}
        SDK_VERSION = v;
        USER_AGENT = "fulfillmenttools-java-sdk/" + SDK_VERSION;
    }

    private Version() {}
}
