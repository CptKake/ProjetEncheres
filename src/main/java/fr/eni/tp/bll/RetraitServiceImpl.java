package fr.eni.tp.bll;

import org.springframework.stereotype.Service;


import fr.eni.tp.bo.Retrait;
import fr.eni.tp.dal.RetraitDAO;

@Service
public class RetraitServiceImpl implements RetraitService {

	 private final RetraitDAO retraitDAO;

	    public RetraitServiceImpl(RetraitDAO retraitDAO) {
	        this.retraitDAO = retraitDAO;
	    }

	    public Retrait getRetraitForArticle(int articleId) {
	        return retraitDAO.takeAdress(articleId);
	    }

	    public void saveRetrait(Retrait retrait, int articleId) {
	        retraitDAO.saveRetrait(retrait, articleId);
	    }

		@Override
		public void updateRetrait(Retrait retrait, int articleId) {
			retraitDAO.updateRetrait(retrait, articleId);
			
		}

		@Override
		public void deleteRetrait(int articleId) {
			retraitDAO.deleteRetrait(articleId);
			
		}
}
