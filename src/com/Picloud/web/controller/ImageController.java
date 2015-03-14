package com.Picloud.web.controller;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.Picloud.web.model.Image;

@Controller
@RequestMapping("/server")
public class ImageController {
	
	/**
	 * 查看单张图片（html）
	 */
	@RequestMapping(value="/{imageKey}",method=RequestMethod.GET)
	public String show(@PathVariable String imageKey,Model model){
		//TODO 
		return "image/show";
	}
	
	/**
	 * 读取图片信息
	 * @param imageKey
	 * @return Image 图片信息JSON
	 */
	@RequestMapping(value="/{imageKey}.json",method=RequestMethod.GET)
	@ResponseBody
	public Image show(@PathVariable String imageKey){
		Image image = new Image();
		//TODO 查询Image
		return image;
	}
	

	
	/**
	 * 查看某空间下的所有图片
	 * @param space
	 */
	@RequestMapping(value="/{space}",method=RequestMethod.GET)
	public String list(@PathVariable String space){
		//TODO
		return "redirect:/server/list";
	}
	
	/**
	 * 删除单张图片
	 * @param imageKey
	 */
	@RequestMapping(value="/{imageKey}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String imageKey){
		return "redirect:/server/list";
	}
	
	//TODO 异常处理
}
