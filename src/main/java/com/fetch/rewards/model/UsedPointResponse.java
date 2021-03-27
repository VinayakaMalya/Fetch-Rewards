package com.fetch.rewards.model;

public class UsedPointResponse 
{
	private String payer;
	private Integer points;
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
}
