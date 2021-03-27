package com.fetch.rewards.model;

import java.util.Date;

public class Transaction
{
	private String payer;
	
	private Integer points;
	
	private Date timestamp;

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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Transaction [payer=" + payer + ", points=" + points + ", timestamp=" + timestamp + "]\n";
	}
}
