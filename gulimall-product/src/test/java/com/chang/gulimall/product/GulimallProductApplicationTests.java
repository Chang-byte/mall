package com.chang.gulimall.product;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.chang.gulimall.product.entity.BrandEntity;
import com.chang.gulimall.product.service.BrandService;
import com.chang.gulimall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class GulimallProductApplicationTests {

	@Autowired
	BrandService brandService;

	@Resource
	OSSClient ossClient;

	@Autowired
	CategoryService categoryService;

	@Test
	public void testGetCatelogPath(){
		Long[] catelogPath = categoryService.getCatelogPath(166L);
		System.out.println(Arrays.asList(catelogPath)); // [1, 22, 166]
	}

	@Test
	public void test() throws FileNotFoundException {
		// 上传文件流
		InputStream inputStream = new FileInputStream("E:\\ChromeDownLoad\\63489ff275566b201222.jpg");

		// 上传文件
		ossClient.putObject("mall-augenstern","rabbit.jpg",inputStream);

		// 关闭OSSClient。
		ossClient.shutdown();
		System.out.println("上传完成啦...");
	}

	@Test
	public void testUpload() throws FileNotFoundException {
		// yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
		String endpoint = "oss-cn-hangzhou.aliyuncs.com";
		// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
		String accessKeyId = "LTAI5tC1KrYt7CKVJeR49Qyt";
		String accessKeySecret = "wyqjliXxT0eXGSYIWOuOQHV0PWojwm";

		// 创建ClientConfiguration。ClientConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
		ClientBuilderConfiguration conf = new ClientBuilderConfiguration();

		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);

		// 上传文件流
		InputStream inputStream = new FileInputStream("\u202AE:\\BaiduNetdiskDownload\\谷粒商城\\Guli Mall(包含代码、课件、sql)\\Guli Mall\\分布式基础\\资源\\pics\\73ab4d2e818d2211.jpg");

		// 上传文件
		ossClient.putObject("mall-augenstern","phone.jpg",inputStream);

		// 关闭OSSClient。
		ossClient.shutdown();
		System.out.println("上传完成...");
	}

	@Test
	public void contextLoads() {
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setDescript("苹果");
		brandEntity.setName("Apple");
		brandService.save(brandEntity);
		log.info("保存成功");
	}

	@Test
	public void testList(){
		List<BrandEntity> list = brandService.list();
		list.forEach(System.out::println);
	}


}
