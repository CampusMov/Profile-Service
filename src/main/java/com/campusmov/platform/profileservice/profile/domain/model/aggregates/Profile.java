package com.campusmov.platform.profileservice.profile.domain.model.aggregates;
import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateProfileCommand;
import com.campusmov.platform.profileservice.profile.domain.model.entities.FavoriteStop;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Profile extends AbstractAggregateRoot<Profile> {
    @EmbeddedId
    private UserId id;

    @Embedded
    private ContactInformation contactInformation;

    @Embedded
    private  PersonalInformation personalInformation;

    private String profilePictureUrl;

    @Embedded
    private AcademicInformation academicInformation;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private List<FavoriteStop> favoriteStops = new ArrayList<>();

    public Profile() {
        //Constructor por defecto para JPA
    }

}
