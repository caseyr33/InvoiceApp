package model;
public class Product {
		private int productID;
		private String description;
		private double rate;

		public Product(int id, String desc, double r) {
			productID = id;
			description = desc;
			rate = r;
		}
		
		public int getProductID() {
			return productID;
		}

		public String getDescription() {
			return description;
		}

		public  double getRate() {
			return rate;
		}
	}