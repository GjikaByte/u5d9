package andi.u5d9.payloads;

import andi.u5d9.entities.Autore;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class BlogPayload {
    private String categoria;
    private String titolo;
    private String contenuto;
    private LocalDateTime tempodiLettura;
    private UUID autore;
}

