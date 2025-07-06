package com.campusmov.platform.profileservice.profile.application.internal.commandservices;

import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Profile;
import com.campusmov.platform.profileservice.profile.domain.model.commands.*;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.UserId;
import com.campusmov.platform.profileservice.profile.domain.services.ProfileCommandService;
import com.campusmov.platform.profileservice.profile.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProfileCommandServiceImplUnitTest {

    @Mock
    private ProfileRepository profileRepository;

    @Captor
    private ArgumentCaptor<Profile> profileCaptor;

    private ProfileCommandService profileCommandService;

    @BeforeEach
    void setUp() {
        profileCommandService = new ProfileCommandServiceImpl(profileRepository);
    }

    private CreateProfileCommand createValidProfileCommand() {
        return new CreateProfileCommand(
                "user-123", "test@uni.edu", "test@gmail.com", "+51", "987654321",
                "John", "Doe", LocalDate.parse("2000-01-01"), "MALE", "http://pic.url",
                "Test Uni", "Test Faculty", "Test Program", "1st", Optional.empty()
        );
    }

    @Test
    @DisplayName("Handle CreateProfileCommand - When ID is new, should create and return profile")
    void testCreateProfile_whenIdIsNew_shouldSucceed() {
        // Given
        var command = createValidProfileCommand();
        Mockito.when(profileRepository.findById(any(UserId.class))).thenReturn(Optional.empty());
        Mockito.when(profileRepository.save(any(Profile.class))).thenAnswer(i -> i.getArgument(0));

        // When
        var result = profileCommandService.handle(command);

        // Then
        Assertions.assertThat(result).isPresent();
        Mockito.verify(profileRepository, Mockito.times(2)).save(profileCaptor.capture());
        Profile savedProfile = profileCaptor.getValue();
        Assertions.assertThat(savedProfile.getId().id()).isEqualTo("user-123");
    }

    @Test
    @DisplayName("Handle CreateProfileCommand - When ID already exists, should throw exception")
    void testCreateProfile_whenIdExists_shouldFail() {
        // Given
        // Corregido: Usar un comando con datos válidos para evitar que el test falle por la razón incorrecta.
        var command = createValidProfileCommand();
        Mockito.when(profileRepository.findById(any(UserId.class))).thenReturn(Optional.of(new Profile()));

        // When & Then
        var exception = assertThrows(IllegalArgumentException.class, () -> profileCommandService.handle(command));
        Assertions.assertThat(exception.getMessage()).isEqualTo("Profile with this ID already exists");
    }

    @Test
    @DisplayName("Handle UpdateProfileCommand - When profile exists, should update and return profile")
    void testUpdateProfile_whenProfileExists_shouldSucceed() {
        // Given
        var command = new UpdateProfileCommand(
                "test.new@uni.edu", "test.new@gmail.com", "+1", "111222333",
                "Jane", "Doe", LocalDate.parse("2001-02-02"), "FEMALE", "http://new.pic.url",
                "New Uni", "New Faculty", "New Program", "2nd", Optional.empty()
        );
        var existingProfile = new Profile(createValidProfileCommand());
        Mockito.when(profileRepository.findById(any(UserId.class))).thenReturn(Optional.of(existingProfile));
        Mockito.when(profileRepository.save(any(Profile.class))).thenAnswer(i -> i.getArgument(0));

        // When
        var result = profileCommandService.handle(command, "user-123");

        // Then
        Assertions.assertThat(result).isPresent();
        Mockito.verify(profileRepository).save(profileCaptor.capture());
        Profile updatedProfile = profileCaptor.getValue();
        Assertions.assertThat(updatedProfile.getPersonalInformation().firstName()).isEqualTo("Jane");
    }

    @Test
    @DisplayName("Handle CreateClassScheduleCommand - When profile exists, should add schedule")
    void testCreateClassSchedule_whenProfileExists_shouldSucceed() {
        // Given
        var command = new CreateClassScheduleCommand("Math", "Room 101",0.0, 0.0,"Addr",
                LocalTime.of(8,0), LocalTime.of(10,0), "MONDAY");
        var existingProfile = new Profile();
        Mockito.when(profileRepository.findById(any(UserId.class))).thenReturn(Optional.of(existingProfile));

        // When
        var result = profileCommandService.handle(command, "user-123");

        // Then
        Assertions.assertThat(result).isPresent();
    }
}