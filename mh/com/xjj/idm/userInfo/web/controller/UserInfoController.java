package com.xjj.idm.userInfo.web.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.xjj.idm.userInfo.model.UserHead;
import com.xjj.idm.userInfo.service.UserHeadService;
import com.xjj.idm.userInfo.util.ImageUtils;

@Controller
@RequestMapping(value = "/userInfo")
public class UserInfoController {

	@Autowired
	@Qualifier("userHeadService")
	private UserHeadService userHeadService;

	/**
	 * 读取用户头像
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getUserHead", method = { RequestMethod.GET, RequestMethod.POST })
	public String getUserHead(HttpServletRequest request, HttpServletResponse response) {
		try {
			String account = (String) request.getSession().getAttribute("account");
			String imageFile = "tu1.jpg";
			if (account != null) {
				UserHead head = userHeadService.findByUserAccount(account);
				if (head != null) {
					imageFile = head.getPicturePath();
				} 
				if (request.getParameter("todo") != null) {
					response.getWriter().write(imageFile);
					return null;
				}
				return "userinfo/index";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "loadError";
		}
		return "loadError";
	}
	
	@RequestMapping(value = "/getUserHead2", method = { RequestMethod.GET, RequestMethod.POST })
	public String getUserHead2(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		try {
			String account = (String) request.getSession().getAttribute("account");
			String imageFile = "tu1.jpg";
			if (account != null) {
				UserHead head = userHeadService.findByUserAccount(account);
				if (head != null) {
					imageFile = head.getPicturePath();
				}
				model.addAttribute("imageFile", imageFile);
				return "userinfo/index2";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "userinfo/error";
		}
		return "userinfo/error";
	}
	
	
	@RequestMapping(value = "/upload", method = {RequestMethod.POST })
	public String upload(@RequestParam(value ="file", required=false) MultipartFile file, 
			HttpServletRequest request, ModelMap model) {
		String account = (String) request.getSession().getAttribute("account");
		
		File image;
		int imageHeigth;
		int imageWidth;
		String imageFileName = file.getOriginalFilename();
		try {
			imageFileName = account + imageFileName.substring(imageFileName.lastIndexOf("."));
			String url = request.getServletContext().getRealPath("/heads");
			File imgdir = new File(url);
			if (!imgdir.exists()) {
				imgdir.mkdirs();
			}
			File imageFile = new File(imgdir + "/" + imageFileName);
			if (!imageFile.exists()) {
				imageFile.createNewFile();
			}
			file.transferTo(imageFile);
			image = new File(imgdir + "/" + imageFileName);
			
			Image src = javax.imageio.ImageIO.read(image);
			int w0 = src.getWidth(null); 	// 得到源图宽
			int h0 = src.getHeight(null); 	// 得到源图长
			// 处理尺寸大于300的图片
			if (w0 > h0 && w0 > 200) {// 宽图片
				double times = w0 / (h0 * 1.0);// 倍数
				w0 = 200;
				h0 = (int) (w0 / times);
			} else if (w0 < h0 && h0 > 200) {
				double times = h0 / (w0 * 1.0);// 倍数
				h0 = 200;
				w0 = (int) (h0 / times);
			}
			BufferedImage tag = new BufferedImage(w0, h0, BufferedImage.TYPE_INT_RGB);
			// 保存文件
			// 绘制缩小后的图
			tag.getGraphics().drawImage(src.getScaledInstance(w0, h0, Image.SCALE_SMOOTH), 0, 0, null);
			// 输出到文件流
			FileOutputStream out = new FileOutputStream(imageFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			// 近JPEG编码
			encoder.encode(tag);
			out.close();
			/* image=imageFile; */
			imageHeigth = h0;
			imageWidth = w0;
			
			model.addAttribute("imageHeigth", imageHeigth);
			model.addAttribute("imageWidth", imageWidth);
			model.addAttribute("imageFileName", imageFileName);
			return "userinfo/index";
			
		} catch (IOException e) {
			e.printStackTrace();
			return "userinfo/error";
		}
	}
	
	/**
	 * 修剪保存头像
	 * 
	 * @return
	 */
	@RequestMapping(value = "/crop", method = {RequestMethod.POST })
	public String crop(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String path = request.getServletContext().getRealPath("/heads");
		String oldpath = path + "/" + request.getParameter("imageFile");
		String newPath = path + "/_" + request.getParameter("imageFile");
		int x1 = Integer.parseInt(request.getParameter("x1"));
		int y1 = Integer.parseInt(request.getParameter("y1"));
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		int headWidth = 60;
		int headHeight = 70;
		
		String account = (String) request.getSession().getAttribute("account");
		String imageFile = request.getParameter("imageFile");
		String msg = "";
		try {
			// 裁剪图片
			ImageUtils.cut(oldpath, oldpath, x1, y1, width, height);
			// 缩放到固定大小
			ImageUtils.scale2(oldpath, newPath, headHeight, headWidth, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (account != null) {
			// 保存到数据库
			imageFile = "_" + imageFile;
			userHeadService.saveOrUpdateUserHead(account,imageFile);
			new File(oldpath).delete();
			msg = "headSaveSuccess";
			model.addAttribute("msg", msg);
			return "userinfo/index";
		}
		return "userinfo/error";
	}
}
