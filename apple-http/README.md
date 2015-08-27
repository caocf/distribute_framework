#requestProxy
    requestProxy 是为了把 http,以及webserver 的简单请求进行封装,做到接口化操作http接口
    快速开发,高效完成
    

###作用:

 1. 可以使http请求进行接口化,使得http接口更好的和java做融合
 2. 融合json可以自动转为bean
 3. 也可以解析符合要求的xml
 4. 负责的返回string,大家可以自己解析


    
###使用方法:

	1.配置httpclient和spring 融合,这个网上比较多
	2.写接口与参数
	3.注入调用接口

###适用项目:

	与其他项目进行交互,调用地方接口较多的项目
    使用连接池,对象返回复杂
	
###支持返回类型:

	String,int,long,double
	bean
	list<bean>

###版本修改
 
 - 2.0.0
	 - 换了包名  cn.afterturn
 - 1.6.1
	 - 新增本多种签名计算方法,可以有效减少签名计算类 
 - 1.6.2
	 - 使用IRequestParam注解替换了RequestParams 统一命名方式
	 - IRequest 增加了 @Component 注解(可以被spring 识别名称)
	 - 扫描类的工具改成了spring 的scan

	----------------------------------------------
	    <!-- 注册 HTTP请求动态代理接口 -->
		<bean class="com.onepiece.requestproxy.factory.RequestBeanScannerConfigurer">
			<property name="basePackage" value="com.jueyue.onepiece.test.request">
			</property><!--符合了spring的写法-->
		</bean>
	 ------------------------------------------------
	 
 ###demo
 	可以轻松设置一写基础参数,调用httpclient属性,接口化调用网络接口
 ```Java
 	@IRequest("testRequest")
	public interface ITestRequest {
	
		@IRequestMethod(type = RequestTypeEnum.GET, url = "http://api.map.baidu.com/telematics/v3/weather")
		String testGet(@IRequestParam("location") String location,
				@IRequestParam("output") String output,
				@IRequestParam("ak") String ak);
	
		@IRequestMethod(type = RequestTypeEnum.GET, url = "http://api.map.baidu.com/telematics/v3/weather")
		BaiduWeatherEntity testGetEntity(
				@IRequestParam("location") String location,
				@IRequestParam("output") String output,
				@IRequestParam("ak") String ak);
	
		@IRequestMethod(connectTimeout = 120, url = "http://open.ikamobile.cn:8391/pur/train/number.json", type = RequestTypeEnum.GET)
		public String queryTrainSchedule(
				@IRequestParam("from_station_name") String fromStationName,
				@IRequestParam("to_station_name") String toStationName,
				@IRequestParam("date") String date,
				@IRequestParam("train_type") String trainType);
	
	}
```
 		