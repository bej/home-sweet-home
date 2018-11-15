package de.derjonk.home_sweet_home.accounting;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "TransactionWithFromAndTo", types = { Transaction.class })
public interface TransactionWithFromAndToProjection {
    Integer getValue();
    Expense getTo();
    Income getFrom();
}
