package bg.softuni.heathy_desserts_recipes.util;


import bg.softuni.heathy_desserts_recipes.common.error.exceptions.UserNotFoundException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() ||
                response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            throw new UserNotFoundException("No such user exist");
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new HttpClientErrorException(response.getStatusCode());
        }
    }
}
