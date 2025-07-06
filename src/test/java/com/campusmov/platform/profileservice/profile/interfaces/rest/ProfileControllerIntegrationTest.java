package com.campusmov.platform.profileservice.profile.interfaces.rest;

import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.ClassScheduleResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.CreateProfileResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.ProfileResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.UpdateProfileResource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@DisplayName("Profile Controller Integration Flow")
class ProfileControllerIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate restTemplate;

    private String createdProfileId;

    private static final String PROFILES_ENDPOINT = "/profiles";
    private static final String PROFILE_BY_ID_ENDPOINT = "/profiles/{id}";


    @Nested
    @Order(1)
    @DisplayName("POST /profiles")
    class CreateProfileTests {
        @Test
        @DisplayName("Should return 201 and created profile")
        void shouldCreateProfile() {
            CreateProfileResource createRequest = createValidProfileResource();
            ResponseEntity<ProfileResource> response = restTemplate.postForEntity(PROFILES_ENDPOINT, createRequest, ProfileResource.class);

            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            ProfileResource createdProfile = response.getBody();
            Assertions.assertThat(createdProfile).isNotNull();
            createdProfileId = createdProfile.id();
            Assertions.assertThat(createdProfileId).isNotNull();
        }
    }

    @Nested
    @Order(2)
    @DisplayName("PUT /profiles/{id}")
    class UpdateProfileTests {
        @Test
        @DisplayName("Should update the existing profile")
        void shouldUpdateProfile() {
            Assertions.assertThat(createdProfileId).isNotNull();

            UpdateProfileResource updateRequest = createValidUpdateProfileResource();
            HttpEntity<UpdateProfileResource> requestEntity = new HttpEntity<>(updateRequest);
            ResponseEntity<ProfileResource> response = restTemplate.exchange(
                    PROFILE_BY_ID_ENDPOINT, HttpMethod.PUT, requestEntity, ProfileResource.class, createdProfileId
            );

            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            ProfileResource updatedProfile = response.getBody();
            Assertions.assertThat(updatedProfile).isNotNull();
            Assertions.assertThat(updatedProfile.firstName()).isEqualTo("Jane");
        }
    }

    @Nested
    @Order(3)
    @DisplayName("GET /profiles/{id}")
    class GetProfileTests {
        @Test
        @DisplayName("Should return 200 and the UPDATED profile")
        void shouldReturnUpdatedProfile() {
            Assertions.assertThat(createdProfileId).isNotNull();

            ResponseEntity<ProfileResource> response = restTemplate.getForEntity(PROFILE_BY_ID_ENDPOINT, ProfileResource.class, createdProfileId);
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            ProfileResource profile = response.getBody();
            Assertions.assertThat(profile).isNotNull();

            Assertions.assertThat(profile.firstName()).isEqualTo("Jane");
            Assertions.assertThat(profile.semester()).isEqualTo("6th");
            Assertions.assertThat(profile.classSchedules()).hasSize(1);
        }

        @Test
        @DisplayName("Should return 404 when profile does not exist")
        void shouldReturn404ForNonExistentProfile() {
            String nonExistentId = UUID.randomUUID().toString();
            ResponseEntity<ProfileResource> response = restTemplate.getForEntity(PROFILE_BY_ID_ENDPOINT, ProfileResource.class, nonExistentId);
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    // --- Métodos Helper (sin cambios) ---
    private CreateProfileResource createValidProfileResource() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        return CreateProfileResource.builder()
                .userId("user-" + uniqueId)
                .institutionalEmailAddress("john.doe@uni.edu")
                .personalEmailAddress("john.doe@gmail.com")
                .countryCode("+51")
                .phoneNumber("987654321")
                .firstName("John")
                .lastName("Doe")
                .birthDate("1990-01-01")
                .gender("MALE")
                .profilePictureUrl("https://example.com/profile-pictures/john-doe.jpg")
                .university("Universidad Nacional")
                .faculty("Engineering")
                .academicProgram("Computer Science")
                .semester("5th")
                .classSchedules(Optional.empty())
                .build();
    }

    private UpdateProfileResource createValidUpdateProfileResource() {
        var schedule = new ClassScheduleResource(
                null, "Advanced Algorithms", "Building A, Room 101",
                "123 University Ave, Lima, Peru", -12.0464, -77.0428,
                LocalTime.of(14, 0), LocalTime.of(16, 0), "MONDAY"
        );
        return UpdateProfileResource.builder()
                .institutionalEmailAddress("john.doe@uni.edu")
                .personalEmailAddress("jane.doe@gmail.com")
                .countryCode("+51")
                .phoneNumber("999888777")
                .firstName("Jane")
                .lastName("Doe")
                .birthDate(LocalDate.parse("1990-01-01"))
                .gender("FEMALE")
                .profilePictureUrl("https://example.com/profile-pictures/jane-doe.jpg")
                .university("Universidad Nacional del Altiplano")
                .faculty("Systems Engineering")
                .academicProgram("Software Engineering")
                .semester("6th")
                .classSchedules(List.of(schedule))
                .build();
    }
}