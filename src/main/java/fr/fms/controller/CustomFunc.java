package fr.fms.controller;

import fr.fms.entities.Article;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CustomFunc {
    private boolean isUserConnected = false;
    public double getTotalPrice(HashMap<Long, Article> articleOrderList){
        double totalPrice = 0;
        for(Article article : articleOrderList.values()){
            totalPrice += article.getPrice() * article.getQuantity();
        }
        return totalPrice;
    }

    public int getTotalNumber(HashMap<Long, Article> articleOrderList){
        int totalNumber = 0;
        for(Article article : articleOrderList.values()){
            totalNumber += article.getQuantity();
        }
        return totalNumber;
    }
    public boolean getIsUserConnected(){
        return isUserConnected;
    }
    public void setUserConnected(boolean userConnected) {
        isUserConnected = userConnected;
    }
}
