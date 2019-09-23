package org.welle.pojos;

public class UserValidation {

    private String username = "";

    private String password = "";

    private Long userId;
    
    private boolean authtorized = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}