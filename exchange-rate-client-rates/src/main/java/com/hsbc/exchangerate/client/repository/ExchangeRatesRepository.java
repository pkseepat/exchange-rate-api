package com.hsbc.exchangerate.client.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hsbc.exchangerate.persistence.entity.ExchangeRatesEntity;


public interface ExchangeRatesRepository extends CrudRepository<ExchangeRatesEntity, Long> {

    @Query(value="SELECT r FROM ExchangeRatesEntity r WHERE r.currencyCode= ?1 and r.exchangeRateDate= ?2")
    ExchangeRatesEntity findByCurrencyCodeAndExchangeRateDate(String currencyCode, Date exchangeRateDate);

    ExchangeRatesEntity save(ExchangeRatesEntity exchangeRatesEntity);

    @Query(value="SELECT t FROM ExchangeRatesEntity t WHERE t.base = ?1 and t.currencyCode = ?2")
    ExchangeRatesEntity findByBaseAndCurrencyCode(String base, String currencyCode);

    List<ExchangeRatesEntity> findALLByExchangeRateDateBetweenAndCurrencyCode(Date fromExchangeRateDate, Date toExchangeRateDate, String currencyCode);

    List<ExchangeRatesEntity> findALLByCurrencyCode(String currencyCode);
}
