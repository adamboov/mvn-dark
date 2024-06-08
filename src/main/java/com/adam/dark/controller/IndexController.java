package com.adam.dark.controller;

import com.adam.dark.service.IActorService;
import com.adam.dark.vo.ActorVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author adamboov
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private IActorService iActorService;

    @GetMapping("/genOne")
    public void genActor() {
        iActorService.genActor();
    }

    @GetMapping("/getOne")
    public ResponseEntity<ActorVO> getOne(Long id) {
        return iActorService.getOne(id);
    }

    @DeleteMapping("/delWorksFromActor")
    public ResponseEntity<ActorVO> delWorks(Long actorId, Long worksId) {
        return iActorService.del(actorId, worksId);
    }

    @PostMapping("/addWorksToActor")
    public ResponseEntity<ActorVO> addWorks(Long actorId, Long worksId) {
        return iActorService.add(actorId, worksId);

    }
}
