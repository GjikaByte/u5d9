package andi.u5d9.services;

import andi.u5d9.entities.Autore;
import andi.u5d9.exceptions.BadRequestException;
import andi.u5d9.exceptions.NotFoundException;
import andi.u5d9.payloads.AutorePayload;
import andi.u5d9.repositories.AutoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class AutoreService {

    private final AutoreRepository autoreRepository;

    @Autowired
    public AutoreService(AutoreRepository autoreRepository) {

        this.autoreRepository = autoreRepository;
    }

    public Autore save(AutorePayload payload) {

        // 1. Verifichiamo che l'email passata non sia già in uso
        this.autoreRepository.findByEmail(payload.getEmail()).ifPresent(autore -> {
            throw new BadRequestException("L'email " + autore.getEmail() + " è già in uso!");
        });

        // 2. Aggiungo dei campi "server-generated" tipo avatar
        Autore newAutore = new Autore(payload.getNome(),payload.getCognome(),payload.getEmail(),payload.getDataDiNascita());
        newAutore.setAvatar("https://ui-avatars.com/api?name=" + payload.getNome() + "+" + payload.getCognome());

        // 3. Salvo
        Autore savedAutore = this.autoreRepository.save(newAutore);

        // 4. Log
        log.info("L'utente con id " + savedAutore.getIdAutore() + " è stato salvato correttamente!");
        // 5. Torno l'utente salvato
        return savedAutore;
    }

    public Page<Autore> findAll(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        // ...
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.autoreRepository.findAll(pageable);
    }

    public Autore findById(UUID autoreId) {
        return this.autoreRepository.findById(autoreId)
                .orElseThrow(() -> new NotFoundException(autoreId));
    }

    public Autore findByIdAndUpdate(UUID autoreId, AutorePayload payload) {
        // 1. Cerchiamo l'utente nel db
        Autore found = this.findById(autoreId);

        // 2. Validazione dati (esempio controllo se email è già in uso)
        if (!found.getEmail().equals(payload.getEmail())) this.autoreRepository.findByEmail(payload.getEmail()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
        });

        // 3. Modifico l'autore trovato
        found.setNome(payload.getNome());
        found.setCognome(payload.getCognome());
        found.setEmail(payload.getEmail());
        found.setDataDiNascita(payload.getDataDiNascita());
        found.setAvatar("https://ui-avatars.com/api?name=" + payload.getNome() + "+" + payload.getCognome());

        // 4. Salvo
        Autore modifiedAutore = this.autoreRepository.save(found);

        // 5. Log
        log.info("L'autore con id " + modifiedAutore.getIdAutore() + " è stato modificato correttamente");

        // 6. Ritorno l'autore modificato
        return modifiedAutore;
    }

    public void findByIdAndDelete(UUID autoreId) {
        Autore found = this.findById(autoreId);
        this.autoreRepository.delete(found);
    }
}
