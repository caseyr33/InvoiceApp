
public class CustomerInfo {

	private String name, phone, email, address, city, state, zip;

	public CustomerInfo(String name, String phone, String email, String address, String city, String state,
			String zip) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public String getName() {
		return this.name;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getEmail() {
		return this.email;
	}

	public String getAddress() {
		return this.address;
	}

	public String getCity(){
		return this.city;
	}

	public String getState() {
		return this.state;
	}

	public String getZIP() {
		return this.zip;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city){
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZIP(String zip) {
		this.zip = zip;
	}
}
