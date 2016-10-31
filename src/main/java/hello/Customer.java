package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class Customer {

	@Id
	@GeneratedValue
	private Long id;
	private Date birthDate;

	private CustomerStatus status;

	private String email = "";

	private String firstName;

	private String lastName;

	public void setId(Long id) {
		this.id = id;
	}

	protected Customer() {
	}

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Customer(Date birthDate, CustomerStatus status, String email, String firstName, String lastName) {
		this.birthDate = birthDate;
		this.status = status;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public CustomerStatus getStatus() {
		return status;
	}

	public void setStatus(CustomerStatus status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public Customer clone() throws CloneNotSupportedException {
		return (Customer) super.clone();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Customer)) return false;
		Customer customer = (Customer) o;
		return Objects.equals(id, customer.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Customer{");
		sb.append("id=").append(id);
		sb.append(", birthDate=").append(birthDate);
		sb.append(", status=").append(status);
		sb.append(", email='").append(email).append('\'');
		sb.append(", firstName='").append(firstName).append('\'');
		sb.append(", lastName='").append(lastName).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
