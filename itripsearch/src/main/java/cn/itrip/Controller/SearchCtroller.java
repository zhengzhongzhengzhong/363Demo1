package cn.itrip.Controller;

import cn.itrip.dao.BaseDao;
import cn.itrip.dao.Page;
import cn.itrip.pojo.ItripHotelVO;
import cn.itrip.pojo.SearchHotCityVO;
import cn.itrip.pojo.SearchHotelVO;
import cn.itrip.util.Dto;
import cn.itrip.util.DtoUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class SearchCtroller {

     @RequestMapping("/api/hotellist/searchItripHotelPage")
     @ResponseBody
     public Dto get2(@RequestBody SearchHotelVO vo) throws SolrServerException, IOException {
          SolrQuery solrQuery=new SolrQuery("*:*");
          // 原有字符 featureIds: "7,8"
          // 截取：  featureIds:*,120,* or  featureIds:*,170,*

          if(vo.getTradeAreaIds()!=null&&vo.getTradeAreaIds()!="")
          {
               String [] str=vo.getTradeAreaIds().split(",");
               String pjstr="";
               for(int i=0;i<str.length;i++)
               {
                    if(i==0) {
                         pjstr += "tradingAreaIds:*," + str[i] + ",*";
                    }else{
                         pjstr += " or tradingAreaIds    :*," + str[i] + ",*";
                    }
               }
               solrQuery.addFilterQuery(pjstr);
          }

          //descSort:"hotelLevel"
         // ascSort:"hotelLevel"
          if(vo.getAscSort()!=null&&vo.getAscSort()!="")
          {
               solrQuery.setSort(vo.getAscSort(), SolrQuery.ORDER.asc);
          }

          if(vo.getDescSort()!=null&&vo.getDescSort()!="")
          {
               solrQuery.setSort(vo.getDescSort(), SolrQuery.ORDER.desc);
          }

          if(vo.getMaxPrice()!=null&&vo.getMaxPrice()!=0)
          {
               solrQuery.addFilterQuery("minPrice:[ 0 TO "+vo.getMaxPrice()+"]");
          }
          if(vo.getMinPrice()!=null&&vo.getMinPrice()!=0)
          {
               solrQuery.addFilterQuery("minPrice:[ "+vo.getMinPrice()+" TO *]");
          }



          if(vo.getFeatureIds()!=null&&vo.getFeatureIds()!="")
          {
               String [] str=vo.getFeatureIds().split(",");
               String pjstr="";
               for(int i=0;i<str.length;i++)
               {
                    if(i==0) {
                         pjstr += "featureIds:*," + str[i] + ",*";
                    }else{
                         pjstr += " or featureIds:*," + str[i] + ",*";
                    }
               }
               solrQuery.addFilterQuery(pjstr);
          }


          if(vo.getDestination()!=null&&vo.getKeywords()!="")
          {
               solrQuery.addFilterQuery("destination:"+vo.getDestination());
          }
          if(vo.getKeywords()!=null&&vo.getKeywords()!="")
          {
               solrQuery.addFilterQuery("keyword:"+vo.getKeywords());
          }
          if(vo.getHotelLevel()!=null)
          {
               solrQuery.addFilterQuery("hotelLevel:"+vo.getHotelLevel());
          }
          if(vo.getPageNo()==null)
          {
               vo.setPageNo(1);
          }
          if(vo.getPageSize()==null)
          {
               vo.setPageSize(6);
          }
          BaseDao dao=new BaseDao();
          Page<ItripHotelVO> obj=dao.getlist1(solrQuery,vo.getPageNo(),vo.getPageSize());
          return DtoUtil.returnDataSuccess(obj);
     }



     @RequestMapping("/api/hotellist/searchItripHotelListByHotCity")
     @ResponseBody
     public Dto get1(@RequestBody SearchHotCityVO vo) throws SolrServerException, IOException {

          BaseDao dao=new BaseDao();
          SolrQuery solrQuery=new SolrQuery("*:*");
          solrQuery.addFilterQuery("cityId:"+vo.getCityId());
          List<ItripHotelVO> obj=dao.getlist(solrQuery);
          return DtoUtil.returnDataSuccess(obj);
     }
}
