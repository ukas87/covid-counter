package io.javaexample.coronavirustracker.services;

import io.javaexample.coronavirustracker.models.LocationStatistics;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


@Service
public class CoronaVirusDataService {

    public static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private  List<LocationStatistics> allStatistics = new ArrayList<>();

    public List<LocationStatistics> getAllStatistics() {
        return allStatistics;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStatistics> newStats = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
       // System.out.println(httpResponse.body());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStatistics locationStatistics = new LocationStatistics();
            locationStatistics.setState(record.get("Province/State"));
            locationStatistics.setCountry(record.get("Country/Region"));
            locationStatistics.setLatestTotalCase(Integer.parseInt(record.get(record.size() - 1)));
            System.out.println(locationStatistics);
            newStats.add(locationStatistics);
        }
        this.allStatistics = newStats;
    }
}
