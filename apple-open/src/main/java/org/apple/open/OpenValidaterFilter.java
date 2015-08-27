package org.durcframework.open;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenValidaterFilter extends OpenValidater implements Filter {
	
	private static final String UTF8 = "UTF-8";
	private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
	private String appSecretFile = "appSecret.properties";
	
	private final Properties props = new Properties();

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		response.setCharacterEncoding(UTF8);
		response.setContentType(CONTENT_TYPE);
		
		boolean success = this.validate(request, response, null);
		
		if(success) {
			arg2.doFilter(arg0, arg1);
		}
	}
	
	@Override
	public void fireException(HttpServletRequest request,
			HttpServletResponse response, OpenException e) {
		try {
			response.getWriter().write("{\"success\":false,\"message\":"+e.getMessage()+"}");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		loadAppSecretFile(filterConfig);
		
		this.setAppSecretManager(new AppSecretManager() {
			@Override
			public boolean isValidAppId(String appId) {
				if(appId == null){
		    		return false;
		    	}
		        return getSecret(appId) != null;
			}
			
			@Override
			public String getSecret(String appId) {
				return props.getProperty(appId);
			}
		});
	}
	
	/**
	 * 加载密钥文件
	 * @param filterConfig
	 */
	protected void loadAppSecretFile(FilterConfig filterConfig) {
		String configHome = System.getProperty("OPEN_CONFIG_HOME");
		InputStream in = null;

		File file = new File(configHome + File.separatorChar + appSecretFile);
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			in = this.getClass().getClassLoader().getResourceAsStream(appSecretFile);
			if(in == null) {
				in = filterConfig.getServletContext().getResourceAsStream("/WEB-INF/" + appSecretFile);
			}
		}

		try {
			if(in == null) {
				throw new FileNotFoundException("无法读取"+appSecretFile+"文件");
			}
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void destroy() {

	}

}
