package de.joesst.dev.fulfillmenttools;

/**
 * Thrown when a network or transport layer error occurs, such as a connection failure or timeout.
 * This wraps IO exceptions that occur during HTTP communication.
 */
public class TransportException extends FulfillmenttoolsException {

    /**
     * Constructs an exception for transport failures with an underlying cause.
     *
     * @param message a message describing the transport error.
     * @param cause the underlying IOException or other exception that caused this failure.
     */
    public TransportException(String message, Throwable cause) {
        super(message, cause);
    }
}
