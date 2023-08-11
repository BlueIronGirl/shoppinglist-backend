package com.example.einkaufsliste.controller;

import com.example.einkaufsliste.entity.ArtikelArchiv;
import com.example.einkaufsliste.service.ArtikelArchivService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
public class ArtikelArchivController {
    private final ArtikelArchivService artikelArchivService;

    /**
     * Alle Artikel von dem Archiv abfragen
     *
     * @return alle Artikel als Liste
     */
    @GetMapping("/selectAllArtikelArchiv")
    public List<ArtikelArchiv> selectAllArtikelArchiv() {
        return artikelArchivService.selectAllArtikelArchiv();
    }
}
