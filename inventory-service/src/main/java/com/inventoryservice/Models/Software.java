package com.inventoryservice.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="softwares")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Software {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(length = 100)
    private String version;
    @ManyToMany(mappedBy = "installedSoftwares")
    private List<Inventory> inventories;
}
