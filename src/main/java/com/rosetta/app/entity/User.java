package com.rosetta.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//use postgres' built-in auto increment.
    @Column(name="user_id")
    private Long userId;

    @Column(name = "plan", nullable = false)
    private Integer plan;

    public User() {}//no-arg constructor is required.

    public User(Integer plan)//no need to pass pk in constructor.
    {
        this.plan = plan;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Integer getPlan()
    {
        return plan;
    }

    public void setPlan(Integer plan)
    {
        this.plan = plan;
    }
}
