package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.*;
import com.javariga6.yugioh.service.ArticleService;
import com.javariga6.yugioh.service.CardStorageService;
import com.javariga6.yugioh.service.StockItemService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("csv")
public class CsvController {
    private final ArticleService articleService;
    private final CardStorageService cardStorageService;
    private final StockItemService stockItemService;

    public CsvController(ArticleService articleService, CardStorageService cardStorageService, StockItemService stockItemService) {
        this.articleService = articleService;
        this.cardStorageService = cardStorageService;
        this.stockItemService = stockItemService;
    }

    @GetMapping("import/art")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public boolean importCsv() throws IOException {
        Resource resource = new ClassPathResource("/static/articles.csv");
        File file = resource.getFile();
        System.out.println(file.canRead());
        List<Article> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("#");
                Article article = new Article();
                article.setBoosterSet(values[0]);
                article.setCardName(values[1]);
                article.setEdition(Edition.valueOf(values[2]));
                article.setRarity(Rarity.valueOf(values[3]));
                article.setCardType(CardType.valueOf(values[4]));

                if(articleService.findByArticle(article).size()<1){
                    records.add(article);
                    articleService.saveArticle(article);
                }


            }
            System.out.println(records.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        records.forEach(articleService::saveArticle);
        return true;
    }
    @GetMapping("import/itm")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public boolean importCsv2() throws IOException {

        Resource resource = new ClassPathResource("/static/items.csv");
        File file = resource.getFile();
        System.out.println(file.canRead());
        List<StockItem> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("#");
                Article article = new Article();
                article.setBoosterSet(values[0]);
                article.setCardName(values[1]);
                article.setEdition(Edition.valueOf(values[2]));
                article.setRarity(Rarity.valueOf(values[3]));
                article.setCardType(CardType.valueOf(values[4]));
                if (articleService.findByArticle(article).size()<1){
                    System.out.println(article);
                }
                article = articleService.findByArticle(article).get(0);
                System.out.println(article);

                StockItem item = new StockItem();
                item.setArticle(article);
                item.setCardCondition(CardCondition.valueOf(values[5]));
                item.setCardStorage(
                        cardStorageService.findByName(values[6])
                );
                if(values[7].length()>0) {
                    item.setCardValue(new BigDecimal(values[7]));
                }
                item.setComments(values[8]);

                long q = Long.parseLong(values[9]);

                for (int i=0; i<q; ++i){
                    StockItem item1 = new StockItem();
                    item1.setComments(item.getComments());
                    item1.setCardValue(item.getCardValue());
                    item1.setCardStorage(item.getCardStorage());
                    item1.setArticle(article);
                    item1.setCardCondition(item.getCardCondition());
                    item1.setInShop(true);
                    stockItemService.saveStockItem(item1);
                }

            }
//            System.out.println(records.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        records.forEach(articleService::saveArticle);
        return true;
    }


}
