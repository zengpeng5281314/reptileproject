package com.mytest.bean_es;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.search.MultiMatchQuery.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class ElasticSearchGet {

	public static void main(String[] args) {
		  // client startup
        try {
            Client client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

            //单个关键字搜索
            TermQueryBuilder qb1 = QueryBuilders.termQuery("title", "hibernate");
            SearchResponse response = client.prepareSearch("blog").setTypes("article").setQuery(qb1).execute()
                    .actionGet();
            SearchHits hits = response.getHits();
            if (hits.totalHits() > 0) {
                for (SearchHit hit : hits) {
                    System.out.println("score:"+hit.getScore()+":\t"+hit.getSource());// .get("title")
                }
            } else {
                System.out.println("搜到0条结果");
            }
            
            //多个关键字搜索
            MultiMatchQueryBuilder qb2= QueryBuilders.multiMatchQuery("git", "title","content");
            SearchResponse response2 = client.prepareSearch("blog").setTypes("article").setQuery(qb2).execute()
                    .actionGet();

            SearchHits hits2 = response2.getHits();
            if (hits2.totalHits() > 0) {
                for (SearchHit hit : hits2) {
                    System.out.println("----score:"+hit.getScore()+":\t"+hit.getSource());// .get("title")
                }
            } else {
                System.out.println("搜到0条结果");
            }

            client.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
	}

}
