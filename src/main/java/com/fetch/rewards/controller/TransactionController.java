package com.fetch.rewards.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fetch.rewards.model.UsedPointResponse;
import com.fetch.rewards.model.Transaction;
import com.fetch.rewards.service.TransactionService;
import com.fetch.rewards.utilties.ErrorResponse;
import com.fetch.rewards.utilties.InSufficientError;
import com.fetch.rewards.utilties.InvalidTransactionError;

@RestController
public class TransactionController 
{
	@Autowired 
	private TransactionService userAccountService;
	
	@PostMapping("transaction/add")
	public String addTransaction(@RequestBody Transaction transaction) {
		String msg = userAccountService.addTransaction(transaction);
		if(msg!=null)
			return msg;
		else
			throw new InvalidTransactionError("Invalid Transaction");
	}
	
	@DeleteMapping("transaction/spend")
	public List<UsedPointResponse> spendPoints(@RequestBody Transaction transaction) 
	{
		List<UsedPointResponse> res = userAccountService.spendPoints(transaction.getPoints());
		if(res!=null)
			return res;
		else
			throw new InSufficientError("Insufficient funds");
	}
	
	@GetMapping("transaction/transaction")
	public List<Transaction> getTranscations() {
		return userAccountService.getTransactions();
	}
	
	@GetMapping("transaction/balancePoints")
	public Map<String,Integer> getBalancePoints(){
		return userAccountService.getBalancePoints();
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(InvalidTransactionError exc)
	{	
		ErrorResponse error=new ErrorResponse();
		error.setStatus(HttpStatus.FORBIDDEN.value());
		error.setMessage(exc.getMessage());
		return new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(InSufficientError exc)
	{	
		ErrorResponse error=new ErrorResponse();
		error.setStatus(HttpStatus.FORBIDDEN.value());
		error.setMessage(exc.getMessage());
		return new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
	}
}
