package com.mt.controller;

import com.mt.dto.RegionDto;
import com.mt.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regions")
@Api(tags = "Контроллер справочника регионов")
public class RegionController {

    private final RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    @ApiOperation(value = "Получить весь справочник регионов")
    List<RegionDto> getAllRegions() {
        return regionService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получить регион по идентификатору")
    RegionDto getById(@ApiParam(value = "Идентификатор региона", example = "2", required = true)
                      @PathVariable("id") Integer id) {
        return regionService.findById(id);
    }

    @PostMapping
    @ApiOperation(value = "Добавить новый регион")
    RegionDto createRegion(@ApiParam(value = "Идентификатор региона", required = true)
                           @RequestBody RegionDto regionDto) {
        return regionService.create(regionDto);
    }

    @PutMapping
    @ApiOperation(value = "Изменить существующий регион")
    RegionDto updateRegion(@ApiParam(value = "Регион на изменение", required = true)
                           @RequestBody RegionDto regionDto) {
        return regionService.update(regionDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удалить существующий регион")
    void deleteRegion(@ApiParam(value = "Идентификатор удаляемого региона", example = "2", required = true)
                      @PathVariable("id") Integer id) {
        regionService.delete(id);
    }

}
