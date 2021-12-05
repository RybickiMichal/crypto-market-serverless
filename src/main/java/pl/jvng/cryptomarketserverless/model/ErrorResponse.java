package pl.jvng.cryptomarketserverless.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@RegisterForReflection
@Value
public class ErrorResponse {

    private String message;
    private int errorCode;
}
