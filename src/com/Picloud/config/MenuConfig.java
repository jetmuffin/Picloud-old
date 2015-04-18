package com.Picloud.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.Picloud.web.model.Action;
import com.Picloud.web.model.Module;


@WebServlet(name = "MenuConfig", urlPatterns = { "/MenuConfig" }, loadOnStartup = 1)
public class MenuConfig  extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public MenuConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		ServletContext application = this.getServletContext();
		List<Module> modules = new ArrayList<Module>();
		
		
		//首页
		Module module_index = new Module("Index", "首页", "home", "index");
		modules.add(module_index);
		List<Action> actions = new ArrayList<Action>();
		
		//图片空间
		actions.add(new Action("PicSpace", "图片空间","spaces"));
		actions.add(new Action("Upload", "快速上传","upload"));
		Module module_picserver = new Module("space", "图片服务器", "link", "#",actions);
		modules.add(module_picserver);
		
		//应用中心
		actions = new ArrayList<Action>();
		actions.add(new Action("AppList", "应用列表","tools"));
		actions.add(new Action("HighDefi", "高清图片","../hd/list"));		
		actions.add(new Action("PanoView", "全景展示","../pano/list"));		
		actions.add(new Action("3DView", "3D物品","../threeD/list"));		
		Module module_appcenter = new Module("process", "应用中心", "th-large", "#",actions);
		modules.add(module_appcenter);
		
		//个人中心
		 actions = new ArrayList<Action>();
		actions.add(new Action("Account", "帐号管理","update"));
		actions.add(new Action("Log", "操作日志","log/0"));
		Module module_usercenter = new Module("user", "个人中心", "user", "#",actions);
		modules.add(module_usercenter);
		
		application.setAttribute("MODULE", modules);
		application.setAttribute("MODULE_INDEX", module_index);
		application.setAttribute("MODULE_APPCENTER", module_appcenter);
		application.setAttribute("MODULE_PICSERVER", module_picserver);
		application.setAttribute("MODULE_USERCENTER", module_usercenter);
		
	}
}
