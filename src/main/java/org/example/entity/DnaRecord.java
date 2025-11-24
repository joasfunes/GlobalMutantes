package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DnaRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dna;

    private boolean isMutant;

    public DnaRecord(String dnaHash, boolean isMutant) {
        this.dna = dnaHash;
        this.isMutant = isMutant;
    }
}
