package cn.itrip.dao;

import cn.itrip.pojo.ItripHotelVO;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.List;

public class BaseDao {
    private static String url="http://localhost:8080/solr-4.9.1/HotelCore";
    HttpSolrClient httpSolrClient;
    public BaseDao()
    {
        httpSolrClient = new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser()); // 设置响应解析器
        httpSolrClient.setConnectionTimeout(500); // 建立连接的最长时间
    }
    //分页获取酒店
    public Page<ItripHotelVO> getlist1(SolrQuery query,int index,int psize) throws SolrServerException, IOException {

        query.setStart((index-1)*6);
        query.setRows(psize);
        QueryResponse queryResponse = null;
        queryResponse = httpSolrClient.query(query);
        List<ItripHotelVO> list = queryResponse.getBeans(ItripHotelVO.class);
       // public Page(int curpage, Integer pagesize, Integer total) {
        SolrDocumentList solrDocuments = ((QueryResponse) queryResponse).getResults();
        //当前页，每页多少个，总条数
        Page page=new Page(index,psize,new Long(solrDocuments.getNumFound()).intValue());
        page.setRows(list);
        return page;
    }
    //获取城市下的热门酒店
    public List<ItripHotelVO> getlist( SolrQuery query ) throws SolrServerException, IOException {

        QueryResponse queryResponse = null;
        queryResponse = httpSolrClient.query(query);
        List<ItripHotelVO> list = queryResponse.getBeans(ItripHotelVO.class);
        return list;
    }

    //获取搜索条件相关的酒店  分页功能
}
