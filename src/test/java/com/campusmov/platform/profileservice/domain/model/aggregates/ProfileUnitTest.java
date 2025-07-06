package com.campusmov.platform.profileservice.domain.model.aggregates;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Profile;
import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateFavoriteStopCommand;
import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateProfileCommand;
import com.campusmov.platform.profileservice.profile.domain.model.commands.UpdateProfileCommand;
import com.campusmov.platform.profileservice.profile.domain.model.events.ProfileCreatedEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

class ProfileUnitTest {

    private CreateProfileCommand createValidCreateProfileCommand() {
        return new CreateProfileCommand(
                "user-test-123", "test@uni.edu", "test@gmail.com",
                "+51", "987654321", "John", "Doe",
                LocalDate.parse("2000-01-01"), "MALE", "http://example.com/pic.jpg",
                "Test University", "Engineering", "CS", "5th", Optional.empty()
        );
    }

    @Test
    @DisplayName("Constructor with CreateProfileCommand should set all fields correctly")
    void TestConstructor_WithValidCommand_ShouldCreateProfile() {
        // Given
        var command = createValidCreateProfileCommand();

        // When
        var profile = new Profile(command);

        // Then
        Assertions.assertThat(profile.getId().id()).isEqualTo(command.userId());
        Assertions.assertThat(profile.getPersonalInformation().firstName()).isEqualTo("John");
        Assertions.assertThat(profile.getPersonalInformation().lastName()).isEqualTo("Doe");
        Assertions.assertThat(profile.getContactInformation().email().institutionalEmailAddress()).isEqualTo("test@uni.edu");
        Assertions.assertThat(profile.getAcademicInformation().getUniversity()).isEqualTo("Test University");
    }

    @Test
    @DisplayName("UpdateProfile method should change profile information")
    void TestUpdateProfile_WithValidCommand_ShouldUpdateAllInfo() {
        // Given
        var originalCommand = createValidCreateProfileCommand();
        var profile = new Profile(originalCommand);

        var updateCommand = new UpdateProfileCommand(
                "update@uni.edu", "update@gmail.com", "+1", "123456789",
                "Jane", "Smith", LocalDate.parse("1999-12-12"), "FEMALE",
                "http://example.com/new-pic.jpg", "Updated University",
                "Updated Faculty", "Updated Program", "8th", Optional.of(new ArrayList<>())
        );

        // When
        profile.updateProfile(updateCommand);

        // Then
        Assertions.assertThat(profile.getPersonalInformation().firstName()).isEqualTo("Jane");
        Assertions.assertThat(profile.getPersonalInformation().lastName()).isEqualTo("Smith");
        Assertions.assertThat(profile.getContactInformation().email().personalEmailAddress()).isEqualTo("update@gmail.com");
        Assertions.assertThat(profile.getAcademicInformation().getSemester()).isEqualTo("8th");
    }

    @Test
    @DisplayName("AddFavoriteStop should add a stop to the list")
    void TestAddFavoriteStop_ShouldIncreaseListSize() {
        // Given
        var profile = new Profile(createValidCreateProfileCommand());
        Assertions.assertThat(profile.getFavoriteStops()).isEmpty();

        var createStopCommand = new CreateFavoriteStopCommand(
                "Casa", "Mi casa", "Paradero X", -12.0, -77.0, "Av. Central 123"
        );

        // When
        profile.addFavoriteStop(createStopCommand);

        // Then
        Assertions.assertThat(profile.getFavoriteStops()).hasSize(1);
        Assertions.assertThat(profile.getFavoriteStops().get(0).getName()).isEqualTo("Casa");
    }
}
