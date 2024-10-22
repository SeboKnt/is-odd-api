package org.youngandhungry.odd;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

public class isOdd {
    @FunctionName("isOdd")
    public HttpResponseMessage run(
        @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
        final ExecutionContext context) {

        String query = request.getQueryParameters().get("number");
        String numberStr = request.getBody().orElse(query);

        if (numberStr == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                .body("Please pass a number on the query string or in the request body")
                .build();
        }

        if (!isNumeric(numberStr)) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                .body("Invalid number format")
                .build();
        }

        int number = Integer.parseInt(numberStr);
        boolean isOdd = (number % 2 != 0);
        return request.createResponseBuilder(HttpStatus.OK)
            .body(isOdd)
            .build();
    }

    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}