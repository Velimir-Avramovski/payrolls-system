package org.welle.pojos;

public class UserValidation {

    private String name = "";

    private String password = "";
    
    private boolean authtorized = false;

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Boolean getAuthtorized() {
		return authtorized;
	}

	public void setAuthtorized(Boolean authtorized) {
		this.authtorized = authtorized;
	}
}