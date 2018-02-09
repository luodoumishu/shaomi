package com.xjj.cms.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.Date;

import com.xjj.jdk17.utils.DateUtil;

/**
 * 文件操作公共类
 * 
 * @author Administrator
 * 
 */
public class FileTools {

	public static String changeFileNameByTime(String fileName) {
		String subffix = fileName.substring(fileName.lastIndexOf("."));
		// 为附件重新命名
		String newFileName = DateTools.getCurTime() + Random.getStrRandom(5)
				+ subffix;
		return newFileName;
	}

	public static void uploadFile(InputStream in, String newFilePathName) {

		byte[] buffer = new byte[8192];
		OutputStream out = null;
		try {
			buffer = new byte[8192];
			out = new FileOutputStream(newFilePathName);

			int bytesRead = 0;
			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);// 将文件写入服务器
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				out.close();
				in.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	public static void saveTextToFile(String filePath, String text) {
		File file = createNewFile(filePath);

		try {
			FileOutputStream fw = new FileOutputStream(file);
			fw.write(text.getBytes());
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据文件名来创建文件对象
	 * 
	 * @param filePath
	 * @return
	 */
	private static File createNewFile(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
			if (!file.exists()) {
				int flag = filePath.lastIndexOf(File.separator);
				String dirPath = filePath.substring(0, flag);
				File dir = new File(dirPath);
				dir.mkdirs();
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static void createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);
		try {
			if (subDirectory == "" && fl.exists() != true)
				fl.mkdir();
			else if (subDirectory != "") {
				dir = subDirectory.replace('\\', '/').split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(directory + File.separator + dir[i]);
					if (subFile.exists() == false)
						subFile.mkdir();
					directory += File.separator + dir[i];
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void deleteFile(String fileName) {
		File afile = new File(fileName);
		afile.delete();
	}

	public static boolean deleteDir(File f) {
		boolean rtn = true;
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDir(files[i]);
				} else {
					rtn = files[i].delete();
					if (!rtn) {
						break;
					}
				}
			}
			if (rtn) {
				rtn = f.delete();
			}
		} else {
			rtn = f.delete();
		}
		return rtn;
	}

	/**
	 * 对比文件名的后缀是否与指定的后缀一致
	 * 
	 * @param arg
	 * @param suffix
	 * @return
	 */
	public static boolean checkSuffix(String arg, String suffix) {
		String[] str = arg.split("\\.");
		if (str == null || str.length <= 1) {
			return false;
		} else {
			String sf = str[str.length - 1];
			if (sf.toLowerCase().equals(suffix)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 对比文件名的后缀是否与指定的后缀一致(新增：从配置文件中拿出来)fengjunkong
	 * 
	 * @param arg
	 * @param suffix
	 *            这里是用逗号连接起来的全部格式
	 * @return boolean 2014.3.12
	 */
	public static boolean checkFormat(String arg, String suffix) {
		String[] allsuffix = suffix.split(",");
		String[] str = arg.split("\\.");
		if (str == null || str.length <= 1) {
			return false;
		} else {
			String sf = str[str.length - 1];
			for (int i = 0; i < allsuffix.length; i++) {
				if (sf.toLowerCase().equals(allsuffix[i].toLowerCase())) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * 剥离出文章名
	 * 
	 * @return
	 */
	public static String getFileNameOutOfSuffix(String fileName) {
		int length = fileName.lastIndexOf(".");
		return fileName.substring(0, length);
	}

	
	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件，返回字节
	 * @author yeyunfeng 2015年6月17日  上午11:41:03
	 * @param fileName
	 * @return
	 *
	 */
	public static byte[] getFileByBytes(String fileName) {
		byte[] content = null;
		InputStream in = null;
		BufferedInputStream bin = null;
		ByteArrayOutputStream out = null;
		try {
			in = new FileInputStream(fileName);
			bin = new BufferedInputStream(in);
			out = new ByteArrayOutputStream(1024);
			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = bin.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			content = out.toByteArray();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) {
				}
			}
			if (bin != null) {
				try {
					bin.close();
				} catch (IOException e1) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}
		return content;
	}

	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 * 
	 * @author yeyunfeng 2015年6月17日 上午10:53:27
	 * @param fileName
	 * 
	 */
	public static void readFileByBytes(String fileName) {
		File file = new File(fileName);
		InputStream in = null;
		try {
			// 一次读多个字节
			byte[] tempbytes = new byte[100];
			int byteread = 0;
			in = new FileInputStream(fileName);
			showAvailableBytes(in);
			// 读入多个字节到字节数组中，byteread为一次读入的字节数
			while ((byteread = in.read(tempbytes)) != -1) {
				System.out.write(tempbytes, 0, byteread);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
				}
			}
		}

	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 * 
	 * @author yeyunfeng 2015年6月17日 上午10:55:01
	 * @param fileName
	 * 
	 */
	public static void readFileByChars(String fileName) {
		File file = new File(fileName);
		Reader reader = null;
		try {
			// 一次读多个字符
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(fileName));
			// 读入多个字符到字符数组中，charread为一次读取字符数
			while ((charread = reader.read(tempchars)) != -1) {
				// 同样屏蔽掉\r不显示
				if ((charread == tempchars.length)
						&& (tempchars[tempchars.length - 1] != '\r')) {
					System.out.print(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
							System.out.print(tempchars[i]);
						}
					}
				}
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 * @author yeyunfeng 2015年6月17日 上午10:55:38
	 * @param fileName
	 * 
	 */
	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 随机读取文件内容
	 * 
	 * @author yeyunfeng 2015年6月17日 上午10:56:16
	 * @param fileName
	 * 
	 */
	public static void readFileByRandomAccess(String fileName) {
		RandomAccessFile randomFile = null;
		try {
			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(fileName, "r");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 读文件的起始位置
			int beginIndex = (fileLength > 4) ? 4 : 0;
			// 将读文件的开始位置移到beginIndex位置。
			randomFile.seek(beginIndex);
			byte[] bytes = new byte[10];
			int byteread = 0;
			// 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
			// 将一次读取的字节数赋给byteread
			while ((byteread = randomFile.read(bytes)) != -1) {
				System.out.write(bytes, 0, byteread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 显示输入流中还剩的字节数
	 * 
	 * @author yeyunfeng 2015年6月17日 上午11:03:01
	 * @param in
	 * 
	 */
	public static int showAvailableBytes(InputStream in) {
		int size = 0;
		try {
			size = in.available();
			System.out.println("当前字节输入流中的字节数为:" + in.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return size;
	}
	
	public static String createDirByTime(String prePath)throws  Exception{
		Date date = new Date();
		String year  = DateUtil.getYearByDate(date)+"";
		String month = DateUtil.getMonthByDate(date)+"";
		String path = prePath+File.separator+year+File.separator+month;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}
	
	public static void main(String[] args) {
		String fileName = "C:\\Users\\Administrator\\Pictures\\20141127212942366_3719475.jpg";
		getFileByBytes(fileName);
		// readFileByBytes(fileName);
		// readFileByChars(fileName);
		// readFileByLines(fileName);
		// readFileByRandomAccess(fileName);
	}

}
