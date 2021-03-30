package com.fetch.rewards.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fetch.rewards.model.Transaction;
import com.fetch.rewards.model.UsedPointResponse;
import com.fetch.rewards.utilties.TransactionDateComparator;

@Service
public class TransactionServiceImpl implements TransactionService
{
	private List<Transaction> transactionsList = new ArrayList<>();
	
	private Integer balance = 0;
	
	private Map<String,Integer> payerList = new HashMap<>();
	
	private Map<String,Integer> spentPoints = new HashMap<>();
	
	@Override
	public String addTransaction(Transaction transaction) 
	{
		
			if(payerList.get(transaction.getPayer()) != null && (payerList.get(transaction.getPayer())-transaction.getPoints()<=0))
				// Invalid transaction - Payer exits and subtracting the transaction point leads to negative point value
				return null;
			else if(payerList.get(transaction.getPayer()) != null && payerList.get(transaction.getPayer())-transaction.getPoints()>0)
			{
				payerList.put(transaction.getPayer(), payerList.get(transaction.getPayer()) + transaction.getPoints());
				balance = balance + transaction.getPoints();
				transactionsList.add(transaction);
				Collections.sort(transactionsList, new TransactionDateComparator());
				for(Iterator<Transaction> transactions = transactionsList.iterator(); transactions.hasNext();)
				{
					Transaction t = transactions.next();
					if(t.getPayer().equals(transaction.getPayer()))
					{
						int points = t.getPoints() + transaction.getPoints();
						if(points<=0)
						{
							continue;
						}
						else if(transaction.getTimestamp().before(t.getTimestamp()) && t.getPoints()<0)
						{
							transactions.remove();
							int index = transactionsList.indexOf(transaction);
							transaction.setPoints(points);
							transactionsList.set(index, transaction);
							break;
						}
					}
				}	
			}
			else
			{
				balance = balance + transaction.getPoints();
				transactionsList.add(transaction);
				Collections.sort(transactionsList, new TransactionDateComparator());
				payerList.put(transaction.getPayer(), transaction.getPoints());
			}
			return "Points added Successfully";
	}

	@Override
	public List<Transaction> getTransactions() 
	{
		return transactionsList;
	}

	@Override
	public List<UsedPointResponse> spendPoints(Integer points) 
	{
		
		if(balance<points)
		{
			// Insufficient funds;
			System.out.println("Insufficient funds");
			return null;
		}
		else
		{
			for(Transaction transcation : transactionsList)
			{
				// If Payer in the spentpoint HashMap
				if(spentPoints.get(transcation.getPayer())!=null)
				{
					// SpendPoint is less than transaction point
					if(points<transcation.getPoints())
					{
						spentPoints.put(transcation.getPayer(), spentPoints.get(transcation.getPayer()) + points);
						break;
					}
					else
					{
						points = points - transcation.getPoints();
						spentPoints.put(transcation.getPayer(), spentPoints.get(transcation.getPayer()) + transcation.getPoints());
					}		
				}
				else
				{
					if(points<transcation.getPoints())
					{
						spentPoints.put(transcation.getPayer(), points);
						points=  transcation.getPoints() - points;
						break;
					}
					else
					{
						points = points - transcation.getPoints();
						spentPoints.put(transcation.getPayer(),transcation.getPoints());
					}
				}
			}
			List<UsedPointResponse> responseList = new ArrayList<>();
			for(String key : spentPoints.keySet())
			{
				// Creating a spent points response for each payer
				UsedPointResponse response = new UsedPointResponse();
				response.setPayer(key);
				response.setPoints(-spentPoints.get(key));
				responseList.add(response);
				
				//Updating the payerlist after spending 
				payerList.put(key, payerList.get(key) - spentPoints.get(key));
				
				//Updating the balance after spending 
				balance = balance - spentPoints.get(key);
			}
			spentPoints.clear();
			return responseList; 
		}
	}

	@Override
	public Map<String, Integer> getBalancePoints() 
	{
		return payerList;
	}	
}
