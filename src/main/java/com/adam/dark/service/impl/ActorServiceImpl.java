package com.adam.dark.service.impl;


import cn.hutool.core.lang.UUID;
import com.adam.dark.base.consts.NumConst;
import com.adam.dark.base.enmus.SexTypeEnum;
import com.adam.dark.dto.ActorDTO;
import com.adam.dark.entity.ActorEntity;
import com.adam.dark.entity.WorksEntity;
import com.adam.dark.mapper.ActorMapper;
import com.adam.dark.repository.ActorRepository;
import com.adam.dark.repository.WorksRepository;
import com.adam.dark.service.IActorService;
import com.adam.dark.util.*;
import com.adam.dark.vo.ActorVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author adamboov
 */
@Service
public class ActorServiceImpl extends ServiceImpl<ActorMapper, ActorEntity> implements IActorService {

    @Resource
    private ActorRepository actorRepository;
    @Resource
    private WorksRepository worksRepository;
    @Resource
    private SpringRedisUtil springRedisUtil;

    @Resource
    private ActorMapper actorMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public ResponseEntity<ActorVO> getOne(Long id) {

        ActorEntity a = actorMapper.selectById(id);

        ActorDTO obj = (ActorDTO) springRedisUtil.get("actor" + id);
        if (obj != null) {
            return ResponseEntity.ok(MapstructUtil.INSTANCE.actorDTOToVO(obj,
                    new CycleAvoidingMappingContext()));
        }

        List<String> keyList = new ArrayList();
        keyList.add("AUTO_INCL_ID_FORMAT");
        keyList.add("5");
        keyList.add(NumConst.MAX_5_NUM.toString());
        keyList.add(NumberUtil.dateTimeNumber());

        Long start = System.currentTimeMillis();


//        for (int i = 0; i <= NumConst.SIX_MIN_NUM; i++) {
//            List<String> keyList = new ArrayList();
//            keyList.add("AUTO_INCL_ID");
//            keyList.add(NumConst.FIVE_MAX_NUM.toString());
//            String res = springRedisUtil.runLuaScript("id.lua", keyList);
//        }
        System.out.println(System.currentTimeMillis() - start + "ms耗时");

        Long autoId = springRedisUtil.redisTemplate().opsForValue().increment("AUTO_INCL_ID");

        Optional<ActorEntity> optional = actorRepository.findById(id);

        if (optional.isPresent()) {

            springRedisUtil.set("actor" + id, MapstructUtil.INSTANCE.actorEntityToDTO(optional.get(), new CycleAvoidingMappingContext()));

            return ResponseEntity.ok(MapstructUtil.INSTANCE.actorEntityToVO(optional.get(), new CycleAvoidingMappingContext()));
        }

        return ResponseEntity.ok(null);
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public ResponseEntity<ActorVO> add(Long actorId, Long worksId) {

        Optional<ActorEntity> optionalActor = actorRepository.findById(actorId);
        Optional<WorksEntity> optionalWorks = worksRepository.findById(worksId);

        if (optionalActor.isEmpty() || optionalWorks.isEmpty()) {
            throw new RuntimeException("数据有误！！！");

        }
        //  懒加载原因 要单独提出来
        List<WorksEntity> worksList = optionalActor.get().getWorksList();
        if (!worksList.contains(optionalWorks.get())) {
            optionalActor.get().getWorksList().add(optionalWorks.get());
        }

//        actorRepository.saveAndFlush(optionalActor.get());

        return ResponseEntity.ok(MapstructUtil.INSTANCE.actorEntityToVO(optionalActor.get(), new CycleAvoidingMappingContext()));

    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public ResponseEntity<ActorVO> del(Long actorId, Long worksId) {
        Optional<ActorEntity> optionalActor = actorRepository.findById(actorId);
        Optional<WorksEntity> optionalWorks = worksRepository.findById(worksId);

        if (optionalActor.isPresent() && optionalWorks.isPresent()) {
            optionalActor.get().getWorksList().remove(optionalWorks.get());
        }

//        actorRepository.saveAndFlush(optionalActor.get());

        return ResponseEntity.ok(MapstructUtil.INSTANCE.actorEntityToVO(optionalActor.get(), new CycleAvoidingMappingContext()));
    }

    @Override
    public void genActor() {

        Long start = System.currentTimeMillis();

        for (int i = 0; i < NumConst.MIN_7_NUM; i++) {
            Long id = springRedisUtil.autoIncrementId("AUTO_INCREMENT");
            actorMapper.insert(
                    ActorEntity.builder()
                            .id(NumberUtil.orderNumberLong(id))
                            .createById(id)
                            .name(UUID.fastUUID().toString())
                            .createBy("adam")
                            .gender(SexTypeEnum.UNKNOWN)
                            .build()
            );
        }
        System.out.println("耗时" + (System.currentTimeMillis() - start) + "ms");
    }
}
