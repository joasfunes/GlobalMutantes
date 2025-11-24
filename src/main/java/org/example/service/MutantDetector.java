package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MutantDetector {

    private final DnaRecordRepository dnaRecordRepository;
    private static final int LARGO_SECUENCIA = 4;

    @Autowired
    public MutantDetector(DnaRecordRepository dnaRecordRepository) {
        this.dnaRecordRepository = dnaRecordRepository;
    }

    public static boolean isMutant(String[] dna) {
        int k = dna.length;
        int sequenceCount = 0;

        sequenceCount += checkRows(dna, k);
        if (sequenceCount > 1) return true;

        sequenceCount += checkColumns(dna, k);
        if (sequenceCount > 1) return true;

        sequenceCount += checkDiagonals(dna, k);
        return sequenceCount > 1;
    }

    private static int checkRows(String[] dna, int k) {
        int sequenceCount = 0;

        for (int i = 0; i < k; i++) {
            int count = 1;
            for (int j = 1; j < k; j++) {
                if (dna[i].charAt(j) == dna[i].charAt(j - 1)) {
                    count++;
                    if (count == LARGO_SECUENCIA) {
                        sequenceCount++;
                        if (sequenceCount > 1) return sequenceCount;
                    }
                } else {
                    count = 1;
                }
            }
        }
        return sequenceCount;
    }

    private static int checkColumns(String[] dna, int k) {
        int sequenceCount = 0;

        for (int j = 0; j < k; j++) {
            int count = 1;
            for (int i = 1; i < k; i++) {
                if (dna[i].charAt(j) == dna[i - 1].charAt(j)) {
                    count++;
                    if (count == LARGO_SECUENCIA) {
                        sequenceCount++;
                        if (sequenceCount > 1) return sequenceCount;
                    }
                } else {
                    count = 1;
                }
            }
        }
        return sequenceCount;
    }

    private static int checkDiagonals(String[] dna, int k) {
        int sequenceCount = 0;

        for (int i = 0; i <= k - LARGO_SECUENCIA; i++) {
            for (int j = 0; j <= k - LARGO_SECUENCIA; j++) {
                if (checkDiagonal(dna, i, j, 1, 1, k)) {
                    sequenceCount++;
                    if (sequenceCount > 1) return sequenceCount; // Early exit
                }
            }
        }

        for (int i = 0; i <= k - LARGO_SECUENCIA; i++) {
            for (int j = LARGO_SECUENCIA - 1; j < k; j++) {
                if (checkDiagonal(dna, i, j, 1, -1, k)) {
                    sequenceCount++;
                    if (sequenceCount > 1) return sequenceCount;
                }
            }
        }
        return sequenceCount;
    }

    private static boolean checkDiagonal(String[] dna, int x, int y, int dx, int dy, int k) {
        char first = dna[x].charAt(y);
        for (int i = 1; i < LARGO_SECUENCIA; i++) {
            if (x + i * dx >= k || y + i * dy >= k || y + i * dy < 0 || dna[x + i * dx].charAt(y + i * dy) != first) {
                return false;
            }
        }
        return true;
    }

    public boolean analyzeDna(String[] dna) {
        String dnaSequence = String.join(",", dna);

        Optional<DnaRecord> existingDna = dnaRecordRepository.findByDna(dnaSequence);
        if (existingDna.isPresent()) {
            return existingDna.get().isMutant();
        }

        boolean isMutant = isMutant(dna);
        DnaRecord dnaRecordEntity = DnaRecord.builder()
                .dna(dnaSequence)
                .isMutant(isMutant)
                .build();
        dnaRecordRepository.save(dnaRecordEntity);

        return isMutant(dna);
    }
}
