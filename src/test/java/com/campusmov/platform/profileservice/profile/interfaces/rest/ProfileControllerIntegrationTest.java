package com.campusmov.platform.profileservice.profile.interfaces.rest;

import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.CreateProfileResource;
import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.ProfileResource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String PROFILES_ENDPOINT = "/profiles";
    private static final String PROFILE_BY_ID_ENDPOINT = "/profiles/{id}";

    @BeforeEach
    void setUp() {
    }
    @Nested
    @DisplayName("GET /profiles/{id}")
    class GetProfileTests {

        @Test
        @DisplayName("Should return 200 and profile when profile exists")
        void whenProfileExists_thenReturn200AndProfile() {
            // Given
            CreateProfileResource createRequest = createValidProfileResource();
            String profileId = createProfile(createRequest);

            // When
            ResponseEntity<ProfileResource> response = restTemplate.getForEntity(
                    PROFILE_BY_ID_ENDPOINT, ProfileResource.class, profileId
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isNotNull();

            ProfileResource profile = response.getBody();
            Assertions.assertThat(profile.id()).isEqualTo(profileId);
            Assertions.assertThat(profile.firstName()).isEqualTo("John");
            Assertions.assertThat(profile.lastName()).isEqualTo("Doe");
            Assertions.assertThat(profile.personalEmailAddress()).isEqualTo("john.doe@gmail.com");
            Assertions.assertThat(profile.institutionalEmailAddress()).isEqualTo("john.doe@uni.edu");
        }

        @Test
        @DisplayName("Should return 404 when profile does not exist")
        void whenProfileDoesNotExist_thenReturn404() {
            // Given
            String nonExistentId = UUID.randomUUID().toString();

            // When
            ResponseEntity<ProfileResource> response = restTemplate.getForEntity(
                    PROFILE_BY_ID_ENDPOINT, ProfileResource.class, nonExistentId
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Nested
    @DisplayName("POST /profiles")
    class CreateProfileTests {

        @Test
        @DisplayName("Should return 201 and created profile when request is valid")
        void whenValidCreateRequest_thenReturn201AndCreatedProfile() {
            // Given
            CreateProfileResource createRequest = createValidProfileResource();

            // When
            ResponseEntity<ProfileResource> response = restTemplate.postForEntity(
                    PROFILES_ENDPOINT, createRequest, ProfileResource.class
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(response.getBody()).isNotNull();

            ProfileResource createdProfile = response.getBody();
            Assertions.assertThat(createdProfile.id()).isNotNull();
            Assertions.assertThat(createdProfile.firstName()).isEqualTo("John");
            Assertions.assertThat(createdProfile.lastName()).isEqualTo("Doe");
            Assertions.assertThat(createdProfile.personalEmailAddress()).isEqualTo("john.doe@gmail.com");
        }
    }

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

    private String createProfile(CreateProfileResource createRequest) {
        ResponseEntity<ProfileResource> response = restTemplate.postForEntity(
                PROFILES_ENDPOINT, createRequest, ProfileResource.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();

        return Objects.requireNonNull(response.getBody()).id();
    }
}