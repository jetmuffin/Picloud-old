package com.Picloud.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.Picloud.exception.ImageException;
import com.Picloud.exception.UserException;
import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.hdfs.MapfileHandler;
import com.Picloud.image.ImageDeleter;
import com.Picloud.image.ImageReader;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.InfoDaoImpl;
import com.Picloud.web.dao.impl.SpaceDaoImpl;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Space;
import com.Picloud.web.model.User;

@Controller
@RequestMapping("/server")
public class ImageController {
	@Autowired
	private InfoDaoImpl infoDaoImpl;
	@Autowired
	private ImageDaoImpl imageDaoImpl;
	@Autowired
	private SpaceDaoImpl spaceDaoImpl;
	private String module = "图片服务器";
	
	/**
	 * 查看单张图片（image）
	 * @throws Exception 
	 */
	@RequestMapping(value="/{imageKey}",method=RequestMethod.GET)
	public void show(@PathVariable String imageKey,HttpSession session,HttpServletResponse response) throws Exception{
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
			PrintWriter out = response.getWriter();
			out.println("Please input the correct image name.");
		}
	}
	
	/**
	 * 查看图片（html）
	 * @param imageKey
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/{imageKey}/view",method=RequestMethod.GET)
	public String show(@PathVariable String imageKey,HttpSession session,Model model) throws Exception {
		
		model.addAttribute("module",module);
		model.addAttribute("action", "图片空间");
		
		User LoginUser = (User) session.getAttribute("LoginUser");
		Image image = imageDaoImpl.find(imageKey);
		Space space = spaceDaoImpl.find(image.getSpace());
		
		
		//其他图片
		List<Image> otherImages = imageDaoImpl.getOtherImages(image.getSpace(), image.getName(),6);
		//所有空间
		List<Space> spaces = spaceDaoImpl.load(LoginUser.getUid());
		if(otherImages!=null){
			model.addAttribute("otherImages",otherImages);
		}
		model.addAttribute("image",image);
		model.addAttribute("space",space);
		model.addAttribute("spaces",spaces);
		model.addAttribute("activeSpace", space);
		model.addAttribute("activeImage",image);
		return "server/show";
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
		image = imageDaoImpl.find(imageKey);
		return image;
	}

	/**
	 * 删除单张图片
	 * @param imageKey
	 * @throws Exception 
	 */
	@RequestMapping(value="/{imageKey}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String imageKey) throws Exception{
		
		Image image = imageDaoImpl.find(imageKey);
		ImageDeleter imageDeleter = new ImageDeleter(infoDaoImpl);
		boolean flag = imageDeleter.deletePicture(image);
		if(!flag) {
			throw new ImageException("删除失败");
		} else {
			return "redirect:/space/spaces";	
		}
	}
	
	/**
	 * 设置封面
	 * @param spaceKey
	 * @param imageKey
	 */
	@RequestMapping(value="/{spaceKey}/cover[{imageKey}]",method=RequestMethod.GET)
	public String  cover(@PathVariable String spaceKey,@PathVariable String imageKey) {
		
		Space space = spaceDaoImpl.find(spaceKey);
		space.setCover(imageKey);
		spaceDaoImpl.update(space);
		return "redirect:/space/spaces";
	}
	
	@ExceptionHandler(value=(ImageException.class))
	public String handlerException(ImageException e,HttpServletRequest req){
		req.setAttribute("e", e);
		return "error";
	}
}
