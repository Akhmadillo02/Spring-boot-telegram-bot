package uz.ahmadillo.springboottelegramboot.service;

import org.springframework.stereotype.Service;
import uz.ahmadillo.springboottelegramboot.exception.ServiceException;

public interface ExchangeRatesService {

    String getUSDExchangeRate()throws ServiceException;
    String getEURExchangeRate()throws ServiceException;
}
