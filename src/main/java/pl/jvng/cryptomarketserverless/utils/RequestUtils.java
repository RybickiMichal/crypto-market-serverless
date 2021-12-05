package pl.jvng.cryptomarketserverless.utils;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import pl.jvng.cryptomarketserverless.model.Crypto;
import pl.jvng.cryptomarketserverless.model.CryptoPriceList;

import pl.jvng.cryptomarketserverless.model.ErrorResponse;
import org.jboss.logging.Logger;
import pl.jvng.cryptomarketserverless.service.CryptoDataService;

public class RequestUtils {

  private static final Logger LOG = Logger.getLogger(CryptoDataService.class);

  static ObjectWriter writer = new ObjectMapper().writerFor(ErrorResponse.class);
  static ObjectReader cryptoReader = new ObjectMapper().readerFor(Crypto.class);
  static ObjectWriter cryptoWriter = new ObjectMapper().writerFor(Crypto.class);
  static ObjectWriter cryptoPriceListWriter = new ObjectMapper().writerFor(CryptoPriceList.class);

  public static APIGatewayProxyResponseEvent errorResponse(int errorCode, String message) {
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    try {
      return response.withStatusCode(errorCode).withBody(writer.writeValueAsString(new ErrorResponse(message, errorCode)));
    } catch (JsonProcessingException exception) {
      LOG.error(exception);
      return response.withStatusCode(500).withBody("Internal error");
    }
  }

  public static APIGatewayProxyResponseEvent successResponse(CryptoPriceList cryptoPriceList) {
    try {
      return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(cryptoPriceListWriter.writeValueAsString(cryptoPriceList));
    } catch (JsonProcessingException exception) {
      LOG.error(exception);
      return errorResponse(500, "Unable to write Crypto List to JSON");
    }
  }
}
