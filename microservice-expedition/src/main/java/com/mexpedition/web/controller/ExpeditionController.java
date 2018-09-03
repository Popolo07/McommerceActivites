package com.mexpedition.web.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.mexpedition.dao.ExpeditionDao;
import com.mexpedition.model.Expedition;
import com.mexpedition.web.exceptions.ExpeditionNotFoundException;
import com.mexpedition.web.exceptions.ImpossibleAfficherExpeditionException;

@RestController
public class ExpeditionController {
    
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    ExpeditionDao expeditionDao;
    
    @PostMapping (value = "/suivi")
    public ResponseEntity<Expedition> ajouterExpedition(@RequestBody Expedition expedition){

    	Expedition nouvelleCommande = expeditionDao.save(expedition);

        if(nouvelleCommande == null) throw new ImpossibleAfficherExpeditionException("Impossible d'ajouter cette commande");

        return new ResponseEntity<Expedition>(expedition, HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/suivi/{id}")
    public Optional<Expedition> recupererUneExpedition(@PathVariable int id){

        Optional<Expedition> commande = expeditionDao.findById(id);

        if(!commande.isPresent()) throw new ExpeditionNotFoundException("Cette expedition n'existe pas");

        return commande;
    }
    
    @PutMapping(value = "/suivi")
    public ResponseEntity<Expedition> updateEtat(@RequestBody Expedition expedition){

    	Optional<Expedition> commande = expeditionDao.findById(expedition.getId());

    	if (commande==null) throw new ExpeditionNotFoundException("Cette expedition n'existe pas");
    		
    	Expedition nouvelleCommande = expeditionDao.save(expedition);

        if(nouvelleCommande == null) throw new ImpossibleAfficherExpeditionException("Impossible de  modifier cette commande");

        return new ResponseEntity<Expedition>(expedition, HttpStatus.CREATED);
    }
    
}
