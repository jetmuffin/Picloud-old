package com.Picloud.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.beanutils.PropertyUtils;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IMOps;
import org.im4java.core.Info;
import org.im4java.core.InfoException;
import org.im4java.process.Pipe;

import com.Picloud.config.SystemConfig;
import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.utils.PropertiesUtil;

public class GraphicMagick {
	Pipe pipeIn = null;
	InputStream in = null;
	InputStream infoIn = null;
	Info info = null;
	String format = null;

	
	public GraphicMagick() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造函数
	 * 
	 * @param blob
	 * @throws IOException
	 * @throws InfoException
	 */
	public GraphicMagick(byte[] imageFileData, String format) throws IOException,
			InfoException {
		this.in = new ByteArrayInputStream(imageFileData);
		this.infoIn = new ByteArrayInputStream(imageFileData);
		this.pipeIn = new Pipe(in, null);
		this.format = format;
	}

	public Pipe getPipeIn() {
		return pipeIn;
	}

	public void setPipeIn(Pipe pipeIn) {
		this.pipeIn = pipeIn;
	}

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public InputStream getInfoIn() {
		return infoIn;
	}

	public void setInfoIn(InputStream infoIn) {
		this.infoIn = infoIn;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * 缩放
	 * 
	 * @author Jet-Muffin
	 * @param width
	 *            缩放宽度
	 * @param height
	 *            缩放高度
	 * @return imageOutData 新图byte数组
	 * @throws IM4JavaException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public byte[] scaleImage(int width, int height) throws IOException,
			InterruptedException, IM4JavaException {
		IMOperation op = new IMOperation();
		byte[] imageOutData = null;
		String formatOp = format + ":-";

		try {
			op.addImage("-");
			op.thumbnail(width, height, "!");
			op.addImage(formatOp);

			// gif动图特殊处理
			if (format == "gif")
				op.coalesce();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Pipe pipeOut = new Pipe(null, out);
			ConvertCmd convert = new ConvertCmd(true);
			convert.setInputProvider(pipeIn);
			convert.setOutputConsumer(pipeOut);
			convert.run(op);

			imageOutData = out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return imageOutData;
	}

	/**
	 * 根据宽度缩放图片
	 * 
	 * @param width
	 * @return imageOutData 新图byte数组
	 * @throws MagickException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws IM4JavaException
	 */
	public byte[] scaleImage(int width) throws IOException,
			InterruptedException, IM4JavaException {
		GMOperation op = new GMOperation();
		byte[] imageOutData = null;
		String formatOp = format + ":-";

		try {
			op.addImage("-");
			op.size(width);
			op.scale(width);
			op.addImage(formatOp);

			// gif动图特殊处理
			if (format == "gif")
				op.coalesce();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Pipe pipeOut = new Pipe(null, out);
			ConvertCmd convert = new ConvertCmd(true);

//			convert.setSearchPath("/usr/local/opt/graphicsmagick/bin");
			convert.setInputProvider(pipeIn);
			convert.setOutputConsumer(pipeOut);
			convert.run(op);

			imageOutData = out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageOutData;
	}

	/**
	 * 裁剪
	 * 
	 * @param width
	 *            裁剪区域宽度
	 * @param height
	 *            裁剪区域高度
	 * @param offsetX
	 *            裁剪区域右上端点x
	 * @param offsetY
	 *            裁剪区域右上端点y
	 * @return imageOutData 新图byte数组
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws IM4JavaException
	 */
	public byte[] cropImage(int width, int height, int offsetX, int offsetY)
			throws IOException, InterruptedException, IM4JavaException {
		GMOperation op = new GMOperation();
		byte[] imageOutData = null;
		String formatOp = format + ":-";

		try {
			op.addImage("-");
			op.crop(width, height, offsetX, offsetY);
			op.addImage(formatOp);

			// gif动图特殊处理
			if (format == "gif")
				op.coalesce();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Pipe pipeOut = new Pipe(null, out);
			ConvertCmd convert = new ConvertCmd(true);
			convert.setInputProvider(pipeIn);
			convert.setOutputConsumer(pipeOut);
			convert.run(op);

			imageOutData = out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return imageOutData;
	}

	/**
	 * 文字水印
	 * 
	 * @param text
	 *            文字内容
	 * @param fontsize
	 *            字体大小
	 * @param offsetX
	 *            偏移位置X
	 * @param offsetY
	 *            偏移位置Y
	 * @param color
	 *            文字颜色十六进制
	 * @return imageOutData 图片byte数组
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws IM4JavaException
	 */
//	public byte[] textWaterMask(String text, int fontsize, String color,
//			int offsetX, int offsetY, int dissolve) throws IOException, InterruptedException,
//			IM4JavaException {
//		IMOperation op = new IMOperation();
//		String option = " text " + offsetX + "," + offsetY + " \'" + text
//				+ "\'";
//		String fontpath = SystemConfig.getSystemPath() + "/font/msyh.ttf";
//		color = "#" + color;
//		color = "rgba\\(0,0,0,0.4\\)";
//		String formatOp = format + ":-";
//		byte[] imageOutData = null;
//
//		//TODO 文字透明度
//		
//		try {
//			op.font(fontpath).gravity("northwest").pointsize(fontsize)
//					.fill(color).annotate(20,20,offsetX,offsetY,text);
//			op.addImage("-");
//			op.addImage(formatOp);
//
//			// gif动图特殊处理
//			if (format == "gif")
//				op.coalesce();
//
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			Pipe pipeOut = new Pipe(null, out);
//			ConvertCmd convert = new ConvertCmd(true);
//			convert.setInputProvider(pipeIn);
//			convert.setOutputConsumer(pipeOut);
//
//			System.out.println(op);
//			convert.run(op);
//			
//			imageOutData = out.toByteArray();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return imageOutData;
//	}


	/**
	 * @param text		文字内容
	 * @param fontsize	字体大小
	 * @param color		字体颜色
	 * @param offsetX	X偏移
	 * @param offsetY	Y偏移
	 * @param alpha		透明度
	 * @return
	 * @throws InfoException 
	 */
	public byte[] textWaterMask(String text, int fontsize, String color,
			int offsetX, int offsetY,int alpha) throws InfoException{
		String logoTmpSrc = PropertiesUtil.getValue("logoTmpSrc");
		try {
			converFontToImage(text,fontsize,color);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int width = new Info(logoTmpSrc).getImageWidth() ;
		int height = new Info(logoTmpSrc).getImageHeight() ;
		
		return imgWaterMask(logoTmpSrc, width, height,offsetX, offsetY, alpha);
	}
	
	public void textToImage(String text,int fontSize,String color){
		
	}
	/**
	 * 文字转换为图片
	 * @param text
	 * @param fontSize
	 * @param color
	 * @throws Exception
	 */
	public  void converFontToImage(String text,int fontSize,String color) throws Exception{  
		String logoTmpSrc = PropertiesUtil.getValue("logoTmpSrc");
		Font font=new Font("Microsoft YaHei",Font.BOLD,fontSize);  
		File file=new File(logoTmpSrc);  
		Rectangle2D r=font.getStringBounds(text, new FontRenderContext(AffineTransform.getScaleInstance(1, 1),false,false));  
		int unitHeight=(int)Math.floor(r.getHeight());  
		int width=(int)Math.round(r.getWidth())+1;  
		int height=unitHeight+3;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
		Graphics2D g2d = image.createGraphics();  
		image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);  
		g2d.dispose();  
		g2d = image.createGraphics();  
		g2d.setColor(Color.WHITE);  
		g2d.setStroke(new BasicStroke(1));  
		g2d.setColor(new Color(Integer.parseInt(color, 16)));
		g2d.setFont(font);  
		g2d.drawString(text, 0,font.getSize()); 	 
		ImageIO.write(image, "png", file);
	}   
	
	/**
	 * 
	 * @param logoSrc
	 *            LOGO地址
	 * @param dissolve
	 *            透明度
	 * @return
	 */
	public byte[] imgWaterMask(String logoSrc, int width, int height,
			int offsetX, int offsetY, int dissolve) {
		IMOperation op = new IMOperation();
		String formatOp = format + ":-";
		byte[] imageOutData = null;
		try {
			op.dissolve(dissolve);
			op.gravity("northwest");
			op.geometry(width, height, offsetX, offsetY);
			op.addImage(logoSrc);
			op.addImage("-");
			op.addImage(formatOp);

			// gif动图特殊处理
			if (format == "gif")
				op.coalesce();

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Pipe pipeOut = new Pipe(null, out);
			CompositeCmd convert = new CompositeCmd(true);
			convert.setInputProvider(pipeIn);
			convert.setOutputConsumer(pipeOut);
			convert.run(op);
			System.out.println(op);
			imageOutData = out.toByteArray();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageOutData;
	}
	
	/**
	 * 模糊函数
	 * 自适应像素模糊操作，在边缘处效果减弱。
	 * 采用给定的半径（radius）和标准差（sigma）进行高斯模糊处理。如果缺少标准差则默认为1
	 * @param sourceBuffer	源图片字节流
	 * @param radius	模糊半径
	 * @param sigma		标准差
	 */
	public byte[] blur(byte[] sourceBuffer,double radius,double sigma ){
		System.out.println("blur");
		IMOperation op = new IMOperation();
		byte[] imageOutData = null;
		
		op.addImage("-");
		op.blur(radius,sigma);
		op.addImage("jpg:-");
		ConvertCmd convert = new ConvertCmd(true);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Pipe pipeOut = new Pipe(null, out);
		try {
			GraphicMagick  gm = new GraphicMagick(sourceBuffer, "jpg");
			convert.setInputProvider(gm.getPipeIn());
			convert.setOutputConsumer(pipeOut);
			convert.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}
		imageOutData = out.toByteArray();
		return imageOutData;
	}
	/**
	 * 锐化函数
	 * 自适应像素锐化操作，在边缘处增强效果。
	 * 采用给定的半径（radius）和标准差（sigma）进行高斯锐化处理。如果缺少标准差则默认为1。 
	 * @param sourceBuffer	源图片字节流
	 * @param radius	锐化半径
	 * @param sigma	标准差
	 */
	public byte[] sharpen(byte[] sourceBuffer,double radius,double sigma ){
		System.out.println("sharpen");
		IMOperation op = new IMOperation();
		byte[] imageOutData = null;
			
		op.addImage("-");
		op.sharpen(radius,sigma);
		op.addImage("jpg:-");
		
		ConvertCmd convert = new ConvertCmd(true);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Pipe pipeOut = new Pipe(null, out);
		try {
			GraphicMagick  gm = new GraphicMagick(sourceBuffer, "jpg");
			convert.setInputProvider(gm.getPipeIn());
			convert.setOutputConsumer(pipeOut);
			convert.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}
		imageOutData = out.toByteArray();
		return imageOutData;
	}
	
	/**
	 * 调节函数
	 * 调整图片的亮度，饱和度，色调
	 * @param sourceBuffer
	 * @param brightness	亮度(默认值100)
	 * @param saturation	饱和度(默认值100)
	 * @param hue					色调(默认值0或360)
	 * @return
	 */
	public byte[] modulate(byte[] sourceBuffer,double brightness){
		System.out.println("brightnessContrast");
		IMOperation op = new IMOperation();
		byte[] imageOutData = null;
			
		op.addImage("-");
		op.modulate(brightness);
		op.addImage("jpg:-");
		
		ConvertCmd convert = new ConvertCmd(true);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Pipe pipeOut = new Pipe(null, out);
		try {
			GraphicMagick  gm = new GraphicMagick(sourceBuffer, "jpg");
			convert.setInputProvider(gm.getPipeIn());
			convert.setOutputConsumer(pipeOut);
			convert.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}
		imageOutData = out.toByteArray();
		return imageOutData;
	}
	
	/**
	 * 左右翻转
	 * @param sourceBuffer
	 * @return
	 */
	public byte[] flop(byte[] sourceBuffer){
		System.out.println("flop");
		IMOperation op = new IMOperation();
		byte[] imageOutData = null;
			
		op.addImage("-");
		op.flop();
		op.addImage("jpg:-");
		
		ConvertCmd convert = new ConvertCmd(true);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Pipe pipeOut = new Pipe(null, out);
		try {
			GraphicMagick  gm = new GraphicMagick(sourceBuffer, "jpg");
			convert.setInputProvider(gm.getPipeIn());
			convert.setOutputConsumer(pipeOut);
			convert.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}
		imageOutData = out.toByteArray();
		return imageOutData;
	}
	
	/**
	 * 上下翻转
	 * @param sourceBuffer
	 * @return
	 */
	public byte[] flip(byte[] sourceBuffer){
		System.out.println("flip");
		IMOperation op = new IMOperation();
		byte[] imageOutData = null;
			
		op.addImage("-");
		op.flip();
		op.addImage("jpg:-");
		
		ConvertCmd convert = new ConvertCmd(true);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Pipe pipeOut = new Pipe(null, out);
		try {
			GraphicMagick  gm = new GraphicMagick(sourceBuffer, "jpg");
			convert.setInputProvider(gm.getPipeIn());
			convert.setOutputConsumer(pipeOut);
			convert.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}
		imageOutData = out.toByteArray();
		return imageOutData;
	}
	
	/**
	 * 这是啥，我在controller里面也写了
	 * @param sourceBuffer
	 * @return
	 */
	public byte[] autoGamma(byte[] sourceBuffer){
		System.out.println("autoGamma");
		IMOperation op = new IMOperation();
		byte[] imageOutData = null;
			
		op.addImage("-");
		op.modulate(100.0, 150.0, 100.0);
		op.autoGamma();
		op.addImage("jpg:-");
		
		ConvertCmd convert = new ConvertCmd();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Pipe pipeOut = new Pipe(null, out);
		try {
			GraphicMagick  gm = new GraphicMagick(sourceBuffer, "jpg");
			convert.setInputProvider(gm.getPipeIn());
			convert.setOutputConsumer(pipeOut);
			convert.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}
		imageOutData = out.toByteArray();
		return imageOutData;
	}
	
	/**
	 * 灰度化
	 * @param sourceBuffer
	 * @return
	 */
	public byte[] gotham(byte[] sourceBuffer){
		System.out.println("gotham");
		IMOperation op = new IMOperation();
		byte[] imageOutData = null;
			
		op.addImage("-");
		op.modulate(120.0, 10.0, 100.0);
		op.fill("#222b6d");
		op.colorize(20);
		op.gamma(0.5);
		op.contrast();
		op.addImage("jpg:-");
		
		ConvertCmd convert = new ConvertCmd();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Pipe pipeOut = new Pipe(null, out);
		try {
			GraphicMagick  gm = new GraphicMagick(sourceBuffer, "jpg");
			convert.setInputProvider(gm.getPipeIn());
			convert.setOutputConsumer(pipeOut);
			convert.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}
		imageOutData = out.toByteArray();
		return imageOutData;
	}
	
	/**
	 * Lomo 效果
	 * @param sourceBuffer
	 * @param level	滤镜等级从[10240.0 , 20480.0]
	 * @return
	 */
	public byte[] lomo(byte[] sourceBuffer,double level){
		System.out.println("lomo");
		IMOperation op = new IMOperation();
		byte[] imageOutData = null;
			
		op.addImage("-");
		op.channel("R");
		op.level(level);
		op.channel("G");
		op.level(level);
		op.addImage("jpg:-");
		
		ConvertCmd convert = new ConvertCmd();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Pipe pipeOut = new Pipe(null, out);
		try {
			GraphicMagick  gm = new GraphicMagick(sourceBuffer, "jpg");
			convert.setInputProvider(gm.getPipeIn());
			convert.setOutputConsumer(pipeOut);
			convert.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}
		imageOutData = out.toByteArray();
		return imageOutData;
	}
	
	/**
	 * 素描碳笔画
	 * @param sourceBuffer
	 * @param factor		碳笔的粗细 [2 , 10]
	 * @return
	 */
	public byte[] charcoal(byte[] sourceBuffer,int factor){
		System.out.println("charcoal");
		IMOperation op = new IMOperation();
		byte[] imageOutData = null;
			
		op.addImage("-");
		op.charcoal(factor);
		op.addImage("jpg:-");
		
		ConvertCmd convert = new ConvertCmd(true);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Pipe pipeOut = new Pipe(null, out);
		try {
			GraphicMagick  gm = new GraphicMagick(sourceBuffer, "jpg");
			convert.setInputProvider(gm.getPipeIn());
			convert.setOutputConsumer(pipeOut);
			convert.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}
		imageOutData = out.toByteArray();
		return imageOutData;
	}
	
	/**
	 * 旋转函数，顺时针旋转
	 * @param sourceBuffer
	 * @param degrees		旋转角度
	 * @return
	 */
	public byte[] rotate(byte[] sourceBuffer,double degrees){
		IMOperation op = new IMOperation();
		byte[] imageOutData = null;
			
		op.addImage("-");
		op.rotate(degrees);
		op.addImage("jpg:-");
		
		ConvertCmd convert = new ConvertCmd(true);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Pipe pipeOut = new Pipe(null, out);
		try {
			GraphicMagick  gm = new GraphicMagick(sourceBuffer, "jpg");
			convert.setInputProvider(gm.getPipeIn());
			convert.setOutputConsumer(pipeOut);
			convert.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}
		imageOutData = out.toByteArray();
		return imageOutData;
	}
}	
