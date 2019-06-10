package com.mytest.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.AsyncHttpClientConfig.Builder;
import com.ning.http.client.ByteArrayPart;
import com.ning.http.client.Cookie;
import com.ning.http.client.Response;
import com.ning.http.client.providers.netty.NettyAsyncHttpProvider;
import com.ning.http.client.providers.netty.NettyResponse;
import com.ning.http.multipart.FilePart;
import com.ning.http.multipart.Part;
import com.ning.http.multipart.StringPart;



/**
 * 基于NettyAsyncHttpProvider的AsyncHttpClient
 * @author dbb
 *
 */
public class AHttpClient {
	
	// realm = new Realm.RealmBuilder().setPrincipal(login).setPassword(passwd).setScheme(Realm.AuthScheme.BASIC).build();
	// ahc = asyncHttpClient.preparePost( url)
	// ahc.setRealm(realm)

	private static Builder config = new AsyncHttpClientConfig.Builder();
	private static NettyAsyncHttpProvider nettyAsyncHttpProvider;
	
	static {
		config.setRequestTimeoutInMs(30000);
		// config.setMaximumConnectionsPerHost(10);
		config.setMaximumConnectionsTotal(20000);
		config.setAllowSslConnectionPool(true);
//		config.setUserAgent(MpHttpUtils.UserAgent);
		nettyAsyncHttpProvider = new NettyAsyncHttpProvider(config.build());
	}

	// 表单参数对对像
	public static class ParamPair {
		private String key;
		private String value;
		
		public ParamPair(String key, String value){
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}
		
		@Override
		public String toString(){
			return key + "=" + value;
		}
	}
	/**
	 * 如果改成3将会重试一次  如果改成2将会不会重试 
	 */
	public int reTry = 3; 
	// Retry Controller
	private  Response ExecuteAndGet(BoundRequestBuilder request) throws Exception{
		Exception ex = null;
		int retrycount = 0;
		while(++retrycount < reTry){
			try{
				Future<Response> future = request.execute();
				return (NettyResponse) future.get(80,TimeUnit.SECONDS);
			} catch (Exception e) {
				ex = e;
				e.printStackTrace();
			}
		}
		throw ex;
	}
	
	private AsyncHttpClient asyncHttpClient;
	private LinkedHashMap<String, Cookie> cm;
	private String referer;
	
	public AHttpClient(){
		this.cm = new LinkedHashMap<String, Cookie>();
		asyncHttpClient = new AsyncHttpClient(nettyAsyncHttpProvider);
	}
	
	public void setReferer(String referer) {
		this.referer = referer;
	}

	public AsyncHttpClient getAsyncHttpClient() {
		return asyncHttpClient;
	}
	
	public static String getHttp(String url) {
		AHttpClient aHttpClient = new AHttpClient();
		return aHttpClient.doHttpGetRequest(url);
	}
	
	// 绑定当前实例下的Cookie对像
	private BoundRequestBuilder bindCookie(BoundRequestBuilder request){
		for(Cookie c : cm.values()){
			System.out.println("addCookie, name=" + c.getName() + " value=" + c.getValue());
			request.addOrReplaceCookie(c);
		}
		return request;
	}
	
	// 保存Cookie
	public void saveCookie(NettyResponse nettyResponse){
		for (Cookie c : nettyResponse.getCookies()) {
			cm.put(c.getName(), c);
			System.out.println("saveCookie, name=" + c.getName() + " value=" + c.getValue());
		}
	}

	public ByteArrayPart getMediaURLBody(String URL, String partname, String minetype, String filename){
		try {
			BoundRequestBuilder request = asyncHttpClient.prepareGet(URL);
			NettyResponse nettyResponse = (NettyResponse)ExecuteAndGet(bindCookie(request));
			
			BufferedInputStream in = new BufferedInputStream(nettyResponse.getResponseBodyAsStream());
			byte[] all = new byte[0];
			byte[] read = new byte[1024];
            int num;
            while ((num = in.read(read)) > 0) {  
                byte[] temp = new byte[all.length + num];
                System.arraycopy(all, 0, temp, 0, all.length);  
                System.arraycopy(read, 0, temp, all.length, num);  
                all = temp;  
            }
			return new ByteArrayPart(partname, filename, all, minetype, "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            
        }
		return null;
	}

	// AsyncHttp请求
	public NettyResponse doHttpRequest(BoundRequestBuilder request) throws Exception {
		// Future<Response> future = bindCookie(request).execute();
		// NettyResponse nettyResponse = (NettyResponse) future.get();
		// return nettyResponse;
		return (NettyResponse)ExecuteAndGet(bindCookie(request));
	}

	// AsyncHttp请求
	public String doHttpGetRequest(String URL){
		return doHttpGetRequest(URL, "UTF-8");
	}
	
	// AsyncHttp请求
	public String doHttpGetRequest(String URL, String charset){
		try {
			BoundRequestBuilder request = asyncHttpClient.prepareGet(URL);
			if(referer!=null && referer.length()>0)
				request.addHeader("Referer", referer);
			
			NettyResponse nettyResponse = (NettyResponse)ExecuteAndGet(bindCookie(request));
			return nettyResponse.getResponseBody(charset);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            
        }
		return null;
	}
	
	// AsyncHttp请求（表单）
	public String doHttpPostRequest(String URL, List<ParamPair> stringPartList){
		try {
			BoundRequestBuilder request = asyncHttpClient.preparePost(URL);
			if(referer!=null && referer.length()>0)
				request.addHeader("Referer", referer);
			
			/*
			if(stringPartList!=null && stringPartList.size()>0)
				for(StringPart stringPart : stringPartList){
					request.addBodyPart(stringPart);
				}*/
			if(stringPartList!=null && stringPartList.size()>0){
				for(ParamPair paramPair : stringPartList){
					request.addParameter(paramPair.getKey(), paramPair.getValue());
				}
			}
			// Future<Response> future = bindCookie(request).execute();
			// NettyResponse nettyResponse = (NettyResponse) future.get();
			NettyResponse nettyResponse = (NettyResponse)ExecuteAndGet(bindCookie(request));
			return nettyResponse.getResponseBody("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            
        }
		return null;
	}
	
	
	// AsyncHttp请求（表单）
    public String doHttpPostRequest(String URL, List<ParamPair> stringPartList,Map<String, String> headers){
        try {
            BoundRequestBuilder request = asyncHttpClient.preparePost(URL);
            if (headers != null) {
                for (String header : headers.keySet()) {
                    request.setHeader(header, (String) headers.get(header));
                }
            }
            if(stringPartList!=null && stringPartList.size()>0){
                for(ParamPair paramPair : stringPartList){
                    request.addParameter(paramPair.getKey(), paramPair.getValue());
                }
            }
            NettyResponse nettyResponse = (NettyResponse)ExecuteAndGet(bindCookie(request));
            return nettyResponse.getResponseBody("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
        }
        return null;
    }
    
	// AsyncHttp请求（表单）
		public String doHttpPostRequest(String URL, Map<String, String> stringPartList){
			try {
				BoundRequestBuilder request = asyncHttpClient.preparePost(URL);
				if(referer!=null && referer.length()>0)
					request.addHeader("Referer", referer);
				
				if(stringPartList!=null && stringPartList.size()>0){
					for(Map.Entry<String, String> param : stringPartList.entrySet()){
						request.addParameter(param.getKey(), param.getValue());
					}
				}
				NettyResponse nettyResponse = (NettyResponse)ExecuteAndGet(bindCookie(request));
				return nettyResponse.getResponseBody("UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	            
	        }
			return null;
		}
	
	// AsyncHttp请求（表单）
	public String doHttpPostRequest(String URL, String postbody){
		try {
			BoundRequestBuilder request = asyncHttpClient.preparePost(URL);
			if(referer!=null && referer.length()>0)
				request.addHeader("Referer", referer);
			request = bindCookie(request).setBody(postbody.getBytes("UTF-8"));
			NettyResponse nettyResponse = (NettyResponse)ExecuteAndGet(request);
			return nettyResponse.getResponseBody("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            
        }
		return null;
	}
	
	// AsyncHttp请求（表单）公钥
    public String doHttpPostRequest(String URL, String postJsonBody,String AccessToken){
        try {
            BoundRequestBuilder request = asyncHttpClient.preparePost(URL);
            if(referer!=null && referer.length()>0)
                request.addHeader("Referer", referer);
            request = bindCookie(request)
                    .setHeader("Content-Type", "application/json");
            request.setHeader("Access-Token", AccessToken);
            // Future<Response> future = bindCookie(request).setBody(postbody.getBytes("UTF-8")).execute();
            // NettyResponse nettyResponse = (NettyResponse) future.get();
            request = bindCookie(request).setBody(postJsonBody.getBytes("UTF-8"));
            NettyResponse nettyResponse = (NettyResponse)ExecuteAndGet(request);
            return nettyResponse.getResponseBody("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
        }
        return null;
    }
    
    public String doHttpPostRequest(String URL, String postJsonBody,Map<String, String> headers){
        try {
            BoundRequestBuilder request = asyncHttpClient.preparePost(URL);
            if (headers != null) {
                for (String header : headers.keySet()) {
                    request.setHeader(header, (String)headers.get(header));
                }
              }
            request = bindCookie(request)
                    .setHeader("Content-Type", "application/json");
            request = bindCookie(request).setBody(postJsonBody.getBytes("UTF-8"));
            NettyResponse nettyResponse = (NettyResponse)ExecuteAndGet(request);
            return nettyResponse.getResponseBody("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
        }
        return null;
    }
	
	// AsyncHttp请求（文件）
	public String doHttpPostFile(String URL, Part part){
		try {
			BoundRequestBuilder request = asyncHttpClient.preparePost(URL);
			if(referer!=null && referer.length()>0)
				request.addHeader("Referer", referer);
			
			/*
			Future<Response> future = bindCookie(request)
					.setHeader("Content-Type", "multipart/form-data")
					.addBodyPart(part)
			        // .setBody(new ByteArrayBodyGenerator(YOUR_BYTE_ARRAY))
					// .addBodyPart( new com.ning.http.client.bFilePart(new File("a_file").name, new File("a_file"), "text/plain", "UTF-8"))
			        .execute();
			NettyResponse nettyResponse = (NettyResponse)future.get();*/
			
			request = bindCookie(request)
					.setHeader("Content-Type", "multipart/form-data")
					.addBodyPart(part);
			NettyResponse nettyResponse = (NettyResponse)ExecuteAndGet(request);
			return nettyResponse.getResponseBody();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            
        }
		return null;
	}
	
	public static String doGet(String url) {
		AHttpClient aHttpClient = new AHttpClient();
		return aHttpClient.doHttpGetRequest(url);
	}
	
	public static String doGet(String url, String params) {
		AHttpClient aHttpClient = new AHttpClient();
		int pos = url.indexOf("?"); 
		if(pos > 0){
			return aHttpClient.doHttpGetRequest(url + "&" + params);
		} else
			return aHttpClient.doHttpGetRequest(url + "?" + params);
	}
	
	/*
	public static String doPost(String url, Map<String, String> params){
		AHttpClient aHttpClient = new AHttpClient();
		Iterator<String> it = params.keySet().iterator();
		List <StringPart> stringPartList = new ArrayList <StringPart>();
		while(it.hasNext()){
			String key = it.next();
			stringPartList.add(new StringPart(key, params.get(key)));
		}
		return aHttpClient.doHttpPostRequest(url, stringPartList);
	}*/

    // AsyncHttp请求（文件）
    public String doHttpPostFile(String URL, Part part,List<StringPart> stringPartList){
        try {
            BoundRequestBuilder request = asyncHttpClient.preparePost(URL);
            if(referer!=null && referer.length()>0)
                request.addHeader("Referer", referer);
            
            request = bindCookie(request)
                    .setHeader("Content-Type", "multipart/form-data")
                    .addBodyPart(part);
            for(StringPart paramPair : stringPartList){
                request.addBodyPart(paramPair);
            }
            NettyResponse nettyResponse = (NettyResponse)ExecuteAndGet(request);
            return nettyResponse.getResponseBody();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
        }
        return null;
    }
    
	public static String doPost(String url, String postbody){
		AHttpClient aHttpClient = new AHttpClient();
		return aHttpClient.doHttpPostRequest(url, postbody);
	}
public static void main(String[] args) throws Exception {
        
        AHttpClient httpClient = new AHttpClient();
        
        
        List<StringPart> stringPartList = new ArrayList<StringPart>();
        stringPartList.add(new StringPart("type","2"));
        stringPartList.add(new StringPart("isqianbao","1"));
        String path ="E://es.png";

        Part part =   new FilePart("qqfile", new File(path));

        stringPartList.add(new StringPart("token","ktrBu1gafo4oOfSqQt4W+EoX29ag7IHyfRO0yM/Y2oFW0MFL3hSbjHvBKB+bBGjb5mKsP2vN9bAji5i1n0f9ysm73hFNy5x17UkY/ywL5b5lsxmJgC6HmkTmxQUOsZDs"));
        
        //String httpresult = httpClient.doHttpPostRequest("http://12.0.0.200:8091/caif-web/debitcard/updateCurrentCard", stringPartList);
        //String httpresult = httpClient.doHttpPostRequest("http://api3.51kahui.com/kahui3-api-web/baseData/advertisement", stringPartList);
       String httpresult = httpClient.doHttpPostFile("http://up3.51kahui.com/kahui3-upload-web/service/upload",part, stringPartList);
     System.out.println(httpresult);
    }
}
