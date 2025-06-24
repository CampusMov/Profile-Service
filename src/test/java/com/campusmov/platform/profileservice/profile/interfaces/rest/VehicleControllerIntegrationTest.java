package com.campusmov.platform.profileservice.profile.interfaces.rest;

import com.campusmov.platform.profileservice.profile.interfaces.rest.resources.*;
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

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VehicleControllerIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE = "/vehicles";
    private static final String BY_OWNER = BASE + "?ownerId={ownerId}";
    private static final String BY_VEHICLE = BASE + "?vehicleId={vehicleId}";

    @BeforeEach
    void setUp() {
    }

    @Nested
    @DisplayName("GET /vehicles")
    class GetVehicleTests {
        @Test
        @DisplayName("Should return 200 and vehicle when vehicle exists by ownerId")
        void whenVehicleExistsByOwnerId_thenReturn200AndVehicle() {
            // Given
            String vin = "1HGCM82633A123135";
            String ownerId = "owner-id2";
            String license = "license3";
            CreateVehicleResource createRequest = createValidVehicleResource(
                    vin, ownerId, license
            );
            VehicleResource vehicleResource = createVehicle(createRequest);

            // When
            ResponseEntity<VehicleResource> response = restTemplate.getForEntity(
                    BY_OWNER, VehicleResource.class, vehicleResource.ownerId()
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isNotNull();

            VehicleResource vehicle = response.getBody();
            Assertions.assertThat(vehicle.id()).isEqualTo(vehicleResource.id());
            Assertions.assertThat(vehicle.ownerId()).isEqualTo(vehicleResource.ownerId());
            Assertions.assertThat(vehicle.status()).isEqualTo(vehicleResource.status());
        }

        @Test
        @DisplayName("Should return 200 and vehicle when vehicle exists by vehicleId")
        void whenVehicleExistsByVehicleId_thenReturn200AndVehicle() {
            // Given
            String vin = "1HGCM82633A123354";
            String ownerId = "owner-id3";
            String license = "license2";
            CreateVehicleResource createRequest = createValidVehicleResource(
                    vin, ownerId, license
            );
            VehicleResource vehicleResource = createVehicle(createRequest);

            // When
            ResponseEntity<VehicleResource> response = restTemplate.getForEntity(
                    BY_VEHICLE, VehicleResource.class, vehicleResource.id()
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isNotNull();

            VehicleResource vehicle = response.getBody();
            Assertions.assertThat(vehicle.id()).isEqualTo(vehicleResource.id());
            Assertions.assertThat(vehicle.ownerId()).isEqualTo(vehicleResource.ownerId());
            Assertions.assertThat(vehicle.status()).isEqualTo(vehicleResource.status());
        }
    }

    @Nested
    @DisplayName("POST /vehicles")
    class CreateVehicleTests {
        @Test
        @DisplayName("Should return 201 and created vehicle when request is valid")
        void whenValidCreateRequest_thenReturn201AndCreatedProfile() {
            // Given
            String vin = "1HGCM82633A123134";
            String ownerId = "owner-id1";
            String license = "license1";
            CreateVehicleResource createRequest = createValidVehicleResource(
                    vin, ownerId, license
            );

            // When
            ResponseEntity<VehicleResource> response = restTemplate.postForEntity(
                    BASE, createRequest, VehicleResource.class
            );

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            Assertions.assertThat(response.getBody()).isNotNull();

            VehicleResource vehicle = response.getBody();
            Assertions.assertThat(vehicle.id()).isNotNull();
            Assertions.assertThat(vehicle.brand()).isEqualTo("Toyota");
            Assertions.assertThat(vehicle.model()).isEqualTo("Corolla");
            Assertions.assertThat(vehicle.year()).isEqualTo(2023);
            Assertions.assertThat(vehicle.status()).isEqualTo("ACTIVE");
            Assertions.assertThat(vehicle.vin()).isEqualTo(vin);
            Assertions.assertThat(vehicle.licensePlate()).isEqualTo(license);
            Assertions.assertThat(vehicle.ownerId()).isEqualTo(ownerId);
        }
    }

    private CreateVehicleResource createValidVehicleResource(String vin, String ownerId, String licensePlate) {
        return CreateVehicleResource.builder()
                .brand("Toyota")
                .model("Corolla")
                .year(2023)
                .status("ACTIVE")
                .vin(vin)
                .licensePlate(licensePlate)
                .ownerId(ownerId)
                .build();
    }

    private VehicleResource createVehicle(CreateVehicleResource createVehicleResource) {
        ResponseEntity<VehicleResource> response = restTemplate.postForEntity(
                BASE, createVehicleResource, VehicleResource.class
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();

        return Objects.requireNonNull(response.getBody());
    }
}