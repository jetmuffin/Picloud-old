package com.Picloud.image;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.Info;
import org.im4java.core.InfoException;
import org.im4java.process.Pipe;

import com.Picloud.hdfs.HdfsHandler;


public class HdImageCut {
	private byte[] tmpBuffer;
	/**
	 * 图片压缩函数
	 * @param srcPath	源图路径
	 * @param tmPath	临时路径
	 * @param width		压缩尺寸
	 * @throws IOException 
	 * @throws InfoException 
	 */
	public  byte[] resize(byte[] sourceBuffer,int width) throws InfoException, IOException{
		System.out.println("resize");
		IMOperation op = new IMOperation();
		byte[] imageOutData = null;
		GraphicMagick  gm = new GraphicMagick(sourceBuffer, "jpg");
		try {
			op.addImage("-");
			op.resize(width,null);
			op.addImage("jpg:-");
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Pipe pipeOut = new Pipe(null, out);
			ConvertCmd convert = new ConvertCmd(true);
			convert.setInputProvider(gm.getPipeIn());
			convert.setOutputConsumer(pipeOut);
			convert.run(op);
			imageOutData = out.toByteArray();
		
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}
			return imageOutData;
	}
	

	/**
	 * @param tmpath	压缩后的图片暂存路径
	 * @param despath	存储目标地址
	 * @param lv		当前层级
	 * @param i_size	元图尺寸
	 * @throws Exception 
	 */
	public  void cutmatix(String hdfsPath,String imageName,int width,int height,int lv,int i_size) throws Exception{
		
		int w_max = (int) Math.pow(2,lv);
		int h_max = height/i_size;
		if(height%i_size != 0)h_max++;
		for(int i=0;i<w_max;i++){
			System.out.println("now cut is "+lv+" "+i);
			for(int j=0;j<h_max;j++){
				try{
					GraphicMagick gm = new GraphicMagick(this.tmpBuffer, "jpg");
				IMOperation op = new IMOperation();
				byte [] imageOutData = null;
				op.addImage("-");
				op.crop(i_size,i_size,i*i_size,j*i_size);
				op.addImage("jpg:-");
//				ConvertCmd convert = new ConvertCmd(true);
//					convert.run(op, img, despath+lv+"/"+j+"_"+i);
				String filePath = hdfsPath + '/'  + imageName + "_files" +  "/"+lv+"/"+j+"_"+i+".jpg";
				System.out.println(filePath);
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				Pipe pipeOut = new Pipe(null, out);
				ConvertCmd convert = new ConvertCmd(true);
				convert.setInputProvider(gm.getPipeIn());
				convert.setOutputConsumer(pipeOut);
				convert.run(op);
				
				imageOutData = out.toByteArray();
				InputStream in = new ByteArrayInputStream(imageOutData);
				//上传到HDFS
				HdfsHandler hdfsHandler = new HdfsHandler();
				filePath = hdfsPath + '/'  + imageName + "_files" +  "/"+lv+"/"+j+"_"+i+".jpg";
				System.out.println(filePath);
				hdfsHandler.upLoad(in, filePath);
				
				} catch (IOException | InterruptedException | IM4JavaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public  void resizeAll(byte[] sourceBuffer,String hdfsPath,String imageName,int width,int height,int min_lv,int i_size) throws Exception{

		int max_num = width/i_size;
		int max_lv = (int) (Math.log(max_num)/Math.log(2));
		System.out.println("start-cutting");
		for(int i=min_lv;i<=max_lv;i++){
			System.out.println(i);
			this.tmpBuffer = resize(sourceBuffer,(int) (Math.pow(2, i)*i_size));
			System.out.println(this.tmpBuffer.length);
			cutmatix(hdfsPath,imageName,width,height,i,i_size);
		}
	}
}