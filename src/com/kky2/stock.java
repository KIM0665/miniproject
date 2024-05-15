package com.kky2;


public class stock {
	double  rate;// 자산내 비중
	String  company;
	String  share; // 전체 지분	율
	String 	Evaluation;
	public stock( String company, double rate,String share, String evaluation) {
		super();
		this.rate = rate;
		this.company = company;
		this.share = share;
		Evaluation = evaluation;
	}

	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getShare() {
		return share;
	}
	public void setShare(String share) {
		this.share = share;
	}
	public String getEvaluation() {
		return Evaluation;
	}
	public void setEvaluation(String evaluation) {
		Evaluation = evaluation;
	} 
	

}
