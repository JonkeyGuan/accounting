package com.sample.split.domain.workhours;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WorkHoursItem {
    
	private String month;
	private String businessGroup;
    private String businessUnit;
	private double hours;

}
