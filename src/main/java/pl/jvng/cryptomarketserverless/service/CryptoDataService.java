package pl.jvng.cryptomarketserverless.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.jvng.cryptomarketserverless.model.Crypto;
import pl.jvng.cryptomarketserverless.model.CryptoPrice;
import pl.jvng.cryptomarketserverless.model.CryptoPriceList;
import pl.jvng.cryptomarketserverless.repository.CryptoRepository;
import pl.jvng.cryptomarketserverless.utils.RequestUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Named("crypto")
public class CryptoDataService implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger LOG = Logger.getLogger(CryptoDataService.class);

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, final Context context) {
        try {
            CryptoPriceList cryptoPriceList = new CryptoPriceList(fetchCryptoPrices(CryptoRepository.getCryptos()));
            cryptoPriceList.getCryptoPrices()
                    .forEach(LOG::info);
            return RequestUtils.successResponse(cryptoPriceList);
        } catch (Exception e) {
            LOG.error("Error processing request", e);
            return RequestUtils.errorResponse(500, "[Lambda] Unable to fetch Crypto from DynamoDB");
        }
    }

    public CryptoPrice fetchCryptoPrice(Crypto crypto) {
        Document document = initDocument(crypto.getDataSourceUrl());
        LOG.info("starting fetching: " + crypto.getName());
        return CryptoPrice.builder()
                .crypto(crypto)
                .price(extractPrice(document.getElementsByClass("priceTitle").text()))
                .low(extractPrice(document.getElementsByClass("n78udj-5 dBJPYV").text().split(" ")[0]))
                .high(extractPrice(document.getElementsByClass("n78udj-5 dBJPYV").text().split(" ")[1]))
                .marketCap(extractPrice(document.getElementsByClass("statsValue").text().split(" ")[0]))
                .volume(extractPrice(document.getElementsByClass("statsValue").text().split(" ")[2]))
                .date(LocalDate.now().toString())
                .build();
    }

    public List<CryptoPrice> fetchCryptoPrices(List<Crypto> cryptos) {
        return cryptos.parallelStream()
                .map(this::fetchCryptoPrice)
                .collect(Collectors.toList());
    }

    private Document initDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException exception) {
            LOG.error("Error during fetching crypto data");
            return null;
        }
    }

    private static BigDecimal extractPrice(String rawPrice) {
        String[] splittedPrice = rawPrice.replace(",", "").substring(1).split("\\.");
        if (splittedPrice.length == 1) {
            return new BigDecimal(splittedPrice[0]);
        }
        return new BigDecimal(splittedPrice[0] + "." + splittedPrice[1]);
    }
}
