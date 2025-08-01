package org.danji.badge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.badge.dto.BadgeDTO;
import org.danji.badge.mapper.BadgeMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor

public class BadgeServiceImpl implements BadgeService {
    private final BadgeMapper mapper;

    @Override
    public BadgeDTO get(UUID badgeId){

    }


}
