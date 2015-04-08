package com.Picloud.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.SpaceDaoImpl;
import com.Picloud.web.dao.impl.UserDaoImpl;
import com.Picloud.web.model.Image;
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
	 * 缩放图片
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
	 * 按宽度等比例缩放图片
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
	 * 裁剪图片
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
	 * 图片水印
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
		String logoSrc = systemConfig.getImagePath() + logo;
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
	 * 文字水印
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
	@RequestMapping(value = "/{imageKey}/textmark[{startX},{startY},{text},{fontSize},{color}]", method = RequestMethod.GET)
	public void  textmark(@PathVariable String imageKey,
			@PathVariable int startX, @PathVariable int startY,
			@PathVariable int fontSize, @PathVariable String text,
			@PathVariable String color,HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		byte[] bufferOut = gm.textWaterMask(text, fontSize, color, startX, startY);
		
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
	 * 亮度调节
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
		
		//TODO 处理+输出到bufferOut
		byte[] bufferOut = null;
		
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
	 *  图片镜面翻转
	 * @param imageKey
	 * @param direction 水平或者竖直
	 * @param session
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/{imageKey}/double[{direction}]", method = RequestMethod.GET)
	public void  reverse(@PathVariable String imageKey,
			@PathVariable double direction,HttpSession session,HttpServletResponse response) throws Exception {
		
		User loginUser = (User) session.getAttribute("LoginUser");
		ImageReader imageReader = new ImageReader(infoDaoImpl);
		byte[] buffer = imageReader.readPicture(imageKey);
		GraphicMagick gm = new GraphicMagick(buffer, "jpg");
		
		//TODO 处理+输出到bufferOut
		byte[] bufferOut = null;
		
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
	 * Lomo特效
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
		
		//TODO 处理+输出到bufferOut
		byte[] bufferOut = null;
		
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
	 * 灰度化特效
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
		
		//TODO 处理+输出到bufferOut
		byte[] bufferOut = null;
		
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
	 * 素碳笔
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
		
		//TODO 处理+输出到bufferOut
		byte[] bufferOut = null;
		
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
	
	@ExceptionHandler(value=(ProcessException.class))
	public String handlerException(ProcessException e,HttpServletRequest req){
		req.setAttribute("e", e);
		return "error";
	}
}
