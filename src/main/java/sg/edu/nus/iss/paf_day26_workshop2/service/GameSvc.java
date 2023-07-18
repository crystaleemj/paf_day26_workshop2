package sg.edu.nus.iss.paf_day26_workshop2.service;

import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.paf_day26_workshop2.model.Game;
import sg.edu.nus.iss.paf_day26_workshop2.repository.GameRepo;

@Service
public class GameSvc {
    
    @Autowired
    GameRepo repo;
    
    public List<Game> listAllGames(Integer offset, Integer limit){
        return repo.listAllGames(offset, limit);
    }

//     {
//         games: [ <array of games> ],
//         offset: <offset or default value>,
//         limit: <limit or default value>,
//         total: <total number of games>,
//         timestamp: <result timestamp>
//   }

    public JsonObject listGameJson(Integer offset, Integer limit){
        // creating an JsonArrayBuilder 
        JsonArrayBuilder gamesArray = Json.createArrayBuilder();
        List<Game> gameList = listAllGames(offset, limit);

        for (Game game : gameList) {
            JsonObject gameObj = Json.createObjectBuilder()
                    .add("game_id", game.getId())
                    .add("name", game.getName())
                    .build();
            // object.add("game_id", game.getId())
            //     .add("name", game.getName())
            //     .build();
            gamesArray.add(gameObj);
        }

        JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        jsonObject.add("games", gamesArray)
                .add("offset", offset)
                .add("limit", limit)
                .add("total", gameList.size())
                .add("timestamp", new Date().toString());
        
        return jsonObject.build();

    }

    public List<Game> listGameByRank(Integer offset, Integer limit){
        return repo.listGameByRank(offset, limit);
    }

       public JsonObject listGameByRankJson(Integer offset, Integer limit){
        // creating an JsonArrayBuilder 
        JsonArrayBuilder gamesArray = Json.createArrayBuilder();
        List<Game> gameList = listGameByRank(offset, limit);

        for (Game game : gameList) {
            JsonObject gameObj = Json.createObjectBuilder()
                    .add("game_id", game.getId())
                    .add("name", game.getName())
                    .build();
            // object.add("game_id", game.getId())
            //     .add("name", game.getName())
            //     .build();
            gamesArray.add(gameObj);
        }

        JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        jsonObject.add("games", gamesArray)
                .add("offset", offset)
                .add("limit", limit)
                .add("total", gameList.size())
                .add("timestamp", new Date().toString());
        
        return jsonObject.build();

    }

    public Boolean checkGameById(Integer id){
        return repo.checkGameIdExists(id);
    }

    public Document listGameById(Integer id){
        return repo.listGameById(id);
    }

    public JsonObject listGameByIdJson(Integer id){
        Document doc = listGameById(id);
        
        System.out.println(countCommentsById(id));
        System.out.println(totalRatingsById(id));

        JsonObjectBuilder object = Json.createObjectBuilder()
            .add("game_id", doc.getInteger("gid"))
            .add("name", doc.getString("name"))
            .add("year", doc.getInteger("year"))
            .add("ranking", doc.getInteger("ranking"))
            .add("average", averageRatings(id))
            .add("users_rated", doc.getInteger("users_rated"))
            .add("url", doc.getString("url"))
            .add("thumbnail", doc.getString("image"))
            .add("timestamp", new Date().toString());

            return object.build();
    }

    public JsonObject errorJson(){
        JsonObjectBuilder object = Json.createObjectBuilder()
        .add("error", "Game ID unavailable");

        return object.build();
    }

    public Long countCommentsById(Integer id){
        return repo.countCommentsById(id);
    }

    public Integer totalRatingsById(Integer id){
        return repo.totalRatingsById(id);
    }

    public double averageRatings(Integer id){
        return totalRatingsById(id)/countCommentsById(id);
    }

    
}
