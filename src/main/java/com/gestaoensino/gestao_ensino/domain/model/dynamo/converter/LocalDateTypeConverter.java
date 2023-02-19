package com.gestaoensino.gestao_ensino.domain.model.dynamo.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.gestaoensino.gestao_ensino.domain.wrappers.utils.DateUtils;

import java.time.LocalDate;
import java.util.Date;

public class LocalDateTypeConverter implements DynamoDBTypeConverter<Date, LocalDate> {

    @Override
    public Date convert(LocalDate object) {
        return DateUtils.parseLocalDateToDate(object);
    }

    @Override
    public LocalDate unconvert(Date object) {
        return DateUtils.parseDateToLocalDate(object);
    }
}
