package uz.ahmadillo.springboottelegramboot.impl;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import uz.ahmadillo.springboottelegramboot.client.CbrClient;
import uz.ahmadillo.springboottelegramboot.exception.ServiceException;
import uz.ahmadillo.springboottelegramboot.service.ExchangeRatesService;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private static final String USD_XPATH = "/ValCurs//Valute[@ID='R01235']/Value";
    private static final String EUR_XPATH = "/ValCurs//Valute[@ID='R01239']/Value";
    private final CbrClient client;

    public ExchangeRatesServiceImpl(CbrClient client) {
        this.client = client;
    }

    @Override
    public String getUSDExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXMl();

        return extractCurrencyValueFromXML(xml,USD_XPATH);
    }

    @Override
    public String getEURExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXMl();
        return extractCurrencyValueFromXML(xml,EUR_XPATH);
    }

    private static String extractCurrencyValueFromXML(String xml, String xpathExpression) throws ServiceException {
        var source = new InputSource(new StringReader(xml));
        try {

            var xpath = XPathFactory.newInstance().newXPath();
            var document = (Document) xpath.evaluate("/", source, XPathConstants.NODE);
            return xpath.evaluate(xpathExpression, document);

        } catch (XPathExpressionException e) {
            throw new ServiceException("topilmadi Xml ", e);
        }

    }
}
