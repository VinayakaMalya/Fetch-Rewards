package com.fetch.rewards.service;

import java.util.List;
import java.util.Map;

import com.fetch.rewards.model.UsedPointResponse;
import com.fetch.rewards.model.Transaction;

public interface TransactionService {

	String addTransaction(Transaction transaction);

	List<Transaction> getTransactions();

	List<UsedPointResponse> spendPoints(Integer spendPoints);

	Map<String, Integer> getBalancePoints();

}
