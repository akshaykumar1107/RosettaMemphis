package com.rosetta.app.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
        name = "api_keys",
        uniqueConstraints = @UniqueConstraint(name = "uk_api_keys_api_key", columnNames = "api_key")//@Column(name = "api_key", unique=true) not possible for @Embeddable
)
public class ApiKey
{
    @EmbeddedId
    private ApiKeyId id;

    @ManyToOne(fetch = FetchType.EAGER)//FetchType.LAZY will fetch user record only when getUser is called (use debugger). EAGER is used here since user object will be re-used elsewhere (always use EAGER if the object will be re-used after transaction).
    @MapsId("userId")//uses id.userId
    @JoinColumn(name = "user_id")//fk column of api_keys
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public ApiKey(){}

    public ApiKey(User user, String apiKey)
    {
        this.user = user;
        this.id = new ApiKeyId(user.getUserId(), apiKey);
    }

    public ApiKeyId getId()
    {
        return id;
    }

    public void setId(ApiKeyId id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
