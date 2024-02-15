package com.mt.service;

import com.mt.dto.RegionDto;

import java.util.List;

public interface RegionService {

    RegionDto create(RegionDto regionDto);

    List<RegionDto> findAll();

    RegionDto findById(Integer id);

    RegionDto update(RegionDto regionDto);

    void delete(Integer id);

}
