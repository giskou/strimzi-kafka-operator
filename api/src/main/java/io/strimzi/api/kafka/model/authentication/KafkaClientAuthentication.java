/*
 * Copyright Strimzi authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.strimzi.api.kafka.model.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.strimzi.api.kafka.model.UnknownPropertyPreserving;
import io.strimzi.crdgenerator.annotations.Description;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Configures the Kafka client authentication in client based components
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(name = KafkaClientAuthenticationTls.TYPE_TLS, value = KafkaClientAuthenticationTls.class),
    @JsonSubTypes.Type(name = KafkaClientAuthenticationScramSha512.TYPE_SCRAM_SHA_512, value = KafkaClientAuthenticationScramSha512.class),
    @JsonSubTypes.Type(name = KafkaClientAuthenticationPlain.TYPE_PLAIN, value = KafkaClientAuthenticationPlain.class),
    @JsonSubTypes.Type(name = KafkaClientAuthenticationOAuth.TYPE_OAUTH, value = KafkaClientAuthenticationOAuth.class),
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public abstract class KafkaClientAuthentication implements UnknownPropertyPreserving, Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Object> additionalProperties;

    @Description("Authentication type. " +
            "Currently the only supported types are `tls`, `scram-sha-512`, and `plain`. " +
            "`scram-sha-512` type uses SASL SCRAM-SHA-512 Authentication. " +
            "`plain` type uses SASL PLAIN Authentication. " +
            "`oauth` type uses SASL OAUTHBEARER Authentication. " +
            "The `tls` type uses TLS Client Authentication. " +
            "The `tls` type is supported only over TLS connections.")
    public abstract String getType();

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public void setAdditionalProperty(String name, Object value) {
        if (this.additionalProperties == null) {
            this.additionalProperties = new HashMap<>(1);
        }
        this.additionalProperties.put(name, value);
    }
}
