package erlikh.yaroslav.jwt.client


import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

class TechTokenHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private JwtClient jwtClient

    TechTokenHttpRequestInterceptor(JwtClient jwtClient) {
        this.jwtClient = jwtClient
    }

    @Override
    ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) {
        request.headers.set(HttpHeaders.AUTHORIZATION, "Bearer ${jwtClient.techToken().token()}")
        return execution.execute(request, body)
    }
}
