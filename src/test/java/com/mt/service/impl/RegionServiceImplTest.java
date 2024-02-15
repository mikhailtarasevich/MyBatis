package com.mt.service.impl;

import com.mt.dto.RegionDto;
import com.mt.entity.RegionEntity;
import com.mt.repository.RegionRepository;
import com.mt.service.exception.DatabaseException;
import com.mt.service.exception.EntityNotFoundException;
import com.mt.service.exception.NotValidEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {

    @Mock
    RegionRepository regionRepository;

    @Mock
    ModelMapper mapper;

    @InjectMocks
    RegionServiceImpl regionService;

    @Test
    void create_validRegion_returnsSavedDto() {
        RegionDto regionDto = RegionDto.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        RegionEntity regionEntity = RegionEntity.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        when(regionRepository.findByName(regionDto.getName())).thenReturn(Optional.empty());
        when(mapper.map(regionDto, RegionEntity.class)).thenReturn(regionEntity);
        doNothing().when(regionRepository).save(regionEntity);
        when(mapper.map(regionEntity, RegionDto.class)).thenReturn(regionDto);

        regionService.create(regionDto);

        verify(regionRepository, times(1)).save(regionEntity);
        verify(regionRepository, times(1)).findByName(regionDto.getName());
        verify(mapper, times(1)).map(regionDto, RegionEntity.class);
        verify(mapper, times(1)).map(regionEntity, RegionDto.class);
    }

    @Test
    void create_regionWithTheSameNameAlreadyExist_returnsNotValidEntityException() {
        RegionDto regionDto = RegionDto.builder()
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        when(regionRepository.findByName(regionDto.getName())).thenReturn(Optional.of(new RegionEntity()));

        assertThrows(NotValidEntityException.class, () -> regionService.create(regionDto));

        verify(regionRepository, times(1)).findByName(regionDto.getName());
    }

    @Test
    void create_regionNameIsNull_returnsNotValidEntityException() {
        RegionDto regionDto = RegionDto.builder()
                .withAbbr("MSK")
                .build();

        assertThrows(NotValidEntityException.class, () -> regionService.create(regionDto));
    }

    @Test
    void create_validRegionButDBProblem_returnsSavedDto() {
        RegionDto regionDto = RegionDto.builder()
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        RegionEntity regionEntity = RegionEntity.builder()
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        when(regionRepository.findByName(regionDto.getName())).thenReturn(Optional.empty());
        when(mapper.map(regionDto, RegionEntity.class)).thenReturn(regionEntity);
        doNothing().when(regionRepository).save(regionEntity);

        assertThrows(DatabaseException.class, () -> regionService.create(regionDto));

        verify(regionRepository, times(1)).save(regionEntity);
        verify(regionRepository, times(1)).findByName(regionDto.getName());
        verify(mapper, times(1)).map(regionDto, RegionEntity.class);
    }

    @Test
    void findAll_nothing_returnsList() {
        RegionDto regionDto = RegionDto.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        RegionEntity regionEntity = RegionEntity.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        List<RegionEntity> entityList = Collections.singletonList(regionEntity);

        when(regionRepository.findAll()).thenReturn(entityList);
        when(mapper.map(regionEntity, RegionDto.class)).thenReturn(regionDto);

        List<RegionDto> resultList = regionService.findAll();

        assertEquals(1, resultList.size());
        verify(regionRepository, times(1)).findAll();
        verify(mapper, times(1)).map(regionEntity, RegionDto.class);
    }

    @Test
    void findById_validId_returnsDto() {
        RegionDto regionDto = RegionDto.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        RegionEntity regionEntity = RegionEntity.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        int id = 1;

        when(regionRepository.findById(id)).thenReturn(Optional.of(regionEntity));
        when(mapper.map(regionEntity, RegionDto.class)).thenReturn(regionDto);

        RegionDto result = regionService.findById(id);

        assertEquals(regionDto, result);
        verify(regionRepository, times(1)).findById(id);
        verify(mapper, times(1)).map(regionEntity, RegionDto.class);
    }

    @Test
    void findById_nonExistingId_returnsEntityNotFoundException() {
        int id = 1;

        when(regionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> regionService.findById(1));

        verify(regionRepository, times(1)).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void update_validRegion_returnsDto() {
        RegionDto regionDto = RegionDto.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        RegionEntity regionEntityForUpdate = RegionEntity.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        RegionEntity regionEntity = RegionEntity.builder()
                .withId(1)
                .withName("Mascaw")
                .withAbbr("MSK")
                .build();

        when(regionRepository.findByNameExcludeId(regionDto.getName(), regionDto.getId())).thenReturn(Optional.empty());
        when(regionRepository.findById(regionDto.getId())).thenReturn(Optional.of(regionEntity));
        when(mapper.map(regionDto, RegionEntity.class)).thenReturn(regionEntityForUpdate);
        doNothing().when(regionRepository).update(regionEntityForUpdate);

        regionService.update(regionDto);

        verify(regionRepository, times(1)).findByNameExcludeId(regionDto.getName(), regionDto.getId());
        verify(regionRepository, times(1)).findById(regionDto.getId());
        verify(mapper, times(1)).map(regionDto, RegionEntity.class);
        verify(regionRepository, times(1)).update(regionEntityForUpdate);
    }

    @Test
    void update_regionNameNull_returnsNotValidEntityException() {
        RegionDto regionDto = RegionDto.builder()
                .withId(1)
                .withAbbr("MSK")
                .build();

        assertThrows(NotValidEntityException.class, () -> regionService.update(regionDto));

        verifyNoInteractions(regionRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    void update_regionIdNull_returnsNotValidEntityException() {
        RegionDto regionDto = RegionDto.builder()
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        assertThrows(NotValidEntityException.class, () -> regionService.update(regionDto));

        verifyNoInteractions(regionRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    void update_regionNameAlreadyExist_returnsNotValidEntityException() {
        RegionDto regionDto = RegionDto.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        RegionEntity regionEntity = RegionEntity.builder()
                .withId(2)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        when(regionRepository.findByNameExcludeId(regionDto.getName(), regionDto.getId())).thenReturn(Optional.of(regionEntity));

        assertThrows(NotValidEntityException.class, () -> regionService.update(regionDto));

        verify(regionRepository, times(1)).findByNameExcludeId(regionDto.getName(), regionDto.getId());
        verifyNoInteractions(mapper);
    }

    @Test
    void update_regionNotFoundInDb_returnsEntityNotFoundException() {
        RegionDto regionDto = RegionDto.builder()
                .withId(1)
                .withName("Moscow")
                .withAbbr("MSK")
                .build();

        when(regionRepository.findByNameExcludeId(regionDto.getName(), regionDto.getId())).thenReturn(Optional.empty());
        when(regionRepository.findById(regionDto.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> regionService.update(regionDto));

        verify(regionRepository, times(1)).findByNameExcludeId(regionDto.getName(), regionDto.getId());
        verify(regionRepository, times(1)).findById(regionDto.getId());
        verifyNoInteractions(mapper);
    }

    @Test
    void delete_validId_returnsNothing() {
        int id = 1;

        when(regionRepository.delete(id)).thenReturn(1);

        regionService.delete(id);

        verify(regionRepository, times(1)).delete(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void delete_nonExistingId_returnsDatabaseException() {
        int id = 1;

        when(regionRepository.delete(id)).thenReturn(0);

        assertThrows(DatabaseException.class, () -> regionService.delete(id));

        verify(regionRepository, times(1)).delete(id);
        verifyNoInteractions(mapper);
    }

}
