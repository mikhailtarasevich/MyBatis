package com.mt.repository;

import com.mt.entity.RegionEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface RegionRepository {

    @Insert(value = "INSERT INTO regions(name, abbr) VALUES(#{name}, #{abbr})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(RegionEntity entity);

    @Select(value = "SELECT * FROM regions")
    List<RegionEntity> findAll();

    @Select(value = "SELECT * FROM regions WHERE id = #{id}")
    Optional<RegionEntity> findById(@Param("id") Integer id);

    @Select(value = "SELECT * FROM regions WHERE name = #{name}")
    Optional<RegionEntity> findByName(@Param("name") String name);

    @Select(value = "SELECT * FROM regions WHERE name = #{name} AND id != id")
    Optional<RegionEntity> findByNameExcludeId(@Param("name") String name, @Param("id") Integer id);

    @Update(value = "UPDATE regions SET name = #{name}, abbr = #{abbr} WHERE id = #{id}")
    void update(RegionEntity entity);

    @Delete(value = "DELETE FROM regions WHERE id = #{id}")
    @Options()
    int delete(@Param("id") Integer id);

}
