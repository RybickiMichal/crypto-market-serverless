package pl.jvng.cryptomarketserverless.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@RegisterForReflection
@Value
public class CryptoPriceList {

    private List<CryptoPrice> cryptoPrices;
}
