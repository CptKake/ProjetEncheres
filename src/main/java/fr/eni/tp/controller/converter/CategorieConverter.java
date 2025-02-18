package fr.eni.tp.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.tp.bll.EnchereService;
import fr.eni.tp.bo.Categorie;

@Component
public class CategorieConverter implements Converter<String, Categorie> {

	private EnchereService enchereService;

	public CategorieConverter(EnchereService enchereService) {
		this.enchereService = enchereService;
	}



	@Override
	public Categorie convert(String source) {
		Categorie cat = enchereService.getCatById(Integer.parseInt(source));
		return cat;
	}

	
}
