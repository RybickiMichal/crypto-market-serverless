package pl.jvng.cryptomarketserverless.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@RegisterForReflection
@Value
public class Crypto {

    private String name;
    private String dataSourceUrl;
}
