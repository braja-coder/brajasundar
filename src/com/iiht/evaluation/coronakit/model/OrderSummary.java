package com.iiht.evaluation.coronakit.model;

import java.util.List;

public class OrderSummary {
	private CovidKit coronaKit;
	private List<KitDetail> kitDetails;
	
	public OrderSummary() {
		// TODO Auto-generated constructor stub
	}
	
	public OrderSummary(CovidKit coronaKit, List<KitDetail> kitDetails) {
		
		this.coronaKit = coronaKit;
		this.kitDetails = kitDetails;
	}
	public CovidKit getCoronaKit() {
		return coronaKit;
	}
	public void setCoronaKit(CovidKit coronaKit) {
		this.coronaKit = coronaKit;
	}
	public List<KitDetail> getKitDetails() {
		return kitDetails;
	}
	public void setKitDetails(List<KitDetail> kitDetails) {
		this.kitDetails = kitDetails;
	}
	
	
}
