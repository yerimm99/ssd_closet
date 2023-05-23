package com.ssp.closet.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Auction implements Serializable {
	private int productId;
	private String userId;
	private int acutionPrice;
	private int maxPrice;
	private Date signDate;
	private int auctionResult;
	
	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public int getAcutionPrice() {
		return acutionPrice;
	}
	public void setAcutionPrice(int acutionPrice) {
		this.acutionPrice = acutionPrice;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public int getAuctionResult() {
		return auctionResult;
	}
	public void setAuctionResult(int auctionResult) {
		this.auctionResult = auctionResult;
	}
	
	public String getUserId() {
		return userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	
//	public boolean deadline() {
//		Calendar getToday = Calendar.getInstance();
//		getToday.setTime(new Date()); //금일 날짜
//		
//		Calendar cmpDate = Calendar.getInstance();
//		cmpDate.setTime(signDate); //특정 일자
//		
//		long diffSec = (getToday.getTimeInMillis() - cmpDate.getTimeInMillis()) / 1000;
//		long diffDays = diffSec / (24*60*60); //일자수 차이
//		
//		System.out.println(diffDays + "일 차이");
//		
//		if (diffDays >= auctionPeriod) {
//			System.out.println("기한만료");
//			return true;
//		}
//		else {
//			System.out.println("기한남음");
//			return false;
//		}
//	}

}