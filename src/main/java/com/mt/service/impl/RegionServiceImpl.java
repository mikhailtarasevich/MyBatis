package com.mt.service.impl;

import com.mt.dto.RegionDto;
import com.mt.entity.RegionEntity;
import com.mt.repository.RegionRepository;
import com.mt.service.RegionService;
import com.mt.service.exception.DatabaseException;
import com.mt.service.exception.NotValidEntityException;
import com.mt.service.exception.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    private final ModelMapper mapper;

    @Autowired
    public RegionServiceImpl(RegionRepository regionRepository, ModelMapper mapper) {
        this.regionRepository = regionRepository;
        this.mapper = mapper;
    }

    @Override
    @CacheEvict(cacheNames = "regions", allEntries = true)
    public RegionDto create(RegionDto regionDto) {
        if (Objects.nonNull(regionDto.getName()) && regionRepository.findByName(regionDto.getName()).isEmpty()) {
            RegionEntity regionEntity = mapper.map(regionDto, RegionEntity.class);

            regionRepository.save(regionEntity);

            if (Objects.isNull(regionEntity.getId())) {
                throw new DatabaseException("Region wasn't saved.");
            }

            return mapper.map(regionEntity, RegionDto.class);
        } else {
            throw new NotValidEntityException("Region already exist or name is null");
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "regions")
    public List<RegionDto> findAll() {
        log.info("Request to DB findAll()");
        return regionRepository.findAll().stream()
                .map(e -> mapper.map(e, RegionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "region", key = "#id")
    public RegionDto findById(Integer id) {
        log.info("Request to DB findById(Long {})", id);
        return regionRepository.findById(id)
                .map(e -> mapper.map(e, RegionDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Region not found"));
    }

    @Override
    @CachePut(cacheNames = "region", key = "#regionDto.getId()")
    @CacheEvict(cacheNames = "regions", allEntries = true)
    public RegionDto update(RegionDto regionDto) {
        if (Objects.nonNull(regionDto.getName()) &&
                Objects.nonNull(regionDto.getId()) &&
                regionRepository.findByNameExcludeId(regionDto.getName(), regionDto.getId()).isEmpty()) {

            Optional<RegionEntity> updateRegion = regionRepository.findById(regionDto.getId());
            if (updateRegion.isEmpty()) throw new EntityNotFoundException("Unsuccessful update. Region not found");

            regionRepository.update(mapper.map(regionDto, RegionEntity.class));

            return regionDto;
        } else {
            throw new NotValidEntityException("Region already exist or name or id are null");
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "regions", allEntries = true),
            @CacheEvict(value = "region", key = "#id")})
    public void delete(Integer id) {
        int deleteResult = regionRepository.delete(id);

        if (deleteResult == 0) throw new DatabaseException("Region wasn't delete.");
    }

}
