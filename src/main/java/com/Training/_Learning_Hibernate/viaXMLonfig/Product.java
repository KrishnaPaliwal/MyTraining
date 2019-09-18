package com.myTraining._Learning_Hibernate.viaXMLonfig;

public class Product implements java.io.Serializable{
	 
	private static final long serialVersionUID = 1L;
 
	private int productId;
	private String proName;
	private double price;
 
	public void setProductId(int productId)
	{
	    this.productId = productId;
	}
	public int getProductId()
	{
	    return productId;
	}
 
	public void setProName(String proName)
	{
	    this.proName = proName;
	}
	public String getProName()
	{
	    return proName;
	}
 
	public void setPrice(double price)
	{
	    this.price = price;
	}
	public double getPrice()
	{
	    return price;
	}
}