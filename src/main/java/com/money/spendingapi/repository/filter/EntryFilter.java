package com.money.spendingapi.repository.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class EntryFilter {

    private String desciption;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate experyDateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate experyDateTo;

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public LocalDate getExperyDateFrom() {
        return experyDateFrom;
    }

    public void setExperyDateFrom(LocalDate experyDateFrom) {
        this.experyDateFrom = experyDateFrom;
    }

    public LocalDate getExperyDateTo() {
        return experyDateTo;
    }

    public void setExperyDateTo(LocalDate experyDateTo) {
        this.experyDateTo = experyDateTo;
    }
}
