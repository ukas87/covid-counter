package io.javaexample.coronavirustracker.controlletrs;


import io.javaexample.coronavirustracker.models.LocationStatistics;
import io.javaexample.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {


    @Autowired
    CoronaVirusDataService coronaVirusDataService;


    @GetMapping("/")
    public String home(Model model) {
        List<LocationStatistics> allStats = coronaVirusDataService.getAllStatistics();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCase()).sum();
        model.addAttribute("locationStatistics", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);

        return "home";
    }
}
