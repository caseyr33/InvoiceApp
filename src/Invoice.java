import java.sql.Date;

public class Invoice{
		private int invoiceID;
		private String customerID;
		private Date date;
		private double total;
		private String products;
		private boolean paidOnDelivery;
		
		public void setInvoiceID(int x) {
			invoiceID = x;
		}
		public void setCustomerID(String x) {
			customerID = x;
		}
		public void setDate(Date x) {
			date = x;
		}
		public void setTotal(double x) {
			total = x;
		}
		public void setProducts(String x) {
			products = x;
		}
		public void setPaidOnDelivery(boolean x) {
			paidOnDelivery = x;
		}
		
		public int getInvoiceID() {
			return invoiceID;
		}
		public String getCustomerID() {
			return customerID;
		}
		public Date getDate() {
			return date;
		}
		public double getTotal() {
			return total;
		}
		public String getProducts() {
			return products;
		}
		public boolean getPaidOnDelivery() {
			return paidOnDelivery;
		}
	}