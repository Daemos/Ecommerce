package com.java.ecommerce.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.java.ecommerce.model.Client;
import com.java.ecommerce.model.Commande;
import com.java.ecommerce.model.Produit;
import com.java.ecommerce.service.ClientService;
import com.java.ecommerce.service.CommandeService;
import com.java.ecommerce.service.ProduitService;

@Controller
@SessionAttributes("idClient")
public class RechercheProduitController {

	@Autowired
	private ClientService clientService;
	@Autowired
	private CommandeService commandeService;

	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = "/rechercheproduit")
	public ModelAndView LoadRechercheProduit(final HttpSession session) {

		int idClientLog = (Integer) session.getAttribute("idClient");
		Client client = clientService.getClientById(idClientLog);
		String displayname = client.getPrenomClient() + " " + client.getNomClient();

		logger.info("Returning recherche produit view");
		List<Commande> commandes = commandeService.getAllCommandes();
		//Commande commande = commandeService.getCommandeById(1);
		ModelAndView mav = new ModelAndView("rechercheproduit");
		//mav.addObject("commande", commande);
		mav.addObject("commandes", commandes);
		logger.info(commandes);
		
		mav.addObject("displayname", displayname);
		return mav;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String VoirDetail(@RequestParam(value = "id") Integer id, final HttpSession session) {
		logger.info("Delete " + id);
		commandeService.deleteCommande(id);
		return "redirect:/rechercheproduit";

	}

}
