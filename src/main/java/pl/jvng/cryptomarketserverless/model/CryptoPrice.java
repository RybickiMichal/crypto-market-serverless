package pl.jvng.cryptomarketserverless.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@RegisterForReflection
@Value
public class CryptoPrice {

    private Crypto crypto;
    private String date;
    private BigDecimal price;
    private BigDecimal marketCap;
    private BigDecimal volume;
    private BigDecimal low;
    private BigDecimal high;
}
