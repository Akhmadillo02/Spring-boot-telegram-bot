package uz.ahmadillo.springboottelegramboot.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.ahmadillo.springboottelegramboot.exception.ServiceException;

import java.io.IOException;

@Component
public class CbrClient {

    private final OkHttpClient okHttpClient;

    public CbrClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }


    @Value("${cbr.currency.rates.xml.url}")
    private String url ;

    public String getCurrencyRatesXMl() throws  ServiceException {
        var request  = new Request.Builder()
                .url(url)
                .build();

        try (var response = okHttpClient.newCall(request).execute()) {
            var body= response.body();
            return body == null ? null : body.string();

        } catch (IOException e) {
            throw new ServiceException("kursda hatolik bor", e);
        }
    }
}
