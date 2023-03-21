package com.chang.gulimall.product;

import com.chang.gulimall.product.entity.BrandEntity;
import com.chang.gulimall.product.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class GulimallProductApplicationTests {

	@Autowired
	private BrandService brandService;

	@Test
	 void contextLoads() {
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setDescript("苹果");
		brandEntity.setName("Apple");
		brandService.save(brandEntity);
		log.info("保存成功");
	}

	@Test
	void testList(){
		List<BrandEntity> list = brandService.list();
		list.forEach(System.out::println);
	}


}
