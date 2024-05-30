package com.example.Spring.Repositories2.controller;

import com.example.Spring.Repositories2.entities.ProgrammingLanguage;
import com.example.Spring.Repositories2.repositories.ProgrammingLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/programmingLanguage")
public class LanguageController {

    @Autowired
    private ProgrammingLanguageRepository programmingLanguageRepository;

    @PostMapping
    public ProgrammingLanguage createProgrammingLanguage(@RequestBody ProgrammingLanguage programmingLanguage) {
        programmingLanguage.setId(null);
        ProgrammingLanguage programmingLanguageSaved = programmingLanguageRepository.saveAndFlush(programmingLanguage);
        return programmingLanguageSaved;
    }

    @GetMapping
    public Page<ProgrammingLanguage> getProgrammingLanguageList
            (@RequestParam(required = false) Optional<Integer> page, @RequestParam(required = false) Optional<Integer> size) {
        if (page.isPresent() && size.isPresent()) {
            Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "name"), new Sort.Order(Sort.Direction.DESC, "inventor"));
            Pageable pageable = PageRequest.of(page.get(), size.get(), sort);
            Page<ProgrammingLanguage> programmingLanguages = programmingLanguageRepository.findAll(pageable);
            return programmingLanguages;
        } else {
            Page<ProgrammingLanguage> pageProgrammingLanguages = Page.empty();
            return pageProgrammingLanguages;
        }
    }

    @GetMapping("/{id}")
    public ProgrammingLanguage getSingleProgrammingLanguage(@PathVariable long id) {
        if (programmingLanguageRepository.existsById(id)) {
            ProgrammingLanguage programmingLanguage = programmingLanguageRepository.findById(id).get();
            return programmingLanguage;
        } else {
            return new ProgrammingLanguage();
        }
    }

    @PutMapping("/{id}")
    public ProgrammingLanguage updateProgrammingLanguageInventor(@PathVariable long id, @RequestParam(required = false) String inventor) {
        if (programmingLanguageRepository.existsById(id)) {
            ProgrammingLanguage programmingLanguage = programmingLanguageRepository.findById(id).get();
            programmingLanguage.setInventor(inventor);
            return programmingLanguageRepository.saveAndFlush(programmingLanguage);
        } else {
            return new ProgrammingLanguage();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteSingleProgrammingLanguage(@PathVariable long id) {
        if (programmingLanguageRepository.existsById(id)) {
            programmingLanguageRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @DeleteMapping("")
    public void deleteAllProgrammingLanguage() {
        programmingLanguageRepository.deleteAll();
    }

}
