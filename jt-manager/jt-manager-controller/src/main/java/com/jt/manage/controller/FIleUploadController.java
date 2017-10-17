package com.jt.manage.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
@Controller
public class FIleUploadController {
	private static Logger logger = Logger.getLogger(FIleUploadController.class);
	/**
	 *  {"error":0,"url":"图片的保存路径","width":图片的宽度,"height":图片的高度}
	 *  文件上传的具体步骤
	 *   获取文件的全名
	 *   获取文件的类型进行判断是否为一个真实的图片 如果不是error为1
	 *   判断当前文件是否为木马(非法程序) imageBuffer
	 *   为了文件名不重复 手动拼接文件夹
	 *   生成本地磁盘路径
	 *   生成url路径
	 *   进行磁盘的写入
	 *   return
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult fileUpload(MultipartFile uploadFile){
		PicUploadResult result = new PicUploadResult();
		//获取文件的全名
		String filename = uploadFile.getOriginalFilename();
		//获取数据的类型
		String fileType = filename.substring(filename.lastIndexOf("."));
		//判断是否为一个图片类型
		if(!fileType.matches("^.*(jpg|png|gif)$")){
			//表示当前文件类型 不是图片
			result.setError(1);
		}
		//4判断是否为非法程序
		try {
			BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
			//定义真实路径
			String rpath = "D:/jt-upload/images/";
			String upath = "http://image.jt.com/images/";
			//按照年月日  yyyy/MM/dd/HH拼接文件名
			String datePath = new SimpleDateFormat("yyyy/MM/dd/HH").format(new Date());
			//获取3位随机数
			Random random = new Random();
			int rnum = random.nextInt(900)+100;
			String width = bufferedImage.getWidth()+"";
			String height = bufferedImage.getHeight()+"";
			result.setHeight(height);
			result.setWidth(width);
			//拼接本地磁盘路径
			String realPath = rpath+datePath;
			//新建文件夹
			File file = new File(realPath);
			//判断当前文件是否存在
			if(!file.exists()){
				file.mkdirs();
			}
			//11写盘操作 拼接文件全路径
			uploadFile.transferTo(new File(realPath+"/"+rnum+filename));
			String urlPath = upath+datePath+"/"+rnum+filename;
			result.setUrl(urlPath);
		
		} catch (IOException e) {
			//当前不是图片
			result.setError(1);
			e.printStackTrace();
		}
		

		
		
		
		return result;
	}
}
