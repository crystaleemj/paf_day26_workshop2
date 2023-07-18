package sg.edu.nus.iss.paf_day26_workshop2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.paf_day26_workshop2.service.GameSvc;

@RestController
@RequestMapping
public class GameRestContoller {

    @Autowired
    GameSvc svc;
    
    @GetMapping(path = "/games")
    public ResponseEntity<String> listAllGames(@RequestParam (defaultValue = "25") Integer limit, @RequestParam (defaultValue = "0") Integer offset) {

        
        return new ResponseEntity<>(svc.listGameJson(offset, limit).toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/games/rank")
    public ResponseEntity<String> listGameByRank(@RequestParam (defaultValue = "25") Integer limit, @RequestParam (defaultValue = "0") Integer offset) {

        return new ResponseEntity<String>(svc.listGameByRankJson(offset, limit).toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/game/{game_id}")
    public ResponseEntity<String> listGameById(@PathVariable Integer game_id) {

        if (!svc.checkGameById(game_id)) {
            return new ResponseEntity<String>(svc.errorJson().toString(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(svc.listGameByIdJson(game_id).toString(), HttpStatus.OK);
    }
    
    
}
