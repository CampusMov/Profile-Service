package com.campusmov.platform.profileservice.profile.interfaces.rest;

import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileClassSchedulesControllerIntegrationTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String PROFILES_URL = "/profiles";
    private static final String SCHEDULES_BASE_URL = "/profiles/{profileId}/class-schedules";
    private static final String SCHEDULE_DETAIL_URL = SCHEDULES_BASE_URL + "/{scheduleId}";
    private static final String SCHEDULE_BY_COURSE_URL = SCHEDULES_BASE_URL + "/{courseName}";

    @Nested
    @DisplayName("POST /profiles/{id}/class-schedules")
    class PostClassScheduleTests {

        @Test
        @DisplayName("Should add schedule and return 201 when profile exists")
        void whenProfileExists_shouldAddScheduleAndReturn201() {
            // Given
            ProfileResource profile = createProfile();
            CreateClassScheduleResource newSchedule = createValidScheduleResource("Math 101");

            // When
            ResponseEntity<ClassScheduleResource> response = restTemplate.postForEntity(
                    SCHEDULES_BASE_URL, newSchedule, ClassScheduleResource.class, profile.id()
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(response.getBody()).isNotNull();
            Assertions.assertThat(response.getBody().id()).isNotNull();
            Assertions.assertThat(response.getBody().courseName()).isEqualTo("Math 101");
        }

        @Test
        @DisplayName("Should return 400 when profile does not exist")
        void whenProfileDoesNotExist_shouldReturn400() {
            // Given
            String nonExistentProfileId = UUID.randomUUID().toString();
            CreateClassScheduleResource newSchedule = createValidScheduleResource("Physics 202");

            // When
            ResponseEntity<ClassScheduleResource> response = restTemplate.postForEntity(
                    SCHEDULES_BASE_URL, newSchedule, ClassScheduleResource.class, nonExistentProfileId
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }

    @Nested
    @DisplayName("GET /profiles/{id}/class-schedules")
    class GetClassScheduleTests {

        @Test
        @DisplayName("Should return list of schedules when profile has schedules")
        void whenProfileHasSchedules_shouldReturnScheduleList() {
            // Given
            ProfileResource profile = createProfile();
            addClassScheduleToProfile(profile.id(), createValidScheduleResource("Calculus"));
            addClassScheduleToProfile(profile.id(), createValidScheduleResource("Algebra"));

            // When
            ResponseEntity<List<ClassScheduleResource>> response = restTemplate.exchange(
                    SCHEDULES_BASE_URL, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {}, profile.id()
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isNotNull().hasSize(2);
        }

        @Test
        @DisplayName("Should get schedules by course name")
        void whenFilteringByCourseName_shouldReturnFilteredList() {
            // Given
            ProfileResource profile = createProfile();
            addClassScheduleToProfile(profile.id(), createValidScheduleResource("History 101"));
            addClassScheduleToProfile(profile.id(), createValidScheduleResource("Art 101"));

            // When
            ResponseEntity<List<ClassScheduleResource>> response = restTemplate.exchange(
                    SCHEDULE_BY_COURSE_URL, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {}, profile.id(), "History 101"
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isNotNull().hasSize(1);
            Assertions.assertThat(response.getBody().get(0).courseName()).isEqualTo("History 101");
        }
    }

    @Nested
    @DisplayName("PUT /profiles/{id}/class-schedules/{scheduleId}")
    class PutClassScheduleTests {

        @Test
        @DisplayName("Should update schedule and return 200 when schedule exists")
        void whenScheduleExists_shouldUpdateAndReturn200() {
            // Given
            ProfileResource profile = createProfile();
            ClassScheduleResource schedule = addClassScheduleToProfile(profile.id(), createValidScheduleResource("Chemistry"));

            UpdateClassScheduleResource updateRequest = new UpdateClassScheduleResource(
                    "Advanced Chemistry", "Lab B", -12.04, -77.02, "Av. La Ciencia 456",
                    LocalTime.of(15, 0), LocalTime.of(17, 0), "FRIDAY"
            );
            HttpEntity<UpdateClassScheduleResource> requestEntity = new HttpEntity<>(updateRequest);

            // When
            ResponseEntity<ClassScheduleResource> response = restTemplate.exchange(
                    SCHEDULE_DETAIL_URL, HttpMethod.PUT, requestEntity, ClassScheduleResource.class,
                    profile.id(), schedule.id()
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isNotNull();
            Assertions.assertThat(response.getBody().courseName()).isEqualTo("Advanced Chemistry");
            Assertions.assertThat(response.getBody().selectedDay()).isEqualTo("FRIDAY");
        }
    }

    @Nested
    @DisplayName("DELETE /profiles/{id}/class-schedules/{scheduleId}")
    class DeleteClassScheduleTests {

        @Test
        @DisplayName("Should delete schedule and return 204 when schedule exists")
        void whenScheduleExists_shouldDeleteAndReturn204() {
            // Given
            ProfileResource profile = createProfile();
            ClassScheduleResource schedule = addClassScheduleToProfile(profile.id(), createValidScheduleResource("Literature"));

            // When
            ResponseEntity<Void> response = restTemplate.exchange(
                    SCHEDULE_DETAIL_URL, HttpMethod.DELETE, null, Void.class, profile.id(), schedule.id()
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

            // Verification
            ResponseEntity<List<ClassScheduleResource>> getResponse = restTemplate.exchange(
                    SCHEDULES_BASE_URL, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {}, profile.id()
            );
            Assertions.assertThat(getResponse.getBody()).isNotNull().isEmpty();
        }
    }

    private ProfileResource createProfile() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        CreateProfileResource createRequest = new CreateProfileResource(
                "user-" + uniqueId, "user.test@uni.edu", "user.test@gmail.com",
                "+51", "999888777", "Test", "User", "2000-01-01", "MALE",
                "http://example.com/pic.jpg", "Test University", "Science",
                "Testing", "1st", Optional.empty()
        );
        ResponseEntity<ProfileResource> response = restTemplate.postForEntity(PROFILES_URL, createRequest, ProfileResource.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        return Objects.requireNonNull(response.getBody());
    }

    private CreateClassScheduleResource createValidScheduleResource(String courseName) {
        return new CreateClassScheduleResource(
                courseName, "Room 101", "Av. Principal 123", -12.0, -77.0,
                LocalTime.of(8, 0), LocalTime.of(10, 0), "MONDAY"
        );
    }

    private ClassScheduleResource addClassScheduleToProfile(String profileId, CreateClassScheduleResource resource) {
        ResponseEntity<ClassScheduleResource> response = restTemplate.postForEntity(
                SCHEDULES_BASE_URL, resource, ClassScheduleResource.class, profileId
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        return Objects.requireNonNull(response.getBody());
    }
}