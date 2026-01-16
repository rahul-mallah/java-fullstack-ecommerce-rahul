package com.social.media.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "socialUser", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private SocialProfile socialProfile;

    @OneToMany(mappedBy = "socialUser")
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")

    )
    private Set<SocialGroup> socialGroups = new HashSet<>();

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    // this custom setter does not seem to work
    public void setSocialProfile(SocialProfile socialProfile) {
        if (this.socialProfile == socialProfile) return; // Break recursion
        this.socialProfile = socialProfile;
        if (socialProfile != null) {
            socialProfile.setSocialUser(this);
        }
    }

    @PrePersist
    @PreUpdate
    public void syncSocialProfile(){
        if (this.socialProfile != null){
            this.socialProfile.setSocialUser(this);
        }
    }
}
