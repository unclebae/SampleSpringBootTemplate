package com.template.coe.demo.mybatis.mapper;

import com.template.coe.demo.mybatis.domain.Hotel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotelMapper {

    Hotel selectByCityId(int cityId);
}
