package com.fetch.rewards.utilties;

import java.util.Comparator;

import com.fetch.rewards.model.Transaction;

public class TransactionDateComparator implements Comparator<Transaction>
{
	@Override
	public int compare(Transaction o1, Transaction o2)
	{
		if(o1.getTimestamp().before(o2.getTimestamp())) 
			return -1;
		else 
			return 1;
	}
}
