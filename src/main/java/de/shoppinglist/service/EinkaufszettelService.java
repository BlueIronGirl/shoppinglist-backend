package de.shoppinglist.service;

import de.shoppinglist.entity.Artikel;
import de.shoppinglist.entity.Einkaufszettel;
import de.shoppinglist.exception.EntityNotFoundException;
import de.shoppinglist.repository.ArtikelRepository;
import de.shoppinglist.repository.EinkaufszettelRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EinkaufszettelService {
    private final EinkaufszettelRepository einkaufszettelRepository;
    private final ArtikelRepository artikelRepository;

    public EinkaufszettelService(EinkaufszettelRepository einkaufszettelRepository, ArtikelRepository artikelRepository) {
        this.einkaufszettelRepository = einkaufszettelRepository;
        this.artikelRepository = artikelRepository;
    }

    public List<Einkaufszettel> findByUserId(Long userId) {
        return einkaufszettelRepository.findByUsersId(userId);
    }

    public Einkaufszettel saveEinkaufszettel(Einkaufszettel entity) {
        return this.einkaufszettelRepository.save(entity);
    }

    public Einkaufszettel updateEinkaufszettel(@RequestBody Einkaufszettel einkaufszettelData, @PathVariable Long id) {
        return einkaufszettelRepository.findById(id)
                .map(einkaufszettel -> {
                    einkaufszettel.setName(einkaufszettelData.getName());
                    einkaufszettel.setUsers(einkaufszettelData.getUsers());
                    return einkaufszettelRepository.save(einkaufszettel);
                })
                .orElseThrow(() -> new EntityNotFoundException("Einkaufszettel nicht gefunden"));
    }

    public Einkaufszettel deleteEinkaufszettel(Long id) {
        Einkaufszettel einkaufszettel = einkaufszettelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Einkaufszettel nicht gefunden"));

        einkaufszettelRepository.deleteById(id);

        return einkaufszettel;
    }

    public Artikel createArtikel(Long einkaufszettelId, @RequestBody Artikel artikelData) {
        Einkaufszettel einkaufszettel = einkaufszettelRepository.findById(einkaufszettelId)
                .orElseThrow(() -> new EntityNotFoundException("Einkaufszettel nicht gefunden"));

        artikelData.setEinkaufszettel(einkaufszettel);
        artikelData.setErstellungsZeitpunkt(LocalDateTime.now());

        return artikelRepository.save(artikelData);
    }

    public Artikel updateArtikel(@RequestBody Artikel artikelData, @PathVariable Long id) {
        return artikelRepository.findById(id)
                .map(artikel -> {
                    artikel.setName(artikelData.getName());
                    artikel.setAnzahl(artikelData.getAnzahl());
                    if (!artikel.isGekauft() && artikelData.isGekauft()) {
                        artikel.setKaufZeitpunkt(LocalDateTime.now());
                    }
                    artikel.setGekauft(artikelData.isGekauft());
                    artikel.setKategories(artikelData.getKategories());
                    return artikelRepository.save(artikel);
                })
                .orElseThrow(() -> new EntityNotFoundException("Artikel nicht gefunden"));
    }

    public Artikel deleteArtikel(@PathVariable Long id) {
        Artikel artikel = artikelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artikel nicht gefunden"));

        artikelRepository.deleteById(id);

        return artikel;
    }
}
