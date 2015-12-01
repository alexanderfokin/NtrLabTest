package ru.symdeveloper.ntrlabtest.network;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query {
	public int id;
	public int method;
    public int ttl;
	public String url;
	public Map<String, String> params;
    public Map<String, File> file_params;

	public static class QueryBuilder {
		private final Query query = new Query();
        private StringBuilder url;
        private StringBuilder urlParams;

        public QueryBuilder id(int id) {
			query.id = id;
			return this;
		}
		
		public QueryBuilder url(String url) {
			this.url = new StringBuilder(url);
			return this;
		}

        public QueryBuilder ttl(int second) {
            query.ttl = second;
            return this;
        }

        public QueryBuilder bodyParam(String key, long value) {
            return bodyParam(key, String.valueOf(value));
        }
		
		public QueryBuilder bodyParam(String key, String value) {
			if (query.params == null) {
				query.params = new HashMap<String, String>();
			}
			query.params.put(key, value);
			return this;
		}
		
		public QueryBuilder urlParam(String key, String value) {
			if (urlParams == null) {
				urlParams = new StringBuilder();
			}
			
			try {
                urlParams.append(key).append('=').append(URLEncoder.encode(value, "UTF-8")).append('&');
            } catch (UnsupportedEncodingException ex) {}
			return this;
		}

        public QueryBuilder fileParam(String key, File value) {
            if (query.file_params == null) {
                query.file_params = new HashMap<String, File>();
            }
            query.file_params.put(key, value);
            return this;
        }

        public QueryBuilder appendUrlParam(String url) {
            if (urlParams == null) {
                urlParams = new StringBuilder();
            }

            urlParams.append(url).append('&');
            return this;

        }

        public QueryBuilder addIntegerArrayAsUrlParameter(String aKey, ArrayList<Integer> aArray) {
            for (Integer id : aArray) {
                String param = aKey + "=" + String.valueOf(id);
                appendUrlParam(param);
            }
            return this;
        }

        public QueryBuilder addStringArrayAsUrlParameter(String aKey, ArrayList<String> aArray) {
            for (String id : aArray) {
                String param = aKey + "=" + URLEncoder.encode(id);
                appendUrlParam(param);
            }
            return this;
        }

        public QueryBuilder urlParam(String key, long ... params) {
            String result = "";
            for (Number param : params) {
                result += param + ",";
            }

            if (result.lastIndexOf(',') != -1)
                return urlParam(key, result.substring(0, result.lastIndexOf(',')));
            return this;
        }

        public QueryBuilder urlParam(String key, ArrayList<Long> params) {
            String result = "";
            for(Number param: params) {
                result+=param + ",";
            }

            if (result.lastIndexOf(',') != -1)
                return urlParam(key, result.substring(0, result.lastIndexOf(',')));
            return this;
        }

		
		public QueryBuilder path(String path) {
			if (url.charAt(url.length() - 1) != '/' && path.charAt(0) != '/') {
                url.append('/');
            }
            if(url.charAt(url.length() -1) == '/' && path.charAt(0) == '/') {
                url.deleteCharAt(url.length() - 1);
            }
			url.append(path);
			return this;
		}
        public QueryBuilder path(Number path)
        {
            return path(String.valueOf(path));
        }

        public QueryBuilder enums(long ... params) {
            String result = "";
            for(Number param: params) {
                result+=param + ",";
            }

            if (result.lastIndexOf(',') != -1)
                return path(result.substring(0, result.lastIndexOf(',')));
            else
                return this;

        }

        public QueryBuilder enums(List<Long> params) {
            String result = "";
            for(Long param: params) {
                result+=param + ",";
            }

            if (result.lastIndexOf(',') != -1)
                return path(result.substring(0, result.lastIndexOf(',')));
            else
                return this;

        }
		
		public QueryBuilder method(int method) {
			query.method = method;
			return this;
		}

		public Query build() {
			if (urlParams != null) {
                url.append('?').append(urlParams).delete(url.length() - 1, url.length());
            }
			query.url = url.toString();
			return query;
		}
	}
}
