package com.Picloud.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/process")
public class ProcessController {

	/**
	 * 读取单张图片
	 * 
	 * @param imageKey
	 *            图片key
	 */
	@RequestMapping(value = "/{imageKey}/read", method = RequestMethod.GET)
	public String read(@PathVariable String imageKey) {
		// TODO 读取图片，返回相应格式
		return "process/read";
	}

	/**
	 * 缩放图片
	 * 
	 * @param width
	 *            缩放宽度
	 * @param height
	 *            缩放高度
	 * @param imageKey
	 *            图片key
	 */
	@RequestMapping(value = "/{imageKey}/scale[{width},{height}]", method = RequestMethod.GET)
	public String scale(@PathVariable String imageKey, @PathVariable int width,
			@PathVariable int height) {
		// TODO 缩放处理
		return "process/scale";
	}

	/**
	 * 按宽度等比例缩放图片
	 * 
	 * @param imageKey
	 *            图片key
	 * @param width
	 *            缩放宽度
	 */
	@RequestMapping(value = "/{imageKey}/scale[{width},-]", method = RequestMethod.GET)
	public String scale(@PathVariable String imageKey, @PathVariable int width) {
		// TODO 缩放处理
		return "process/scale";
	}

	/**
	 * 裁剪图片
	 * @param imageKey
	 *            图片key
	 * @param startX
	 *            裁剪偏移坐标x
	 * @param startY
	 *            裁剪偏移坐标y
	 * @param width
	 *            裁剪宽度
	 * @param height
	 *            裁剪高度
	 * @return
	 */
	@RequestMapping(value = "/{imageKey}/crop[{startX},{startY},{width},{height}]", method = RequestMethod.GET)
	public String crop(@PathVariable String imageKey, @PathVariable int startX,
			@PathVariable int startY, @PathVariable int width,
			@PathVariable int height) {
		// TODO
		return "process/crop";
	}

	/**
	 * 图片水印
	 * @param imageKey 图片key
	 * @param startX 水印起始坐标x
	 * @param startY 水印起始坐标y
	 * @param width 水印宽度
	 * @param height 水印高度
	 * @param logo 水印Logo
	 * @param optical 水印透明度
	 * @return
	 */
	@RequestMapping(value = "/{imageKey}/watermark[{startX},{startY}],[{width},{height}],[{logo},{optical}]", method = RequestMethod.GET)
	public String watermark(@PathVariable String imageKey,
			@PathVariable int startX, @PathVariable int startY,
			@PathVariable int width, @PathVariable int height,
			@PathVariable String logo, @PathVariable int optical) {
		//TODO
		return "process/watermark";
	}
	
	/**
	 * 文字水印
	 * @param imageKey 图片key
	 * @param startX 水印起始坐标x
	 * @param startY 水印起始坐标y
	 * @param fontSize 水印文字大小
	 * @param text 水印文字内容
	 * @param color 水印文字颜色
	 * @return
	 */
	@RequestMapping(value = "/{imageKey}/textmark[{startX},{startY}],[{text},{fontSize},{color}]", method = RequestMethod.GET)
	public String textmark(@PathVariable String imageKey,
			@PathVariable int startX, @PathVariable int startY,
			@PathVariable int fontSize, 
			@PathVariable String text, @PathVariable String color){
		//TODO
		return "process/textmark";
	}
	
	// TODO 异常处理
}
