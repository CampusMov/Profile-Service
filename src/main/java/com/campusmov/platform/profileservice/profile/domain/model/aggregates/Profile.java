package com.campusmov.platform.profileservice.profile.domain.model.aggregates;

import com.campusmov.platform.profileservice.profile.domain.model.commands.*;
import com.campusmov.platform.profileservice.profile.domain.model.entities.ClassSchedule;
import com.campusmov.platform.profileservice.profile.domain.model.entities.FavoriteStop;
import com.campusmov.platform.profileservice.profile.domain.model.events.ProfileCreatedEvent;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
public class Profile extends AbstractAggregateRoot<Profile> {
    @EmbeddedId
    private UserId id;

    @Embedded
    private ContactInformation contactInformation;

    @Embedded
    private PersonalInformation personalInformation;

    private String profilePictureUrl;

    @Embedded
    private AcademicInformation academicInformation;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private List<FavoriteStop> favoriteStops = new ArrayList<>();

    public Profile() {
        super();
    }

    public Profile(CreateProfileCommand cmd) {
        this.id = new UserId(cmd.userId());
        this.contactInformation = new ContactInformation(
                new Email(cmd.institutionalEmailAddress(), cmd.personalEmailAddress()),
                new PhoneNumber(cmd.countryCode(), cmd.phoneNumber())
        );
        this.personalInformation = new PersonalInformation(
                cmd.firstName(),
                cmd.lastName(),
                cmd.birthDate(),
                EGender.valueOf(cmd.gender().toUpperCase())
        );
        this.profilePictureUrl = cmd.profilePictureUrl();
        this.academicInformation = new AcademicInformation(
                cmd.university(),
                cmd.faculty(),
                cmd.academicProgram(),
                cmd.semester()
        );
        if (cmd.classSchedules().isPresent()) {
            this.academicInformation.setClassSchedules(cmd.classSchedules().get());
        }
    }

    public static Profile from(CreateProfileCommand cmd) {
        return new Profile(cmd);
    }

    private void updateContactInformation(UpdateProfileCommand command) {
        this.contactInformation = new ContactInformation(
                new Email(command.institutionalEmailAddress(), command.personalEmailAddress()),
                new PhoneNumber(command.countryCode(), command.phoneNumber())
        );
    }

    private void updatePersonalInformation(UpdateProfileCommand command) {
        this.personalInformation = new PersonalInformation(
                command.firstName(),
                command.lastName(),
                command.birthDate(),
                EGender.valueOf(command.gender().toUpperCase())
        );
    }

    private void updateProfilePictureUrl(UpdateProfileCommand command) {
        this.profilePictureUrl = command.profilePictureUrl();
    }

    private void updateAcademicInformation(UpdateProfileCommand command) {
        this.academicInformation = new AcademicInformation(
                command.university(),
                command.faculty(),
                command.academicProgram(),
                command.semester()
        );
    }

    public void updateProfile(UpdateProfileCommand command) {
        this.updateContactInformation(command);
        this.updatePersonalInformation(command);
        this.updateProfilePictureUrl(command);
        this.updateAcademicInformation(command);
    }

    public ClassSchedule addClassScheduleToAcademicInformation(CreateClassScheduleCommand command) {
        return this.academicInformation.addClassSchedule(command);
    }

    public void addFavoriteStop(CreateFavoriteStopCommand command) {
        var favoriteStop = new FavoriteStop(command);
        this.favoriteStops.add(favoriteStop);
    }

    public Optional<ClassSchedule> updateClassSchedule(String classScheduleId, UpdateClassScheduleCommand command) {
        return this.academicInformation.updateClassSchedule(classScheduleId, command);
    }

    public Optional<FavoriteStop> updateFavoriteStop(String favoriteStopId, UpdateFavoriteStopCommand command) {
        return this.favoriteStops.stream()
                .filter(favoriteStop -> favoriteStop.getId().equals(favoriteStopId))
                .findFirst()
                .map(favoriteStop -> {
                    favoriteStop.updateFavoriteStopInfo(command);
                    return favoriteStop;
                });
    }

    public boolean removeClassScheduleByClassScheduleId(String classScheduleId) {
        return this.academicInformation.removeClassScheduleByClassScheduleId(classScheduleId);
    }

    public boolean removeFavoriteStopByFavoriteStopId(String favoriteStopId) {
        return this.favoriteStops.removeIf(favoriteStop -> favoriteStop.removeFavoriteStopByFavoriteStopId(favoriteStopId));
    }

    public void sendProfileCreatedEvent() {
        ProfileCreatedEvent event = new ProfileCreatedEvent(
                this,
                this.id.id(),
                this.contactInformation.email().institutionalEmailAddress(),
                this.contactInformation.email().personalEmailAddress(),
                this.contactInformation.phone().countryCode(),
                this.contactInformation.phone().number(),
                this.personalInformation.firstName(),
                this.personalInformation.lastName(),
                this.personalInformation.birthDate(),
                this.personalInformation.gender().name(),
                this.profilePictureUrl,
                this.academicInformation.getUniversity(),
                this.academicInformation.getFaculty(),
                this.academicInformation.getAcademicProgram(),
                this.academicInformation.getSemester()
        );
        registerEvent(event);
    }
}
