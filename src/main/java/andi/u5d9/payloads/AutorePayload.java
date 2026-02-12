package andi.u5d9.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AutorePayload {
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataDiNascita;
}
