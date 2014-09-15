package de.ur.mi.android.ting.app.fragments;

public class RegisterRequest {
private String username;
private Gender gender;
private String password;
private String email;

public RegisterRequest(String username, String email, String password, Gender gender) {
	this.username = username;
	this.gender = gender;
	this.password = password;
	this.email = email;
}

public String getName() {
	
	return this.username;
}

public String getPassword() {
	return this.password;
}

public String getEmail() {
	return this.email;
}

public Gender getGender() {
	return this.gender;
}
}
