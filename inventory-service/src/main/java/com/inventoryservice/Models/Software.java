package com.inventoryservice.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.inventoryservice.Enums.STATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Column(nullable = false)
    private String name;

    @Column
    private String version;

    @Column
    private String description;

    @Column
    private String category;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column( nullable = false)
    private STATUS status;


    @ManyToMany(mappedBy = "installedSoftwares")
    @JsonBackReference // This prevents infinite recursion when serializing to JSON
    @Builder.Default
    private Set<Inventory> inventories = new HashSet<>();
}
