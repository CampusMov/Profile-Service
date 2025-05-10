package com.campusmov.platform.profileservice.profile.domain.model.aggregates;

import com.campusmov.platform.profileservice.profile.domain.model.valueobjects.*;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@Getter
@Setter
public class Vehicle extends AbstractAggregateRoot<Vehicle> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Embedded
    private BasicVehicleSpecs basicVehicleSpecs;

    @Enumerated(EnumType.STRING)
    private EVehicleStatus status;

    @Embedded
    private VehicleIdentification vehicleIdentification;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "owner_id"))
    private UserId ownerId;

    public Vehicle() {
        //JPA
    }
}
