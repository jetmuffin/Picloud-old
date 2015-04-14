package com.Picloud.hdfs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Picloud.config.HdfsConfig;
import com.Picloud.utils.JspUtil;
import com.Picloud.utils.EncryptUtil;
import com.Picloud.utils.PropertiesUtil;
import com.Picloud.web.dao.impl.ImageDaoImpl;
import com.Picloud.web.dao.impl.MapfileDaoImpl;
import com.Picloud.web.model.Image;
import com.Picloud.web.model.Mapfile;

@Service
public class MapfileHandler {
	@Autowired
	private HdfsConfig mHdfsConfig;
	@Autowired
	private MapfileDaoImpl mMapfileDaoImpl;
	@Autowired
	private ImageDaoImpl mImageDaoImpl;

	/**
	 * 将小图片打包成mapfile进行存储
	 * 
	 * @author Jet-Muffin
	 * @param items
	 *            文件数组
	 * @param filePath
	 *            map文件地址
	 * @throws IOException
	 */
	public void packageToHdfs(File[] items, String hdfsDir, String uid,
			String space) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(
				URI.create(mHdfsConfig.getFileSystemPath()), conf);
		Path path = new Path(fs.getHomeDirectory(), hdfsDir);

		BytesWritable value = new BytesWritable();
		Text key = new Text();

		MapFile.Writer writer = new MapFile.Writer(conf, fs, path.toString(),
				key.getClass(), value.getClass());
		// 通过writer向文档中写入记录
		for (File item : items) {
			try {
				String filename = item.getName();
				byte buffer[] = getBytes(item);
				writer.append(new Text(filename), new BytesWritable(buffer));
				// 更新数据库
				Image image = mImageDaoImpl.find(EncryptUtil.imageEncryptKey(item.getName(), uid));
				image.setStatus("HdfsSmallFile");
				image.setPath(path.toString());
				image.setUid(uid);
				image.setSpace(space);
				mImageDaoImpl.add(image);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Mapfile mapfile = new Mapfile();
			mapfile.setUid(uid);
			mapfile.setPicNum(Integer.toString(items.length));
			mapfile.setFlagNum("0");
			String name = hdfsDir.substring(hdfsDir.length() - 14,
					hdfsDir.length());
			mapfile.setKey(name + uid);
			mapfile.setName(name);
			mMapfileDaoImpl.add(mapfile);
			item.delete();
		}
		IOUtils.closeStream(writer);// 关闭write流
	}

	/**
	 * 从Mapfile中读取出图片
	 * 
	 * @param filePath
	 *            Mapfile文件路径
	 * @param fileName
	 *            图片名
	 * @return bytep[] 图片byte数组
	 * @throws IOException
	 */
	public byte[] readFromHdfs(String hdfsDir, String image) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.newInstance(URI.create(PropertiesUtil.getValue("FileSystemPath")),conf);
		Path path = new Path(fs.getHomeDirectory(), hdfsDir);
		Text key = new Text(image);
		BytesWritable value = new BytesWritable();
		byte[] data = null;
		try {
			MapFile.Reader reader = new MapFile.Reader(fs, path.toString(),
					conf);
			reader.seek(key);
			if (reader.seek(key)) {
				reader.get(key, value);
				data = value.get();
				return data;
			} else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将file对象转化为byte数组
	 * 
	 * @param file
	 * @return
	 */
	private byte[] getBytes(File file) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[fis.available()];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 删除文件，代码未测试
	 * 
	 * @param hdfsDir
	 *            map 文件地址
	 * @param images
	 *            图片文件
	 * @throws IOException
	 */
	public void deleteImage(String hdfsDir, List<Image> images)
			throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(
				URI.create(mHdfsConfig.getFileSystemPath()), conf);
		// 重新生成mapfile的路径
		String date = JspUtil.getCurrentDateStr();
		String filePath = hdfsDir.substring(0, hdfsDir.length() - 14) + date;
		Path path = new Path(fs.getHomeDirectory(), filePath);
		BytesWritable value = new BytesWritable();
		Text key = new Text();
		MapFile.Writer writer = new MapFile.Writer(conf, fs, path.toString(),
				key.getClass(), value.getClass());

		// 对文件进行写入PictureBean
		for (Image image : images) {
			byte[] data = readFromHdfs(hdfsDir, image.getName());
			if (data == null)
				System.out.println("null");
			writer.append(new Text(image.getName()), new BytesWritable(data));
			System.out.println("重写mapfile成功！");

			// 更新图片的地址
			image.setPath(filePath);
			mImageDaoImpl.update(image);
			System.out.println("更新图片的地址成功");
		}
		IOUtils.closeStream(writer);// 关闭write流
		// 更新mapfile数据库信息
		Mapfile mapfile = new Mapfile();
		mapfile.setKey(date + images.get(0).getUid());
		mapfile.setFlagNum("0");
		mapfile.setName(date);
		mapfile.setPicNum(Integer.toString(images.size()));
		mapfile.setUid(images.get(0).getUid());
		mMapfileDaoImpl.update(mapfile);
		System.out.println("更新mapfile成功！");
	}
	
	
	/**
	 *  打包测试方法
	 * @param items
	 * @param hdfsDir
	 */
	public void packageToHdfs(File[] items, String hdfsDir) throws IOException {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(
				URI.create(mHdfsConfig.getFileSystemPath()), conf);
		Path path = new Path(fs.getHomeDirectory(), hdfsDir);

		BytesWritable value = new BytesWritable();
		Text key = new Text();

		MapFile.Writer writer = new MapFile.Writer(conf, fs, path.toString(),
				key.getClass(), value.getClass());
		// 通过writer向文档中写入记录
		for (File item : items) {
			try {
				String filename = item.getName();
				byte buffer[] = getBytes(item);
				writer.append(new Text(filename), new BytesWritable(buffer));
				// 更新数据库
			} catch (Exception e) {
				e.printStackTrace();
			}
			item.delete();
		}
		IOUtils.closeStream(writer);// 关闭write流
	}
}
