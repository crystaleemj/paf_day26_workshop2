package sg.edu.nus.iss.paf_day26_workshop2.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_day26_workshop2.model.Game;


@Repository
public class GameRepo {

    @Autowired
    MongoTemplate template;
    
    public List<Game> listAllGames(Integer offset, Integer limit){
        
        Criteria c = Criteria.where("");
        Query q = Query.query(c).skip(offset).limit(limit);
        List<Document> docList = new ArrayList<>();
        docList = template.find(q, Document.class, "games");

        List<Game> gameList = new ArrayList<>();
        for (Document doc : docList) {
            Game game = new Game(doc.getInteger("gid"), doc.getString("name"));

            gameList.add(game);
        }

        return gameList;
    }

    public List<Game> listGameByRank(Integer offset, Integer limit){

        Criteria c = Criteria.where("");
        Query q = Query.query(c)
        .limit(limit)
        .skip(offset)
        .with(Sort.by("ranking")
        .ascending());

        List<Document> docList = new ArrayList<>();
        docList = template.find(q, Document.class, "games");

        List<Game> gameList = new ArrayList<>();
        for (Document doc : docList) {
            Game game = new Game(doc.getInteger("gid"), doc.getString("name"));

            gameList.add(game);
        }

        return gameList;
    }

    public Boolean checkGameIdExists(Integer id){

        Criteria c = Criteria.where("gid").is(id);
        Query q = Query.query(c);

        return template.exists(q,  "games");
    }

    public Document listGameById(Integer id){

        Criteria c = Criteria.where("gid").is(id);
        Query q = Query.query(c);

        return template.findOne(q, Document.class, "games");
    }

    public Long countCommentsById(Integer id){

        Criteria c = Criteria.where("gid").is(id);
        Query q = Query.query(c);

        return template.count(q, "comments");
    }

   public Integer totalRatingsById(Integer id){

        Criteria c = Criteria.where("gid").is(id);
        Query q = Query.query(c);

        List<Document> docList = template.find(q, Document.class, "comments");

        List<Integer> countRating = new ArrayList<>();
        for (Document document : docList) {
            Integer i = document.getInteger("rating");
            countRating.add(i);
        }

        Integer total = 0;
        // sum up the ratings in the list
        for (Integer integer : countRating) {
            total += integer;
        }
        return total;
   }
}
