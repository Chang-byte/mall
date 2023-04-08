package com.chang.gulimall.thirdparty;

import com.aliyun.oss.OSSClient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
public class GulimallThirdpartyApplicationTests {

	@Resource
	OSSClient ossClient;

	@Test
	public void test() throws FileNotFoundException {
		// 上传文件流
		InputStream inputStream = new FileInputStream("E:\\ChromeDownLoad\\lala.jpg");

		// 上传文件
		ossClient.putObject("mall-augenstern","ggirls.jpg",inputStream);

		// 关闭OSSClient。
		ossClient.shutdown();
		System.out.println("上传完成啦...");
	}

}
