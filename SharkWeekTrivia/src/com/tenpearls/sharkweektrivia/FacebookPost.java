package com.tenpearls.sharkweektrivia;

public class FacebookPost {

		private String key;
		private String secret;
		private String url;
		private String udid;
		private String platform;
		private String token;
	
		public FacebookPost() {
			super();
		}

		public FacebookPost(String key, String sec, String url, String id, String plat, String token) {
			super();
			this.key = key;
			this.secret = sec;
			this.url = url;
			this.udid = id;
			this.platform = plat;
			this.token = token;
		}
		
		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
		
		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
		public String getUdid() {
			return udid;
		}

		public void setUdid(String id) {
			this.udid = id;
		}
		
		public String getPlatform() {
			return platform;
		}

		public void setPlatform(String plat) {
			this.platform = plat;
		}
		
		public String getAccToken() {
			return token;
		}

		public void setAccToken(String tkn) {
			this.token = tkn;
		}
}
