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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileFavoriteStopsControllerIntegrationTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate restTemplate;

    // --- Endpoints ---
    private static final String PROFILES_URL = "/profiles";
    private static final String STOPS_BASE_URL = "/profiles/{profileId}/favorite-stops";
    private static final String STOP_DETAIL_URL = STOPS_BASE_URL + "/{stopId}";

    // --- Tests para POST ---
    @Nested
    @DisplayName("POST /profiles/{id}/favorite-stops")
    class PostFavoriteStopTests {

        @Test
        @DisplayName("Should add favorite stop and return 201 when profile exists")
        void whenProfileExists_shouldAddStopAndReturn201() {
            // Given: Un perfil existente y válido
            ProfileResource profile = createProfile();
            CreateFavoriteStopResource newStop = createValidFavoriteStopResource("Casa", "Mi dulce hogar");

            // When
            ResponseEntity<FavoriteStopResource> response = restTemplate.postForEntity(
                    STOPS_BASE_URL, newStop, FavoriteStopResource.class, profile.id()
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(response.getBody()).isNotNull();
            Assertions.assertThat(response.getBody().id()).isNotNull();
            Assertions.assertThat(response.getBody().name()).isEqualTo("Casa");
            Assertions.assertThat(response.getBody().description()).isEqualTo("Mi dulce hogar");
        }
    }

    // --- Tests para GET ---
    @Nested
    @DisplayName("GET /profiles/{id}/favorite-stops")
    class GetFavoriteStopTests {

        @Test
        @DisplayName("Should return list of favorite stops")
        void whenProfileHasStops_shouldReturnStopList() {
            // Given: Un perfil con dos paradas favoritas
            ProfileResource profile = createProfile();
            // Corregido: Usar descripciones no vacías
            addFavoriteStopToProfile(profile.id(), createValidFavoriteStopResource("Casa", "Hogar"));
            addFavoriteStopToProfile(profile.id(), createValidFavoriteStopResource("Universidad", "Campus"));

            // When
            ResponseEntity<List<FavoriteStopResource>> response = restTemplate.exchange(
                    STOPS_BASE_URL, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {}, profile.id()
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isNotNull().hasSize(2);
        }

        @Test
        @DisplayName("Should return empty list when profile has no stops")
        void whenProfileHasNoStops_shouldReturnEmptyList() {
            // Given: Un perfil nuevo sin paradas
            ProfileResource profile = createProfile();

            // When
            ResponseEntity<List<FavoriteStopResource>> response = restTemplate.exchange(
                    STOPS_BASE_URL, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {}, profile.id()
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isNotNull().isEmpty();
        }
    }

    // --- Tests para PUT ---
    @Nested
    @DisplayName("PUT /profiles/{id}/favorite-stops/{stopId}")
    class PutFavoriteStopTests {

        @Test
        @DisplayName("Should update favorite stop and return 200")
        void whenStopExists_shouldUpdateAndReturn200() {
            // Given: Un perfil con una parada existente
            ProfileResource profile = createProfile();
            // Corregido: Usar descripción no vacía
            FavoriteStopResource stop = addFavoriteStopToProfile(profile.id(), createValidFavoriteStopResource("Trabajo", "Oficina antigua"));

            UpdateFavoriteStopResource updateRequest = new UpdateFavoriteStopResource(
                    "Oficina Central", "Edificio principal", stop.locationName(),
                    stop.locationLatitude(), stop.locationLongitude(), stop.address()
            );
            HttpEntity<UpdateFavoriteStopResource> requestEntity = new HttpEntity<>(updateRequest);

            // When
            ResponseEntity<FavoriteStopResource> response = restTemplate.exchange(
                    STOP_DETAIL_URL, HttpMethod.PUT, requestEntity, FavoriteStopResource.class,
                    profile.id(), stop.id()
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isNotNull();
            Assertions.assertThat(response.getBody().name()).isEqualTo("Oficina Central");
            Assertions.assertThat(response.getBody().description()).isEqualTo("Edificio principal");
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

    private CreateFavoriteStopResource createValidFavoriteStopResource(String name, String description) {
        Assertions.assertThat(description).isNotBlank();
        return new CreateFavoriteStopResource(
                name, description, "Estación Central",
                -12.046374, -77.042793, "Plaza 2 de Mayo, Lima"
        );
    }

    private FavoriteStopResource addFavoriteStopToProfile(String profileId, CreateFavoriteStopResource resource) {
        ResponseEntity<FavoriteStopResource> response = restTemplate.postForEntity(
                STOPS_BASE_URL, resource, FavoriteStopResource.class, profileId
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        return Objects.requireNonNull(response.getBody());
    }
}