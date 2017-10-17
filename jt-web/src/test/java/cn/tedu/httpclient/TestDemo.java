package cn.tedu.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestDemo {
	/**
	 * 模拟Get()请求
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Test
	public void doGet() throws ClientProtocolException, IOException{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet("https://item.jd.com/1226645.html");
		CloseableHttpResponse response = client.execute(get);
		//判断请求是否成功
		if(response.getStatusLine().getStatusCode()==200){
			//获取响应数据
			String content = EntityUtils.toString(response.getEntity());
			System.out.println(content);
		}
		
	}
	/*
	 * 模拟Post请求
	 */
	@Test
	public void doPost() throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost("https://passport.csdn.net/account/login?from=http://my.csdn.net/my/mycsdn");
		List<BasicNameValuePair> formList = new ArrayList<>();
		formList.add(new BasicNameValuePair("username", "tom"));
		formList.add(new BasicNameValuePair("password", "123456"));
		post.setEntity(new UrlEncodedFormEntity(formList,"utf-8"));
		CloseableHttpResponse response=client.execute(post);
		if(response.getStatusLine().getStatusCode()==200){
			String content=EntityUtils.toString(post.getEntity());
			System.out.println(content);
		}
		
	}
	
	@Test
	public void test01(){
		List<String> list = new ArrayList<String>();
		List<String> list2 = list;
		list.add("131");
		System.out.println(list2);
		

		
	}
	
}
