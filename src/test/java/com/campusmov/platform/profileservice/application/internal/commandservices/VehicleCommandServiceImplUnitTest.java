package com.campusmov.platform.profileservice.application.internal.commandservices;

import com.campusmov.platform.profileservice.profile.application.internal.commandservices.VehicleCommandServiceImpl;
import com.campusmov.platform.profileservice.profile.domain.model.aggregates.Vehicle;
import com.campusmov.platform.profileservice.profile.domain.model.commands.ChangeVehicleStatusCommand;
import com.campusmov.platform.profileservice.profile.domain.model.commands.CreateVehicleCommand;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.EVehicleStatus;
import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.UserId;
import com.campusmov.platform.profileservice.profile.domain.services.VehicleCommandService;
import com.campusmov.platform.profileservice.profile.infrastructure.persistence.jpa.repositories.VehicleRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class VehicleCommandServiceImplUnitTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Captor
    private ArgumentCaptor<Vehicle> vehicleCaptor;

    private VehicleCommandService vehicleCommandService;

    @BeforeEach
    void setUp() {
        vehicleCommandService = new VehicleCommandServiceImpl(vehicleRepository);
    }

    private CreateVehicleCommand createValidVehicleCommand() {
        // ** LA CORRECCIÓN FINAL ESTÁ AQUÍ **
        // Se usa un VIN de 17 caracteres para pasar la validación del comando.
        return CreateVehicleCommand.builder()
                .brand("Toyota")
                .model("Corolla")
                .year(2023)
                .status("ACTIVE")
                .vin("123456789ABCDEFGH") // <-- VIN VÁLIDO DE 17 CARACTERES
                .licensePlate("PLATE456")
                .ownerId("owner-1")
                .build();
    }

    @Test
    @DisplayName("Handle CreateVehicleCommand - When VIN and Owner are new, should create vehicle")
    void testCreateVehicle_whenVinAndOwnerAreNew_shouldSucceed() {
        // Given
        var command = createValidVehicleCommand();
        Mockito.when(vehicleRepository.existsByVehicleIdentification_Vin(command.vin())).thenReturn(false);
        Mockito.when(vehicleRepository.existsByOwnerId(any(UserId.class))).thenReturn(false);
        Mockito.when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(i -> i.getArgument(0));

        // When
        var result = vehicleCommandService.handle(command);

        // Then
        Assertions.assertThat(result).isPresent();
        Mockito.verify(vehicleRepository).save(vehicleCaptor.capture());
        Vehicle savedVehicle = vehicleCaptor.getValue();
        Assertions.assertThat(savedVehicle.getVehicleIdentification().vin()).isEqualTo(command.vin());
        Assertions.assertThat(savedVehicle.getOwnerId().id()).isEqualTo(command.ownerId());
    }

    @Test
    @DisplayName("Handle CreateVehicleCommand - When VIN already exists, should throw exception")
    void testCreateVehicle_whenVinExists_shouldThrowException() {
        // Given
        var command = createValidVehicleCommand();
        Mockito.when(vehicleRepository.existsByVehicleIdentification_Vin(command.vin())).thenReturn(true);

        // When & Then
        var exception = assertThrows(IllegalArgumentException.class, () -> vehicleCommandService.handle(command));
        Assertions.assertThat(exception.getMessage()).isEqualTo("Vehicle with VIN already exists.");
        Mockito.verify(vehicleRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Handle CreateVehicleCommand - When Owner already has a vehicle, should throw exception")
    void testCreateVehicle_whenOwnerExists_shouldThrowException() {
        // Given
        var command = createValidVehicleCommand();
        Mockito.when(vehicleRepository.existsByVehicleIdentification_Vin(command.vin())).thenReturn(false);
        Mockito.when(vehicleRepository.existsByOwnerId(any(UserId.class))).thenReturn(true);

        // When & Then
        var exception = assertThrows(IllegalArgumentException.class, () -> vehicleCommandService.handle(command));
        Assertions.assertThat(exception.getMessage()).isEqualTo("Vehicle with this owner already exists.");
        Mockito.verify(vehicleRepository, Mockito.never()).save(any());
    }


    @Test
    @DisplayName("Handle ChangeVehicleStatusCommand - When vehicle exists, should change status")
    void testChangeStatus_whenVehicleExists_shouldSucceed() {
        // Given
        var command = new ChangeVehicleStatusCommand("INACTIVE");
        String vehicleId = "vehicle-123";
        Vehicle mockVehicle = Mockito.mock(Vehicle.class);

        Mockito.when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(mockVehicle));
        Mockito.when(mockVehicle.getStatus()).thenReturn(EVehicleStatus.INACTIVE);

        // When
        var result = vehicleCommandService.handle(command, vehicleId);

        // Then
        Assertions.assertThat(result).isPresent().contains("INACTIVE");
        Mockito.verify(mockVehicle).changeStatus("INACTIVE");
        Mockito.verify(vehicleRepository).save(mockVehicle);
    }

    @Test
    @DisplayName("Handle ChangeVehicleStatusCommand - When vehicle does NOT exist, should throw exception")
    void testChangeStatus_whenVehicleDoesNotExist_shouldThrowException() {
        // Given
        var command = new ChangeVehicleStatusCommand("INACTIVE");
        String nonExistentId = "non-existent-id";
        Mockito.when(vehicleRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // When & Then
        var exception = assertThrows(IllegalArgumentException.class, () -> vehicleCommandService.handle(command, nonExistentId));
        Assertions.assertThat(exception.getMessage()).isEqualTo("Vehicle not found with ID: " + nonExistentId);
    }
}