package org.ivan.core.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

public class Es {

	// 连接
	private static TransportClient client;
	// 索引库名称，一般都是一个库，只是里边的type不同，比如user/goods
	private static final String index = "zhoulibo";

	public Es() {
		// 通过 setting对象来指定集群配置信息
		Settings settings = Settings.builder()//
				.put("client.transport.sniff", true)// 自动嗅探发现集群节点
				.put("client.transport.ignore_cluster_name", true)// 忽略集群名称
				.put("xpack.security.user", "elastic:changeme")// 安全认证
				.build();
		client = new PreBuiltXPackTransportClient(settings);

		try {
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.128.128"), 9300));
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.128.129"), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description: 关闭连接
	 * @author kang
	 * @date 2017年5月11日
	 */
	public void close() {
		client.close();
	}

	/**
	 * 
	 * @Description: 验证链接是否正常
	 * @author kang
	 * @date 2017年5月11日
	 */
	public boolean validate() {
		return client.connectedNodes().size() == 0 ? false : true;
	}

	/**
	 * 
	 * @Description:添加文档
	 * @author kang
	 * @date 2017年1月3日
	 */
	public void addDoc(String type, Object id, Object object) {
		client.prepareIndex(index, type, id.toString()).setSource(JSON.toJSONString(object)).get();
	}

	/**
	 * 
	 * @Description:更新文档
	 * @author kang
	 * @date 2017年1月3日
	 */
	public void updateDoc(String type, Object id, Object object) {
		client.prepareUpdate(index, type, id.toString()).setDoc(JSON.toJSONString(object)).get();
	}

	/**
	 * 
	 * @Description:删除文档
	 * @author kang
	 * @date 2017年1月3日
	 */
	public void delDoc(String type, Object id) {
		client.prepareDelete(index, type, id.toString()).get();
	}

	/**
	 * 
	 * @Description: 分页高亮查询
	 * @param fields 查询的字段集合
	 * @author kang
	 * @date 2017年1月11日
	 */
	public Page getDocHighLight(String keywords, String type, Set<String> fields, int currentPage, int pageSize, boolean isHighlight) throws Exception {
		// 搜索数据
		SearchResponse response = client.prepareSearch(index).setTypes(type)//
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)//
				.setQuery(QueryBuilders.multiMatchQuery(keywords, fields.toArray(new String[fields.size()]))// 查询所有字段
						.analyzer("ik_max_word"))// 分词器
				.highlighter(new HighlightBuilder().preTags("<span style=\"color:red\">").postTags("</span>").field("*"))// 高亮标签
				.setFrom((currentPage - 1) * pageSize).setSize(pageSize)// 分页
				.setExplain(true)// 评分排序
				.execute().actionGet();

		// 获取查询结果集
		SearchHits searchHits = response.getHits();
		List<Object> result = Lists.newArrayList();
		// 反射填充高亮
		for (SearchHit hit : searchHits) {
			Map<String, Object> source = hit.getSource();
			if (isHighlight) {
				// 获取对应的高亮域
				Map<String, HighlightField> highlight = hit.getHighlightFields();
				for (String field : fields) {
					// 从设定的高亮域中取得指定域
					HighlightField titleField = highlight.get(field);
					if (titleField == null) continue;
					// 取得定义的高亮标签
					String texts = StringUtils.join(titleField.fragments());
					source.put(field, texts);
				}
			}
			result.add(JSON.toJSON(source));
		}
		return new Page(currentPage, pageSize, (int) searchHits.totalHits(), result);
	}

	/**
	 * 
	 * @Description: 重构索引(更新词库之后)
	 * @author kang
	 * @date 2017年5月16日
	 */
	public void reindex() {
		SearchResponse scrollResp = client.prepareSearch(index)//
				.setScroll(new TimeValue(60000))//
				.setQuery(QueryBuilders.matchAllQuery())//
				.setSize(100).get(); // max of 100 hits will be returned for
		// Scroll until no hits are returned
		do {
			for (SearchHit hit : scrollResp.getHits().getHits()) {
				client.prepareIndex(index, hit.getType(), hit.getId()).setSource(hit.getSourceAsString()).execute().actionGet();
			}
			scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
		} while (scrollResp.getHits().getHits().length != 0);
	}
	/**
	 * 创建索引
	 * @param indexName 索引名
	 * @return
	 */
	public boolean addIndex(String indexName){
		CreateIndexRequestBuilder  indexResponse =client.admin().indices().prepareCreate(indexName);
		System.out.println(indexResponse.request());
		return true;
	}
	/**
	 * 删除索引
	 * @param indexName
	 */
	public void delIndex(String indexName){
		client.admin().indices().prepareDelete(indexName);
		System.out.println("删除索引成功！");
	}
	/**
	 * 判断索引是否存在
	 * @param index
	 * @return
	 */
	 public boolean isIndexExist(String index) {
		return client.admin().indices().prepareExists(index).execute().actionGet().isExists();
	 }
	 /**
	  * 判断某个索引下type是否存在
	  * @param index
	  * @param type
	  * @return
	  */
	 public boolean isTypeExist(String index, String type) {
		return client.admin().indices().prepareTypesExists(index).setTypes(type).execute().actionGet().isExists();
	 }
	 /**
	  * 创建type（存在则进行更新）
	  * @param index 	索引名称
	  * @param type 	type名称
	  * @param o 		要设置type的object
	  * @return
	  */
	public boolean createType(String index, String type, Object o) {
		if (!isIndexExist(index)) {
			System.out.println("索引不存在");
			return false;
		}
		try {
			// 若type存在则可通过该方法更新type
			return client.admin().indices().preparePutMapping(index).setType(type).setSource(o).get().isAcknowledged();
		} catch (Exception e) {
			System.out.println("创建type失败:" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 根据文件ID获取数据
	 * @param indexName	索引名字
	 * @param typeName	类型名字
	 * @param docId		文件ID
	 */
	public String  getWithId(String indexName, String typeName, String docId) {
		GetResponse gResponse = client.prepareGet(indexName, typeName, docId).execute().actionGet();
//		System.out.println(gResponse.getIndex());
//		System.out.println(gResponse.getType());
//		System.out.println(gResponse.getVersion());
//		System.out.println(gResponse.isExists());
		Map<String, Object> results = gResponse.getSource();
//		if (results != null) {
//			for (String key : results.keySet()) {
//				Object field = results.get(key);
//				System.out.println(key);
//				System.out.println(field);
//			}
//		}
		return JSON.toJSONString(results);
	}
}
