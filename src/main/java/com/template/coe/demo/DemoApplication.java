package com.template.coe.demo;

import com.template.coe.demo.mybatis.dao.CityDao;
import com.template.coe.demo.mybatis.mapper.HotelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	private final CityDao cityDao;
	private final HotelMapper hotelMapper;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public DemoApplication(CityDao cityDao, HotelMapper hotelMapper) {
		this.cityDao = cityDao;
		this.hotelMapper = hotelMapper;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.cityDao.selectCityById(1));
		System.out.println(this.hotelMapper.selectByCityId(1));
	}
}
