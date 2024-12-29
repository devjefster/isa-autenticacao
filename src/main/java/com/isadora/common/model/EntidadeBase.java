package com.isadora.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
    public class EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @CreationTimestamp
    private Instant createdAt;

    @Version
    @UpdateTimestamp
    private Instant updatedAt;
}
