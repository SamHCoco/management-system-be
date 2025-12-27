package unit.com.samhcoco.managementsystem.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samhcoco.managementsystem.core.service.HttpService;
import com.samhcoco.managementsystem.core.service.impl.HttpServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HttpServiceImplTest {

    @Mock
    private RestClient restClient;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    RestClient.RequestHeadersUriSpec requestSpec;

    @Mock
    RestClient.RequestHeadersSpec headersSpec;

    @Mock
    RestClient.ResponseSpec responseSpec;

    private HttpService underTest;

    @BeforeEach
    public void setup() {
        underTest = new HttpServiceImpl(restClient, objectMapper);
    }

    @Test
    public void get_noRetries_happyPath() {
        final String body = "{\"http\" : \"OK\"}";


        when(restClient.get()).thenReturn(requestSpec);
        when(requestSpec.uri(anyString())).thenReturn(headersSpec);
        when(headersSpec.headers(any())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(String.class)).thenReturn(body);

        final String response  = (String) underTest.get("http://localhost:9000",
                                                            String.class,
                                                            null,
                                                            0);
        assertThat(response).isEqualTo(body);
    }

}
