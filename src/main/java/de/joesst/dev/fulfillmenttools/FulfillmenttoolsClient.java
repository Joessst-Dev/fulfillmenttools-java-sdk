package de.joesst.dev.fulfillmenttools;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.joesst.dev.fulfillmenttools.auth.Credentials;
import de.joesst.dev.fulfillmenttools.auth.EmailPasswordCredentials;
import de.joesst.dev.fulfillmenttools.auth.GoogleIdentityToolkitTokenProvider;
import de.joesst.dev.fulfillmenttools.auth.TokenProvider;
import de.joesst.dev.fulfillmenttools.internal.JsonCodec;
import de.joesst.dev.fulfillmenttools.internal.http.AuthenticatingTransport;
import de.joesst.dev.fulfillmenttools.internal.http.HttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.JdkHttpTransport;
import de.joesst.dev.fulfillmenttools.internal.http.ResponseHandler;
import de.joesst.dev.fulfillmenttools.facilities.FacilitiesClient;
import de.joesst.dev.fulfillmenttools.carriers.CarriersClient;
import de.joesst.dev.fulfillmenttools.handoverjobs.HandoverJobsClient;
import de.joesst.dev.fulfillmenttools.inbound.InboundClient;
import de.joesst.dev.fulfillmenttools.internal.carriers.CarriersClientImpl;
import de.joesst.dev.fulfillmenttools.internal.facilities.FacilitiesClientImpl;
import de.joesst.dev.fulfillmenttools.internal.handoverjobs.HandoverJobsClientImpl;
import de.joesst.dev.fulfillmenttools.internal.inbound.InboundClientImpl;
import de.joesst.dev.fulfillmenttools.internal.orders.OrdersClientImpl;
import de.joesst.dev.fulfillmenttools.internal.packjobs.PackingClientImpl;
import de.joesst.dev.fulfillmenttools.internal.pickjobs.PickJobsClientImpl;
import de.joesst.dev.fulfillmenttools.internal.reservations.ReservationsClientImpl;
import de.joesst.dev.fulfillmenttools.internal.returns.ReturnsClientImpl;
import de.joesst.dev.fulfillmenttools.internal.stocks.StocksClientImpl;
import de.joesst.dev.fulfillmenttools.orders.OrdersClient;
import de.joesst.dev.fulfillmenttools.packjobs.PackingClient;
import de.joesst.dev.fulfillmenttools.pickjobs.PickJobsClient;
import de.joesst.dev.fulfillmenttools.reservations.ReservationsClient;
import de.joesst.dev.fulfillmenttools.returns.ReturnsClient;
import de.joesst.dev.fulfillmenttools.stocks.StocksClient;

import java.util.Objects;

public final class FulfillmenttoolsClient {

    private final HttpTransport transport;
    private final ResponseHandler responseHandler;
    private final String baseUrl;

    private volatile OrdersClient ordersClient;
    private volatile FacilitiesClient facilitiesClient;
    private volatile StocksClient stocksClient;
    private volatile PickJobsClient pickJobsClient;
    private volatile ReservationsClient reservationsClient;
    private volatile PackingClient packingClient;
    private volatile HandoverJobsClient handoverJobsClient;
    private volatile ReturnsClient returnsClient;
    private volatile CarriersClient carriersClient;
    private volatile InboundClient inboundClient;

    private FulfillmenttoolsClient(HttpTransport transport, ResponseHandler responseHandler,
                                   String baseUrl) {
        this.transport = transport;
        this.responseHandler = responseHandler;
        this.baseUrl = baseUrl;
    }

    public OrdersClient orders() {
        OrdersClient local = ordersClient;
        if (local == null) {
            synchronized (this) {
                local = ordersClient;
                if (local == null) {
                    local = ordersClient = new OrdersClientImpl(transport, responseHandler, baseUrl);
                }
            }
        }
        return local;
    }

    public FacilitiesClient facilities() {
        FacilitiesClient local = facilitiesClient;
        if (local == null) {
            synchronized (this) {
                local = facilitiesClient;
                if (local == null) {
                    local = facilitiesClient = new FacilitiesClientImpl(transport, responseHandler, baseUrl);
                }
            }
        }
        return local;
    }

    public StocksClient stocks() {
        StocksClient local = stocksClient;
        if (local == null) {
            synchronized (this) {
                local = stocksClient;
                if (local == null) {
                    local = stocksClient = new StocksClientImpl(transport, responseHandler, baseUrl);
                }
            }
        }
        return local;
    }

    public PickJobsClient pickJobs() {
        PickJobsClient local = pickJobsClient;
        if (local == null) {
            synchronized (this) {
                local = pickJobsClient;
                if (local == null) {
                    local = pickJobsClient = new PickJobsClientImpl(transport, responseHandler, baseUrl);
                }
            }
        }
        return local;
    }

    public ReservationsClient reservations() {
        ReservationsClient local = reservationsClient;
        if (local == null) {
            synchronized (this) {
                local = reservationsClient;
                if (local == null) {
                    local = reservationsClient = new ReservationsClientImpl(transport, responseHandler, baseUrl);
                }
            }
        }
        return local;
    }

    public PackingClient packing() {
        PackingClient local = packingClient;
        if (local == null) {
            synchronized (this) {
                local = packingClient;
                if (local == null) {
                    local = packingClient = new PackingClientImpl(transport, responseHandler, baseUrl);
                }
            }
        }
        return local;
    }

    public HandoverJobsClient handoverJobs() {
        HandoverJobsClient local = handoverJobsClient;
        if (local == null) {
            synchronized (this) {
                local = handoverJobsClient;
                if (local == null) {
                    local = handoverJobsClient = new HandoverJobsClientImpl(transport, responseHandler, baseUrl);
                }
            }
        }
        return local;
    }

    public ReturnsClient returns() {
        ReturnsClient local = returnsClient;
        if (local == null) {
            synchronized (this) {
                local = returnsClient;
                if (local == null) {
                    local = returnsClient = new ReturnsClientImpl(transport, responseHandler, baseUrl);
                }
            }
        }
        return local;
    }

    public CarriersClient carriers() {
        CarriersClient local = carriersClient;
        if (local == null) {
            synchronized (this) {
                local = carriersClient;
                if (local == null) {
                    local = carriersClient = new CarriersClientImpl(transport, responseHandler, baseUrl);
                }
            }
        }
        return local;
    }

    public InboundClient inbound() {
        InboundClient local = inboundClient;
        if (local == null) {
            synchronized (this) {
                local = inboundClient;
                if (local == null) {
                    local = inboundClient = new InboundClientImpl(transport, responseHandler, baseUrl);
                }
            }
        }
        return local;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String baseUrl;
        private Credentials credentials;
        private TokenProvider tokenProvider;
        private HttpTransport httpTransport;
        private ObjectMapper objectMapper;

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder credentials(Credentials credentials) {
            this.credentials = credentials;
            return this;
        }

        /** Overrides the default {@link GoogleIdentityToolkitTokenProvider}. Useful for testing. */
        public Builder tokenProvider(TokenProvider tokenProvider) {
            this.tokenProvider = tokenProvider;
            return this;
        }

        /** Overrides the default {@link JdkHttpTransport}. Useful for testing or custom interceptors. */
        public Builder httpTransport(HttpTransport httpTransport) {
            this.httpTransport = httpTransport;
            return this;
        }

        /** Overrides the default Jackson {@link ObjectMapper}. */
        public Builder objectMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
            return this;
        }

        public FulfillmenttoolsClient build() {
            Objects.requireNonNull(baseUrl, "baseUrl must not be null");

            HttpTransport rawTransport = httpTransport != null ? httpTransport : new JdkHttpTransport();

            TokenProvider tokens;
            if (tokenProvider != null) {
                tokens = tokenProvider;
            } else if (credentials instanceof EmailPasswordCredentials c) {
                tokens = new GoogleIdentityToolkitTokenProvider(c, rawTransport);
            } else {
                throw new IllegalStateException(
                        "Either credentials (EmailPasswordCredentials) or tokenProvider must be set");
            }

            HttpTransport authenticating = new AuthenticatingTransport(rawTransport, tokens);
            JsonCodec codec = objectMapper != null ? new JsonCodec(objectMapper) : new JsonCodec();
            ResponseHandler responseHandler = new ResponseHandler(codec);

            return new FulfillmenttoolsClient(authenticating, responseHandler, baseUrl);
        }
    }
}
