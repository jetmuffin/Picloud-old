package com.Picloud.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.Info;
import org.im4java.core.InfoException;
import org.im4java.process.Pipe;

import com.Picloud.config.SystemConfig;

public class GraphicMagick {
	Pipe pipeIn = null;
	InputStream in = null;
	InputStream infoIn = null;
	Info info = null;
	String format = null;

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
		GMOperation op = new GMOperation();
		byte[] imageOutData = null;
		String formatOp = format + ":-";

		try {
			op.addImage("-");
			op.size(width, height);
			op.scale(width, height);
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
	public byte[] textWaterMask(String text, int fontsize, String color,
			int offsetX, int offsetY) throws IOException, InterruptedException,
			IM4JavaException {
		GMOperation op = new GMOperation();
		String option = " text " + offsetX + "," + offsetY + " \'" + text
				+ "\'";
		String fontpath = SystemConfig.getSystemPath() + "/font/msyh.ttf";
		color = "#" + color;
		String formatOp = format + ":-";
		byte[] imageOutData = null;

		try {
			op.font(fontpath).gravity("northwest").pointsize(fontsize)
					.fill(color).draw(option);
			op.addImage("-");
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
			imageOutData = out.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageOutData;
	}
}
