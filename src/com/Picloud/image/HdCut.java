package com.Picloud.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;

import com.Picloud.hdfs.HdfsHandler;
import com.Picloud.web.model.HdImage;


public class HdCut {
	//static byte[] tmpByte;
		//压缩函数临时存储路径
		static String tmpByte = "/home/inenrac/Pictures/imgs/tmp";
		//最大层级，可修改
		static int MAX_LV = 20;
		private byte[] tmpBuffer;
		
		/**
		 * 分级切割函数
		 * @param srcPath	源图路径
		 * @param distPath	目标路径
		 * @param i_size	元图尺寸
		 * @param mas_width	源图宽
		 * @param mas_height	源图高
		 * @throws Exception 
		 */
		public  void HdCutx(byte[] sourceBuffer,String hdfsPath,String imageName,int i_size,int mas_width,int mas_height) throws Exception{
			//计算最高层级
			int max_lv = (int) (Math.log(mas_width)/Math.log(2));
			if(max_lv > MAX_LV)
				max_lv = MAX_LV;
			int i_lv = max_lv + 1;
			int now_height = mas_height;
			int now_width = mas_width;
			double tmp_m,tmp_n;
			int m,n;
			
			GraphicMagick  gm = null;
			IMOperation op = null;
			HdfsHandler hdfsHandler = null;
			while(--i_lv >= 0){
				gm = new GraphicMagick(sourceBuffer, "jpg");
				//图片宽高修正
				now_width = now_width < 1?1:now_width;
				now_height = now_height<1?1:now_height;
				tmp_n = (double)now_width/i_size;
				tmp_m = (double)now_height/i_size;
				
				//尺寸修正
				n = (int) tmp_n;
				m = (int) tmp_m;
				
				n = n<tmp_n?n+1:n;
				m = m<tmp_m?m+1:m;
				 
				n = n<1?1:n;
				m = m<1?1:m;
				
				//压缩开始
				op = new IMOperation();
				op.addImage("-");
				op.resize(now_width,now_height);
				op.addImage("jpg:-");
				
				//管道输入输出 byte[]
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				Pipe pipeOut = new Pipe(null, out);
				ConvertCmd convert = new ConvertCmd(true);
				convert.setInputProvider(gm.getPipeIn());
				convert.setOutputConsumer(pipeOut);
				convert.run(op);
				this.tmpBuffer = out.toByteArray();

				//裁剪开始
				for(int i=0;i<m;i++){
					for(int j=0;j<n;j++){
						op = new IMOperation();
						op.addImage("-");
						op.crop(i_size,i_size,j*i_size,i*i_size);
						op.addImage("jpg:-");
						convert = new ConvertCmd(true);
						
						gm = new GraphicMagick(this.tmpBuffer, "jpg");
						out = new ByteArrayOutputStream();
						pipeOut = new Pipe(null, out);
						convert = new ConvertCmd(true);
						convert.setInputProvider(gm.getPipeIn());
						convert.setOutputConsumer(pipeOut);
						convert.run(op);
						byte[] imageOutData = out.toByteArray();
						InputStream in = new ByteArrayInputStream(imageOutData);
						
						//上传到HDFS
						hdfsHandler = new HdfsHandler();
						String filePath = hdfsPath + '/'  + imageName + "_files" +  "/"+(i_lv+1)+"/"+j+"_"+i+".jpg";
						hdfsHandler.upLoad(in, filePath);
					}
				}
				now_height /= 2;
				now_width /= 2;
			}
			
			String RealPath = hdfsPath  + '/' + imageName + ".dzi";
			String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Image TileSize=\""+i_size+"\" Overlap=\"1\" Format=\"jpg\" xmlns=\"http://schemas.microsoft.com/deepzoom/2008\"><Size Width=\""+mas_width+"\" Height=\""+mas_height+"\"/></Image>";
			hdfsHandler.createFile(RealPath, str);
		}
		
}
