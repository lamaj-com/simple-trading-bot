package io.bux.trade.client.trade_api.exception;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Determines whether a particular response received from Trade Rest API has an
 * error or not.
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateErrorHandler.class);
	private static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
		return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
				|| httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
	}

	@Override
	public void handleError(URI url, HttpMethod method, ClientHttpResponse httpResponse) throws IOException {
		if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
			throw new HttpClientErrorException(httpResponse.getStatusCode());
		} else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
			if (httpResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
				String responseAsString = IOUtils.toString(httpResponse.getBody(), StandardCharsets.UTF_8);
				RestError result = objectMapper.readValue(responseAsString, RestError.class);
				LOGGER.error("URL: {}, HttpMethod: {}, ResponseBody: {}", url, method, result.toString());
			}
		}

	}

	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {
		if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
			throw new HttpClientErrorException(httpResponse.getStatusCode());
		}
	}

}