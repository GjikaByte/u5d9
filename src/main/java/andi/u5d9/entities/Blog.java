package andi.u5d9.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Blog {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idBlog;
    private String categoria;
    private String titolo;
    private String cover;
    private String contenuto;
    private LocalDateTime tempodiLettura;
    @ManyToOne
    private Autore autore;

    public Blog(String categoria, String titolo, String contenuto, LocalDateTime tempodiLettura, Autore autore) {
        this.categoria = categoria;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.tempodiLettura = tempodiLettura;
        this.autore = autore;
        this.cover = "https://picsum.photos/200/300";
    }
}
