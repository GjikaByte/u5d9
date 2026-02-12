package andi.u5d9.services;

import andi.u5d9.entities.Autore;
import andi.u5d9.entities.Blog;
import andi.u5d9.exceptions.BadRequestException;
import andi.u5d9.exceptions.NotFoundException;
import andi.u5d9.payloads.AutorePayload;
import andi.u5d9.payloads.BlogPayload;
import andi.u5d9.repositories.AutoreRepository;
import andi.u5d9.repositories.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class BlogService {

    private final BlogRepository blogRepository;
    private final AutoreRepository autoreRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository,AutoreRepository autoreRepository) {

        this.blogRepository = blogRepository;
        this.autoreRepository = autoreRepository;
    }

    public Blog save(BlogPayload payload) {
        // 1. Trovo autore by id e costruisco il newBlog
        Autore autore = autoreRepository.findById(payload.getAutore())
                .orElseThrow(() -> new RuntimeException("Autore non trovato con id: " + payload.getAutore()));

        Blog newBlog = new Blog(payload.getCategoria(), payload.getTitolo(), payload.getContenuto(), payload.getTempodiLettura(),autore);

        // 2. Salvo
        Blog savedBlog = this.blogRepository.save(newBlog);

        // 4. Log
        log.info("Il blog con id " + savedBlog.getIdBlog() + " è stato salvato correttamente!");
        // 5. Torno il blog salvato
        return savedBlog;
    }

    public Page<Blog> findAll(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        // ...
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.blogRepository.findAll(pageable);
    }

    public Blog findById(UUID blogId) {
        return this.blogRepository.findById(blogId)
                .orElseThrow(() -> new NotFoundException(blogId));
    }

    public Blog findByIdAndUpdate(UUID blogId, BlogPayload payload) {
        // 1. Cerchiamo il blog e l'autore nel db
        Blog found = this.findById(blogId);
        Autore autore = autoreRepository.findById(payload.getAutore())
                .orElseThrow(() -> new RuntimeException("Autore non trovato con id: " + payload.getAutore()));


        // 2. Modifico l'autore trovato
        found.setCategoria(payload.getCategoria());
        found.setAutore(autore);
        found.setTitolo(payload.getTitolo());
        found.setTempodiLettura(payload.getTempodiLettura());
        found.setCover("https://picsum.photos/200/300");
        found.setContenuto(payload.getContenuto());

        // 3. Salvo
        Blog modifiedBlog = this.blogRepository.save(found);

        // 4. Log
        log.info("Il blog con id " + modifiedBlog.getIdBlog() + " è stato modificato correttamente");

        // 5. Ritorno il blog modificato
        return modifiedBlog;
    }

    public void findByIdAndDelete(UUID blogId) {
        Blog found = this.findById(blogId);
        this.blogRepository.delete(found);
    }
}
