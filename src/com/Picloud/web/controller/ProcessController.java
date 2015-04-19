package com.Picloud.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.im4java.core.IM4JavaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Picloud.config.SystemConfig;
import com.Picloud.exception.ProcessException;
import com.Picloud.exception.UserException;
import com.Picloud.image.GraphicMagick;
import com.Picloud.image.ImageReader;
import com.Picloud.image.ImageUpdate;
import com.Picloud.utils.PropertiesUtil;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.LogDaoImpl;
import com.Picloud.web.dao.impl.SpaceDaoImpl;
import com.Picloud.web.dao.impl.UserDaoImpl;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Log;
import com.Picloud.web.model.Space;
import com.Picloud.web.model.User;

@Controller
@RequestMapping("/process")
public class ProcessController {
	@Autowired
	private InfoDaoImpl infoDaoImpl;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private ImageDaoImpl mImageDaoImpl;
	@Autowired
	private UserDaoImpl mUserDaoImpl;
	@Autowired
	private SpaceDaoImpl  mSpaceDaoImpl;
	@Autowired
	private LogDaoImpl mLogDaoImpl;
	private String module = "应用中心";
	
	
	/**
	 *  应用列表
	 */
	@RequestMapping(value="/tools",method=RequestMethod.GET)
	public String  list(Model model){
		model.addAttribute("module", module);
		return "process/list";
	}
	
	
	@RequestMapping(value="/scale",method=RequestMethod.GET)
	public String  scale(Model model,HttpSession session){
		model.addAttribute("module", module);
		model.addAttribute("action", "缩放");

		User loginUser = (User) session.getAttribute("LoginUser");
		List<Space> spaces = mSpaceDaoImpl.load(loginUser.getUid());
		model.addAttribute("spaces",spaces);
		return "process/scale";
	}
	
	@RequestMapping(value="/crop",method=RequestMethod.GET)
	public String  crop(Model model,HttpSession session){
		model.addAttribute("module", module);
		model.addAttribute("action", "裁剪");
		
		User loginUser = (User) session.getAttribute("LoginUser");
		List<Space> spaces = mSpaceDaoImpl.load(loginUser.getUid());
		model.addAttribute("spaces",spaces);
		return "process/crop";
	}
	
	@RequestMapping(value="/watermark",method=RequestMethod.GET)
	public String  watermark(Model model,HttpSession session){
		model.addAttribute("module", module);
		model.addAttribute("action", "图片水印");
		
		User loginUser = (User) session.getAttribute("LoginUser");
		List<Space> spaces = mSpaceDaoImpl.load(loginUser.getUid());
		model.addAttribute("spaces",spaces);
		return "process/watermark";
	}
	
	@RequestMapping(value="/textmark",method=RequestMethod.GET)
	public String  textmark(Model model,HttpSession session){
		model.addAttribute("module", module);
		model.addAttribute("action", "文字水印");
		
		User loginUser = (User) session.getAttribute("LoginUser");
		List<Space> spaces = mSpaceDaoImpl.load(loginUser.getUid());
		model.addAttribute("spaces",spaces);
		return "process/watermark";
	}
	
	/**
	 * 缩放图片查看
	 * 
	 * @param width
	 *            缩放宽度
	 * @param height
	 *            缩放高度
	 * @param imageKey
	 *            图片key
	 * @throws Exception
	 */
	@RequestMapping(value = "/{imageKey}/scale[{width},{height}]", method = RequestMethod.GET)
	public void scale(@PathVariable String imageKey, @PathVariable int width,
			@PathVariable int height, HttpSession session,
			HttpServletResponse response) throws Exception {

		Image image = mImageDaoImpl.find(imageKey);
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, image.getType());
		byte[] bufferOut = gm.scaleImage(width, height);

		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();
			
			output.close();

		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}

	/**
	 * 未修改的默认图片
	 * 
	 */
	@RequestMapping(value = "/{imageKey}/default", method = RequestMethod.GET)
	public void mdefault(@PathVariable String imageKey,HttpSession session,
			HttpServletResponse response) throws Exception {

		Image image = mImageDaoImpl.find(imageKey);
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);

		if (buffer != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(buffer);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();
			
			output.close();

		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 缩放图片保存
	 * 
	 * @param width
	 *            缩放宽度
	 * @param height
	 *            缩放高度
	 * @param imageKey
	 *            图片key
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/{imageKey}/scale[{width},{height}]", method = RequestMethod.GET)
	public void scaleUpdate(@PathVariable String imageKey, @PathVariable int width,
			@PathVariable int height, HttpSession session,
			HttpServletResponse response) throws Exception {

		Image image = mImageDaoImpl.find(imageKey);
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, image.getType());
		byte[] bufferOut = gm.scaleImage(width, height);
		System.out.println(bufferOut.length);
		if (bufferOut != null) {
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "按照宽度："+width+"高度："+
			height+"缩放了图片"+image.getName());
			mLogDaoImpl.add(log);
			
			response.setStatus(200);
			
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 按宽度等比例缩放图片查看
	 * 
	 * @param imageKey
	 *            图片key
	 * @param width
	 *            缩放宽度
	 * @throws Exception 
	 */
	@RequestMapping(value = "/{imageKey}/scale[{width},-]", method = RequestMethod.GET)
	public void  scale(@PathVariable String imageKey, @PathVariable int width,
			HttpSession session, HttpServletResponse response) throws Exception {

		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		Image image = mImageDaoImpl.find(imageKey);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, image.getType());
		byte[] bufferOut = gm.scaleImage(width);

		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();

			output.close();
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 按宽度等比例缩放图片保存
	 * 
	 * @param imageKey
	 *            图片key
	 * @param width
	 *            缩放宽度
	 * @throws Exception 
	 */
	@RequestMapping(value = "/update/{imageKey}/scale[{width},-]", method = RequestMethod.GET)
	public void  scaleUpdate(@PathVariable String imageKey, @PathVariable int width,
			HttpSession session, HttpServletResponse response) throws Exception {

		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		Image image = mImageDaoImpl.find(imageKey);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, image.getType());
		byte[] bufferOut = gm.scaleImage(width);

		if (bufferOut != null) {
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "按照宽度："+width+"缩放了图片"+image.getName());
			mLogDaoImpl.add(log);
			
			response.setStatus(200);
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}

	/**
	 * 裁剪图片查看
	 * 
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
	 * @throws Exception 
	 */
	@RequestMapping(value = "/{imageKey}/crop[{startX},{startY},{width},{height}]", method = RequestMethod.GET)
	public void crop(@PathVariable String imageKey, @PathVariable int startX,
			@PathVariable int startY, @PathVariable int width,
			@PathVariable int height,HttpServletResponse response,HttpSession session) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.cropImage(width, height, startX, startY);
		
		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();

			output.close();
			
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 裁剪图片保存
	 * 
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
	 * @throws Exception 
	 */
	@RequestMapping(value = "/update/{imageKey}/crop[{startX},{startY},{width},{height}]", method = RequestMethod.GET)
	public void cropUpdate(@PathVariable String imageKey, @PathVariable int startX,
			@PathVariable int startY, @PathVariable int width,
			@PathVariable int height,HttpServletResponse response,HttpSession session) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.cropImage(width, height, startX, startY);
		
		if (bufferOut != null) {
			
			Image image = mImageDaoImpl.find(imageKey);
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() + "起始坐标："+startX+"，"+
			startY+"按照宽度："+width+"高度："+height+"裁剪了图片"+image.getName());
			mLogDaoImpl.add(log);
			
			response.setStatus(200);
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}

	/**
	 * 图片水印查看
	 * 
	 * @param imageKey
	 *            图片key
	 * @param startX
	 *            水印起始坐标x
	 * @param startY
	 *            水印起始坐标y
	 * @param width
	 *            水印宽度
	 * @param height
	 *            水印高度
	 * @param logo
	 *            水印Logo
	 * @param optical
	 *            水印透明度
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/{imageKey}/watermark[{startX},{startY},{width},{height},{logo},{optical}]", method = RequestMethod.GET)
	public void  watermark(@PathVariable String imageKey,
			@PathVariable int startX, @PathVariable int startY,
			@PathVariable int width, @PathVariable int height,
			@PathVariable String logo, @PathVariable int optical,HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		String logoSrc = PropertiesUtil.getValue("imagePath") + logo;
		byte[] bufferOut = gm.imgWaterMask(logoSrc, width, height, startX, startY, optical);
		
		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();

			output.close();
			
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 图片水印保存
	 * 
	 * @param imageKey
	 *            图片key
	 * @param startX
	 *            水印起始坐标x
	 * @param startY
	 *            水印起始坐标y
	 * @param width
	 *            水印宽度
	 * @param height
	 *            水印高度
	 * @param logo
	 *            水印Logo
	 * @param optical
	 *            水印透明度
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/update/{imageKey}/watermark[{startX},{startY},{width},{height},{logo},{optical}]", method = RequestMethod.GET)
	public void  watermarkUpdate(@PathVariable String imageKey,
			@PathVariable int startX, @PathVariable int startY,
			@PathVariable int width, @PathVariable int height,
			@PathVariable String logo, @PathVariable int optical,HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		String logoSrc = PropertiesUtil.getValue("imagePath") + logo;
		byte[] bufferOut = gm.imgWaterMask(logoSrc, width, height, startX, startY, optical);
		
		if (bufferOut != null) {
			
			Image image = mImageDaoImpl.find(imageKey);
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() +"按照Logo坐标："+startX+"，"+startY+ 
					"Logo宽度："+width+"Logo高度："+height+"透明度"+optical+"添加了水印图片"+image.getName());
			mLogDaoImpl.add(log);
			
			response.setStatus(200);
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}

	/**
	 * 文字水印查看
	 * 
	 * @param imageKey
	 *            图片key
	 * @param startX
	 *            水印起始坐标x
	 * @param startY
	 *            水印起始坐标y
	 * @param fontSize
	 *            水印文字大小
	 * @param text
	 *            水印文字内容
	 * @param color
	 *            水印文字颜色
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/{imageKey}/textmark[{startX},{startY},{text},{fontSize},{color},{dissolve}]", method = RequestMethod.GET)
	public void  textmark(@PathVariable String imageKey,
			@PathVariable int startX, @PathVariable int startY,
			@PathVariable int fontSize, @PathVariable String text,
			@PathVariable String color,@PathVariable int dissolve,HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		Image image = mImageDaoImpl.find(imageKey);
		ImageReader imageReader = new ImageReader(infoDaoImpl);		
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, image.getType());
		 text = new String(text.getBytes("iso8859-1"),
					"utf-8");
		byte[] bufferOut = gm.textWaterMask(text, fontSize, color, startX, startY,dissolve);
		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();

			output.close();
			
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 文字水印保存
	 * 
	 * @param imageKey
	 *            图片key
	 * @param startX
	 *            水印起始坐标x
	 * @param startY
	 *            水印起始坐标y
	 * @param fontSize
	 *            水印文字大小
	 * @param text
	 *            水印文字内容
	 * @param color
	 *            水印文字颜色
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/update/{imageKey}/textmark[{startX},{startY},{text},{fontSize},{color}]", method = RequestMethod.GET)
	public void  textmarkUpdate(@PathVariable String imageKey,
			@PathVariable int startX, @PathVariable int startY,
			@PathVariable int fontSize, @PathVariable String text,
			@PathVariable String color,@PathVariable int alpha,HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		Image image = mImageDaoImpl.find(imageKey);
		ImageReader imageReader = new ImageReader(infoDaoImpl);		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, image.getType());
		byte[] bufferOut = gm.textWaterMask(text, fontSize, color, startX, startY,alpha);
		
		if (bufferOut != null) {

			
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() +"按照坐标："+startX+"，"+startY+ "大小："+fontSize+"颜色："+color+"添加了水印文字"+text);
			mLogDaoImpl.add(log);
			
			response.setStatus(200);
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 亮度调节查看
	 * @param imageKey
	 * @param brightness
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/{imageKey}/brightness[{brightness}]", method = RequestMethod.GET)
	public void  brightness(@PathVariable String imageKey,
			@PathVariable double brightness,HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.modulate(buffer, brightness);
		
		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();

			output.close();
			
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 亮度调节保存
	 * @param imageKey
	 * @param brightness
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/{imageKey}/brightness[{brightness}]", method = RequestMethod.GET)
	public void  brightnessUpdate(@PathVariable String imageKey,
			@PathVariable double brightness,HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.modulate(buffer, brightness);
		
		if (bufferOut != null) {
			
			Image image = mImageDaoImpl.find(imageKey);
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() +"按照亮度："+brightness+"调节图片"+image.getName());
			mLogDaoImpl.add(log);
			
			response.setStatus(200);
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 *  图片旋转查看
	 * @param imageKey
	 * @param direction 水平或者竖直
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/{imageKey}/rotate[{angle}]", method = RequestMethod.GET)
	public void  rotate(@PathVariable String imageKey,
			@PathVariable int angle,HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.rotate(buffer, angle);
		
		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();

			output.close();
			
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 *  图片旋转保存
	 * @param imageKey
	 * @param direction 水平或者竖直
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/{imageKey}/rotate[{angle}]", method = RequestMethod.GET)
	public void  rotateUpdate(@PathVariable String imageKey,
			@PathVariable int angle,HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.rotate(buffer, angle);
		
		if (bufferOut != null) {
			
			Image image = mImageDaoImpl.find(imageKey);
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() +"按照角度："+angle+"旋转图片"+image.getName());
			mLogDaoImpl.add(log);
			
			response.setStatus(200);
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 *  图片镜面翻转查看
	 * @param imageKey
	 * @param direction 水平或者竖直 1水平，0竖直
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/{imageKey}/reverse[{direction}]", method = RequestMethod.GET)
	public void  reverse(@PathVariable String imageKey,
			@PathVariable String direction,HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = null;
		if(direction.equals("1")){
			bufferOut = gm.flop(buffer);
		}else if(direction.equals("0")){
			bufferOut = gm.flip(buffer);
		}
		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();

			output.close();
			
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 *  图片镜面翻转保存
	 * @param imageKey
	 * @param direction 水平或者竖直 1水平，0竖直
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/{imageKey}/reverse[{direction}]", method = RequestMethod.GET)
	public void  reverseUpdate(@PathVariable String imageKey,
			@PathVariable String direction,HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = null;
		if(direction.equals("1")){
			bufferOut = gm.flop(buffer);
		}else if(direction.equals("0")){
			bufferOut = gm.flip(buffer);
		}
		if (bufferOut != null) {
			
			Image image = mImageDaoImpl.find(imageKey);
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() +"镜面翻转图片"+image.getName());
			mLogDaoImpl.add(log);
			
			response.setStatus(200);
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	
	/**
	 * Lomo特效查看
	 * @param imageKey
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/{imageKey}/lomo", method = RequestMethod.GET)
	public void  lomo(@PathVariable String imageKey,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.lomo(buffer, 20480.0);
		
		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();

			output.close();
			
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * Lomo特效保存
	 * @param imageKey
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/{imageKey}/lomo", method = RequestMethod.GET)
	public void  lomoUpdate(@PathVariable String imageKey,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.lomo(buffer, 20480.0);
		
		if (bufferOut != null) {
			
			Image image = mImageDaoImpl.find(imageKey);
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() +"Lomo处理了图片"+image.getName());
			mLogDaoImpl.add(log);
			
			response.setStatus(200);
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 灰度化特效查看
	 * @param imageKey
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/{imageKey}/gotham", method = RequestMethod.GET)
	public void  gotham(@PathVariable String imageKey,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.gotham(buffer);
		
		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();

			output.close();
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 灰度化特效保存
	 * @param imageKey
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/{imageKey}/gotham", method = RequestMethod.GET)
	public void  gothamUpdate(@PathVariable String imageKey,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.gotham(buffer);
		
		if (bufferOut != null) {
			
			Image image = mImageDaoImpl.find(imageKey);
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() +"灰化图片"+image.getName());
			mLogDaoImpl.add(log);
			
			response.setStatus(200);
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 素碳笔查看
	 * @param imageKey
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/{imageKey}/charcoal", method = RequestMethod.GET)
	public void  charcoal(@PathVariable String imageKey,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.charcoal(buffer, 3);
		
		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数autoGamma
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();

			output.close();
			
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 素碳笔保存
	 * @param imageKey
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/{imageKey}/charcoal", method = RequestMethod.GET)
	public void  charcoalUpdate(@PathVariable String imageKey,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.charcoal(buffer, 3);
		
		if (bufferOut != null) {
			
			Image image = mImageDaoImpl.find(imageKey);
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() +"使用素炭笔处理了图片"+image.getName());
			mLogDaoImpl.add(log);
			
			response.setStatus(200);
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	
	/**
	 * 这是啥我也不知道查看
	 * @param imageKey
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/{imageKey}/autoGamma", method = RequestMethod.GET)
	public void  autoGamma(@PathVariable String imageKey,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.autoGamma(buffer);
		
		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();

			output.close();
			
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 这是啥我也不知道保存
	 * @param imageKey
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/{imageKey}/autoGamma", method = RequestMethod.GET)
	public void  autoGammaUpdate(@PathVariable String imageKey,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.autoGamma(buffer);
		
		if (bufferOut != null) {
			
			Image image = mImageDaoImpl.find(imageKey);
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			
			response.setStatus(200);
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	
	/**
	 * 锐化查看
	 * @param imageKey
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/{imageKey}/sharpen", method = RequestMethod.GET)
	public void  sharpen(@PathVariable String imageKey,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.sharpen(buffer, 2, 1);
		
		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();

			output.close();
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 锐化保存
	 * @param imageKey
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/{imageKey}/sharpen", method = RequestMethod.GET)
	public void  sharpenUpdate(@PathVariable String imageKey,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.sharpen(buffer, 2, 1);
		
		if (bufferOut != null) {
			
			Image image = mImageDaoImpl.find(imageKey);
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() +"锐化了图片"+image.getName());
			mLogDaoImpl.add(log);
			
			response.setStatus(200);
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 模糊查看
	 * @param imageKey
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/{imageKey}/blur", method = RequestMethod.GET)
	public void  blur(@PathVariable String imageKey,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.blur(buffer, 25, 5);
		
		if (bufferOut != null) {
			// 输出byte为图片
			InputStream imageIn = new ByteArrayInputStream(bufferOut);
			BufferedInputStream bis = new BufferedInputStream(imageIn);// 输入缓冲流
			OutputStream output = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
			byte data[] = new byte[4096];// 缓冲字节数
			int size = 0;
			size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bis.close();
			bos.flush();// 清空输出缓冲流
			bos.close();

			output.close();
			
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	/**
	 * 模糊保存
	 * @param imageKey
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/{imageKey}/blur", method = RequestMethod.GET)
	public void  blurUpdate(@PathVariable String imageKey,
			HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.blur(buffer, 25, 5);
		
		if (bufferOut != null) {
			
			Image image = mImageDaoImpl.find(imageKey);
			ImageUpdate imageUpdate=new ImageUpdate(infoDaoImpl);
			imageUpdate.updateImage(bufferOut, loginUser.getUid(), image.getSpace(),imageKey);
			
			Log log=new Log(loginUser.getUid(),loginUser.getNickname() +"模糊了图片"+image.getName());
			mLogDaoImpl.add(log);
			
			response.setStatus(200);
		} else {
			throw new ProcessException("请输入正确的参数！");
		}
	}
	@ExceptionHandler(value=(ProcessException.class))
	public String handlerException(ProcessException e,HttpServletRequest req){
		req.setAttribute("e", e);
		return "error";
	}
}
