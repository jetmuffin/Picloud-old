package com.Picloud.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name="SystemVariable" , urlPatterns={"/SystemVariable"} , loadOnStartup = 1)
public class SystemVariable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static String ROOT;
    public static String RESOURCES;
    public static String PLUGIN;
    public static String UPLOAD;
    public static String TITLE;
    public static String VIEWS;
    public static String IP;
    
    public SystemVariable() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {  
		ServletContext application = this.getServletContext();
		
		ROOT = "/Picloud";
		RESOURCES =ROOT + "/resources";
		PLUGIN	= RESOURCES + "/plugins";
		UPLOAD = ROOT + "/resources/upload";
		VIEWS = ROOT + "/views";
		TITLE = "Picloud图片存储云";
		IP = "http://localhost:8080";
		
		application.setAttribute("ROOT",ROOT);
		application.setAttribute("RESOURCES", RESOURCES);
		application.setAttribute("PLUGIN", PLUGIN);
		application.setAttribute("UPLOAD",UPLOAD);
		application.setAttribute("VIEWS", VIEWS);
		application.setAttribute("TITLE", TITLE);
		application.setAttribute("IP",IP);
		
    }  
}