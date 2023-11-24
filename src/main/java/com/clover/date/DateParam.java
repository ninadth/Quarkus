package com.clover.date;

import java.time.LocalDate;

public class DateParam {

    private LocalDate date;

    public DateParam(String dateStr) {
        this.date = LocalDate.parse(dateStr); // You might want to use a custom date parsing logic
    }

    public LocalDate getDate() {
        return date;
    }
}