package pl.jvng.cryptomarketserverless.repository;

import pl.jvng.cryptomarketserverless.model.Crypto;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class CryptoRepository {
    private static List<Crypto> cryptos;

    static {
        cryptos = Arrays.asList(
                new Crypto("Tether", "https://coinmarketcap.com/currencies/tether/"),
                new Crypto("Bitcoin", "https://coinmarketcap.com/currencies/bitcoin/"),
                new Crypto("Ethereum", "https://coinmarketcap.com/currencies/ethereum/"),
                new Crypto("Cardano", "https://coinmarketcap.com/currencies/cardano/")
        );
    }

    public static List<Crypto> getCryptos() {
        return cryptos;
    }

}
