package cn.itrip;

import cn.itrip.pojo.ItripHotel;
import cn.itrip.pojo.ItripHotelVO;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.List;

public class TextSolr {
    private static String url="http://localhost:8080/solr-4.9.1/HotelCore";
    public static void main(String[] args) throws SolrServerException, IOException {
        HttpSolrClient httpSolrClient = new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser()); // 设置响应解析器
        httpSolrClient.setConnectionTimeout(500); // 建立连接的最长时间
        SolrQuery query = new SolrQuery("*:*");
        query.setSort("id", SolrQuery.ORDER.asc);
        query.setStart(0);
        query.setRows(10);
        QueryResponse queryResponse = null;
            queryResponse = httpSolrClient.query(query);
            List<ItripHotelVO> list = queryResponse.getBeans(ItripHotelVO.class);
            for (ItripHotelVO hotel:list){
                System.out.println(hotel.getId()+hotel.getHotelName()+hotel.getAddress());
            }

    }
}
