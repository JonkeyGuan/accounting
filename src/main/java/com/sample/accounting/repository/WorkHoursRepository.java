package com.sample.accounting.repository;

import java.util.ArrayList;
import java.util.List;

import com.sample.accounting.domain.workhours.WorkHoursItem;

import org.springframework.stereotype.Repository;

@Repository
public class WorkHoursRepository {

    public List<WorkHoursItem> load() {
        return simulate();
    }

    private List<WorkHoursItem> simulate() {
        List<WorkHoursItem> result = new ArrayList<>();
        result.add(new WorkHoursItem("202108", "Charging", "Battery", 3));
        result.add(new WorkHoursItem("202108", "Charging", "Cable", 1));
        result.add(new WorkHoursItem("202108", "Headphone", "Headphone", 2));
        result.add(new WorkHoursItem("202108", "none-bg", "None BG", 4));
        return result;
    }

}
