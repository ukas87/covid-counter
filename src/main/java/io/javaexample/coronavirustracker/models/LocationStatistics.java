package io.javaexample.coronavirustracker.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocationStatistics {


    private String state;
    private String country;
    private int latestTotalCase;



}
