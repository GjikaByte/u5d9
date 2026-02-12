package andi.u5d9.controllers;

import andi.u5d9.entities.Autore;
import andi.u5d9.payloads.AutorePayload;
import andi.u5d9.services.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("authors")
public class AutoreController {
    private final AutoreService autoreService;

    @Autowired
    public AutoreController(AutoreService autoreService) {
        this.autoreService = autoreService;
    }

    // 1. POST http://localhost:3001/authors (+ Payload)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Autore createUser(@RequestBody AutorePayload payload) {
        return this.autoreService.save(payload);
    }

    // 2. GET http://localhost:3001/authors
    @GetMapping
    public Page<Autore> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "cognome") String orderBy,
                                @RequestParam(defaultValue = "asc") String sortCriteria) {

        return this.autoreService.findAll(page, size, orderBy, sortCriteria);
    }

    // 3. GET http://localhost:3001/authors/{autoreId}
    @GetMapping("/{autoreId}")
    public Autore findById(@PathVariable UUID userId) {
        return this.autoreService.findById(userId);
    }

    // 4. PUT http://localhost:3001/authors/{autoreId}
    @PutMapping("/{autoreId}")
    public Autore findByIdAndUpdate(@PathVariable UUID autoreId, @RequestBody AutorePayload payload) {
        return this.autoreService.findByIdAndUpdate(autoreId, payload);
    }

    // 5. DELETE http://localhost:3001/authors/{autoreId}
    @DeleteMapping("/{autoreId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID autoreId) {
        this.autoreService.findByIdAndDelete(autoreId);
    }

}
