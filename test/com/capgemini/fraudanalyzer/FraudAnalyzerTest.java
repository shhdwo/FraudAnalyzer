package com.capgemini.fraudanalyzer;

import java.math.BigDecimal;
import java.util.*;
import org.junit.Assert;
import org.junit.Test;

public class FraudAnalyzerTest {
	/**
	 * System omija uzytkownikow oznaczonych jako "immune"
	 * System wyłapuje transakcje uzytkownikow oznaczonych jako "vulnerable"
	 * System wyłapuje transakcje w przypadku wiecej niz:
	 * -  5 powtorzen dla tego samego rachunku w tym samym dniu
	 * -  4 powtorzen dla tego samego uzytkownika, nadawcy i daty
	 * -  3 powtorzen dla tego samego uzytkowanika i daty przy kwocie powyzej 5000
	 * -  2 powtorzen dla tego samego uzytkowanika i daty przy kwocie powyzej 10000
	 */
	
	@Test
	public void shouldReturnTrueWhenUserIsVulnerable () {
		//given
		FraudAnalyzer analyzer = new FraudAnalyzer();
		Transaction trans = new Transaction(1, 542, BigDecimal.valueOf(2000), "ABC", "XYZ", "2016/11/11");
		//when
		boolean condition = analyzer.isUserVulnerable(trans.getUserId());
		//then
		Assert.assertTrue(condition);
	}
	
	@Test
	public void shouldReturnTrueWhenUserIsImmune () {
		//given
		FraudAnalyzer analyzer = new FraudAnalyzer();
		Transaction trans = new Transaction(2, 606, BigDecimal.valueOf(2000), "ABC", "XYZ", "2016/11/11");
		//when
		boolean condition = analyzer.isUserImmune(trans.getUserId());
		//then
		Assert.assertTrue(condition);
	}
	
	@Test
	public void shouldReturnVulnerableUserTransactionWhenPutIntoAnalyze () {
		//given
		FraudAnalyzer analyzer = new FraudAnalyzer();
		List<Transaction> examinedTransactions = new ArrayList<Transaction>();
		examinedTransactions.add(new Transaction(1, 542, BigDecimal.valueOf(2000), "ABC", "XYZ", "2016/11/11"));
		//when
		analyzer.analyze(examinedTransactions);
		List<Transaction> actuals = analyzer.getSuspiciousTransactions();
		List<Transaction> expecteds = examinedTransactions;
		//then
		Assert.assertEquals(expecteds, actuals);
	}
	
	@Test
	public void shouldNotReturnStandardUserSingleTransactionWhenPutIntoAnalyze () {
		//given
		FraudAnalyzer analyzer = new FraudAnalyzer();
		List<Transaction> examinedTransactions = new ArrayList<Transaction>();
		examinedTransactions.add(new Transaction(1, 666, BigDecimal.valueOf(2000), "ABC", "XYZ", "2016/11/11"));
		//when
		analyzer.analyze(examinedTransactions);
		List<Transaction> actuals = analyzer.getSuspiciousTransactions();
		List<Transaction> expecteds = examinedTransactions;
		//then
		Assert.assertNotEquals(expecteds, actuals);
	}
	
	@Test
	public void shouldNotReturnImmuneUserTransactionsWhenPutIntoAnalyze () {
		//given
		FraudAnalyzer analyzer = new FraudAnalyzer();
		List<Transaction> examinedTransactions = new ArrayList<Transaction>();
		examinedTransactions.add(new Transaction(1, 606, BigDecimal.valueOf(100000), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(2, 606, BigDecimal.valueOf(100000), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(3, 606, BigDecimal.valueOf(100000), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(4, 606, BigDecimal.valueOf(100000), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(5, 606, BigDecimal.valueOf(100000), "ABC", "XYZ", "2016/11/11"));
		//when
		analyzer.analyze(examinedTransactions);
		List<Transaction> actuals = analyzer.getSuspiciousTransactions();
		List<Transaction> expecteds = examinedTransactions;
		//then
		Assert.assertNotEquals(expecteds, actuals);
	}
	
	@Test
	public void shouldReturnExaminedTransactionsWhenPutIntoAnalyze_moreThan_4_toSameAccountBySameUserOnSameDate () {
		//given
		FraudAnalyzer analyzer = new FraudAnalyzer();
		List<Transaction> examinedTransactions = new ArrayList<Transaction>();
		examinedTransactions.add(new Transaction(1, 555, BigDecimal.valueOf(500), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(2, 555, BigDecimal.valueOf(500), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(3, 555, BigDecimal.valueOf(500), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(4, 555, BigDecimal.valueOf(500), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(5, 555, BigDecimal.valueOf(500), "ABC", "XYZ", "2016/11/11"));
		//when
		analyzer.analyze(examinedTransactions);
		List<Transaction> actuals = analyzer.getSuspiciousTransactions();
		List<Transaction> expecteds = examinedTransactions;
		//then
		Assert.assertEquals(expecteds, actuals);
	}
	
	@Test
	public void shouldReturnExaminedTransactionsWhenPutIntoAnalyze_moreThan_5_fromSameAccountOnSameDate () {
		//given
		FraudAnalyzer analyzer = new FraudAnalyzer();
		List<Transaction> examinedTransactions = new ArrayList<Transaction>();
		examinedTransactions.add(new Transaction(1, 555, BigDecimal.valueOf(500), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(2, 555, BigDecimal.valueOf(500), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(3, 555, BigDecimal.valueOf(500), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(4, 555, BigDecimal.valueOf(500), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(5, 555, BigDecimal.valueOf(500), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(6, 555, BigDecimal.valueOf(500), "ABC", "XYZ", "2016/11/11"));
		//when
		analyzer.analyze(examinedTransactions);
		List<Transaction> actuals = analyzer.getSuspiciousTransactions();
		List<Transaction> expecteds = examinedTransactions;
		//then
		Assert.assertEquals(expecteds, actuals);
	}
	
	@Test
	public void shouldReturnExaminedTransactionsWhenPutIntoAnalyze_moreThan_2_fromSameUserWithAmountOver_10000 () {
		//given
		FraudAnalyzer analyzer = new FraudAnalyzer();
		List<Transaction> examinedTransactions = new ArrayList<Transaction>();
		examinedTransactions.add(new Transaction(1, 555, BigDecimal.valueOf(10001), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(2, 555, BigDecimal.valueOf(10001), "ABCD", "XYZZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(3, 555, BigDecimal.valueOf(10001), "ABCDE", "XYZZZ", "2016/11/11"));
		//when
		analyzer.analyze(examinedTransactions);
		List<Transaction> actuals = analyzer.getSuspiciousTransactions();
		List<Transaction> expecteds = examinedTransactions;
		//then
		Assert.assertEquals(expecteds, actuals);
	}
	
	@Test
	public void shouldReturnExaminedTransactionsWhenPutIntoAnalyze_moreThan_3_fromSameUserWithAmountOver_5000 () {
		//given
		FraudAnalyzer analyzer = new FraudAnalyzer();
		List<Transaction> examinedTransactions = new ArrayList<Transaction>();
		examinedTransactions.add(new Transaction(1, 555, BigDecimal.valueOf(5001), "ABC", "XYZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(2, 555, BigDecimal.valueOf(5001), "ABCD", "XYZZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(3, 555, BigDecimal.valueOf(5001), "ABCDE", "XYZZZ", "2016/11/11"));
		examinedTransactions.add(new Transaction(4, 555, BigDecimal.valueOf(5001), "ABCDE", "XYZZZ", "2016/11/11"));
		//when
		analyzer.analyze(examinedTransactions);
		List<Transaction> actuals = analyzer.getSuspiciousTransactions();
		List<Transaction> expecteds = examinedTransactions;
		//then
		Assert.assertEquals(expecteds, actuals);
	}
	
	@Test
	public void shouldReturnExaminedTransactionsWhenPutIntoAnalyzeSomeDifferentSuspiciousTransactions () {
		//given
		FraudAnalyzer analyzer = new FraudAnalyzer();
		List<Transaction> examinedTransactions = new ArrayList<Transaction>();
		examinedTransactions.add(new Transaction(1, 401, BigDecimal.valueOf(200), "ABC", "XYZ", "2016/11/11")); //wiecej niz 5 z konta
		examinedTransactions.add(new Transaction(2, 402, BigDecimal.valueOf(200), "ABC", "XYZZ", "2016/11/11")); //wiecej niz 5 z konta
		examinedTransactions.add(new Transaction(3, 403, BigDecimal.valueOf(200), "ABC", "XYZGZZ", "2016/11/11")); //wiecej niz 5 z konta
		examinedTransactions.add(new Transaction(4, 404, BigDecimal.valueOf(200), "ABC", "XYZZZ", "2016/11/11")); //wiecej niz 5 z konta
		examinedTransactions.add(new Transaction(5, 542, BigDecimal.valueOf(500), "ABCDEFGH", "XYZYZYZYZYZ", "2009/1/1")); //vulnerable
		examinedTransactions.add(new Transaction(6, 405, BigDecimal.valueOf(200), "ABC", "XYZZ", "2016/11/11")); //wiecej niz 5 z konta
		examinedTransactions.add(new Transaction(7, 700, BigDecimal.valueOf(10001), "ABCDEX", "XYZZZA", "2015/6/7")); //wiecej niz 2 powyzej 10000
		examinedTransactions.add(new Transaction(8, 700, BigDecimal.valueOf(10001), "ABCDEY", "XYZZZB", "2015/6/7")); //wiecej niz 2 powyzej 10000
		examinedTransactions.add(new Transaction(9, 700, BigDecimal.valueOf(10001), "ABCDEZ", "XYZZZC", "2015/6/7")); //wiecej niz 2 powyzej 10000
		examinedTransactions.add(new Transaction(10, 406, BigDecimal.valueOf(200), "ABC", "XYZZ", "2016/11/11")); //wiecej niz 5 z konta
		//when
		analyzer.analyze(examinedTransactions);
		List<Transaction> actuals = analyzer.getSuspiciousTransactions();
		List<Transaction> expecteds = examinedTransactions;
		//then
		Assert.assertEquals(expecteds, actuals);
	}
}
