package com.capgemini.fraudanalyzer;

import java.math.BigDecimal;
import java.util.*;


public class FraudAnalyzer {
	
	//private List<Transaction> testedTransactions = new ArrayList<Transaction>();
	private List<Transaction> suspiciousTransactions = new ArrayList<Transaction>();
	private int[] vulnerableUsers = {542, 1052, 2103};
	private int[] immuneUsers = {101, 606};
	
	public void analyze(List<Transaction> examinedTransactions) {
		for (Transaction aTransaction : examinedTransactions) {
			if (!isUserImmune(aTransaction.getUserId())) {
				if (isUserVulnerable(aTransaction.getUserId())) {
					suspiciousTransactions.add(aTransaction);
				}
				else if (isSuspicious(aTransaction, examinedTransactions)) {
					suspiciousTransactions.add(aTransaction);
				}
		
			}
		}
		printOutSuspicious();
	}
	
	public void printOutSuspicious() {
		if (suspiciousTransactions.size() == 0) System.out.println("Nie wykryto podejrzanych transakcji");
		else {
			System.out.println("Transakcje o ponizszym ID zostaly wykryte jako podejrzane:");
			for (Transaction e : suspiciousTransactions) {
				System.out.println(e.getId());
			}
		}
	}
	
	public boolean isSuspicious(Transaction aTransaction, List<Transaction> examinedTransactions) {
		if(Conditions.check(examinedTransactions, 2, aTransaction.getUserId(), BigDecimal.valueOf(10000), aTransaction.getDate())) return true;
		if(Conditions.check(examinedTransactions, 3, aTransaction.getUserId(), BigDecimal.valueOf(5000), aTransaction.getDate())) return true;
		if(Conditions.check(examinedTransactions, 4, aTransaction.getUserId(), aTransaction.getTo(), aTransaction.getDate())) return true;
		if(Conditions.check(examinedTransactions, 5, aTransaction.getFrom(), aTransaction.getDate())) return true;
		else return false;
	}
	
	public boolean isUserImmune(int userId) {
		for (int u : immuneUsers) {
			if (userId == u) return true;
		}
		return false;
	}
	
	public boolean isUserVulnerable(int userId) {
		for (int u : vulnerableUsers) {
			if (userId == u) return true;
		}
		return false;
	}
	
	public List<Transaction> getSuspiciousTransactions() {
		return suspiciousTransactions;
	}
}
