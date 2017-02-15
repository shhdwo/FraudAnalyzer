package com.capgemini.fraudanalyzer;

import java.math.BigDecimal;
import java.util.*;

public class Conditions {
	public static boolean check(List<Transaction> examined, int limit, int userId, BigDecimal amountLimit, String date) {
		int counter = 0;
		for (Transaction e : examined) {
			if (e.getUserId() == userId && e.getAmount().compareTo(amountLimit) == 1 && e.getDate() == date) counter++;
		}
		return counter > limit ? true : false;
	}
	
	public static boolean check(List<Transaction> examined, int limit, int userId, String to, String date) {
		int counter = 0;
		for (Transaction e : examined) {
			if (e.getUserId() == userId && e.getTo() == to && e.getDate() == date) counter++;
		}
		return counter > limit ? true : false;
	}
	
	public static boolean check(List<Transaction> examined, int limit, String from, String date) {
		int counter = 0;
		for (Transaction e : examined) {
			if (e.getFrom() == from && e.getDate() == date) counter++;
		}
		return counter > limit ? true : false;
	}
}
