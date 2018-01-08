package com.myTwitter.model;

public class Role {
  
    private Long id;
    private String name;

    /**
	 * default constructor
	 */
    public Role() {
    }
    
    public Role(String name) {
        this.name = name;
    }

    public Role(Long id, String name) {
    		this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}