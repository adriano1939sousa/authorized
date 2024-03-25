package br.com.adrianosousa.authorized.authorization;

import br.com.adrianosousa.authorized.transaction.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthorizationService {
    private RestClient restClient;

    public AuthorizationService(RestClient.Builder builder) {
        this.restClient = RestClient.builder().baseUrl("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc").build();
    }

    public void authorized(Transaction transaction) {
        var response = restClient.get().retrieve().toEntity(Authorization.class);
        if(response.getStatusCode().isError() || !response.getBody().isAuthorized()){
            throw new UnauthorizedTransactionException("Unauthorized");
        }

    }
}
