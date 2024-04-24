package keenan.james.nathan.muddemo;

import jakarta.persistence.*;

@Entity
@Table(name = "MUD Users")
public class User 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    @Column(nullable = false, unique = true, length = 16)
    private String username;
     
    @Column(nullable = false, length = 32)
    private String password;

	public String getUsername() { return username; }

	public void setUsername(String username) { this.username = username; }

	public String getPassword() { return password; }

	public void setPassword(String password) { this.password = password; }
}
