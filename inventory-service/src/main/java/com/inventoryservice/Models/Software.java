package com.inventoryservice.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference // This prevents infinite recursion when serializing to JSON
    private List<Inventory> inventories;
}
