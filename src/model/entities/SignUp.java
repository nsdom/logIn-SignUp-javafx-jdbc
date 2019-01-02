package model.entities;

import java.io.Serializable;
import java.util.Date;

public class SignUp implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private Integer phoneNumber;
	private Date birthDate;
	private Integer age;
	private String gender;
	
	private LogIn logIn;
	
	public SignUp() {
	}



	public SignUp(Integer id, String firstName, String lastName, String email, Integer phoneNumber, Date birthDate,
			Integer age, String gender, LogIn logIn) {

		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
		this.age = age;
		this.gender = gender;
		this.logIn = logIn;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
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



	public void setLastName(String lasttName) {
		this.lastName = lasttName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Integer getPhoneNumber() {
		return phoneNumber;
	}



	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public Date getBirthDate() {
		return birthDate;
	}



	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}



	public Integer getAge() {
		return age;
	}



	public void setAge(Integer age) {
		this.age = age;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public LogIn getLogIn() {
		return logIn;
	}



	public void setLogIn(LogIn logIn) {
		this.logIn = logIn;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SignUp other = (SignUp) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "SignUp [id=" + id + ", firstName=" + firstName + ", lasttName=" + lastName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", birthDate=" + birthDate + ", age=" + age + ", gender=" + gender
				+ ", logIn=" + logIn + "]";
	}

	
}
