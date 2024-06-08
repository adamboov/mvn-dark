package com.adam.dark.service;

import com.adam.dark.vo.ActorVO;
import org.springframework.http.ResponseEntity;

/**
 * @author adamboov
 */
public interface IActorService {

    /**
     * 获取单个对象
     *
     * @param id id主键
     * @return 对象
     */
    ResponseEntity<ActorVO> getOne(Long id);

    ResponseEntity<ActorVO> add(Long actorId, Long worksId);

    ResponseEntity<ActorVO> del(Long actorId, Long worksId);

    void genActor();
}
