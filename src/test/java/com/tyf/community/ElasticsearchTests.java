package com.tyf.community;

import com.tyf.community.dao.DiscussPostMapper;
import com.tyf.community.dao.elasticsearch.DiscussPostRepository;
import com.tyf.community.entity.DiscussPost;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ElasticsearchTests {
    @Autowired
    public DiscussPostMapper discussPostMapper;
    @Autowired
    public DiscussPostRepository discussPostRepository;
    @Autowired
    public ElasticsearchTemplate elasticsearchTemplate;

    /**
     *一条数据
     */
    @Test
    public void testInsert(){
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(109));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(110));
    }

    /**
     * 多条数据
     */
    @Test
        public void testInsertAll(){
            discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(101,0,5));
            discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(102,0,5));
    }

    //修改
    @Test
    public void testupdate(){
        DiscussPost discussPost = discussPostMapper.selectDiscussPostById(109);
        discussPost.setContent("撒野");
        discussPostRepository.save(discussPost);
    }

    //修改
    @Test
    public void testdelete(){
       discussPostRepository.deleteById(109);
    }

    //搜索
    @Test
    public void testSearchByRepository(){
        //构造搜索条件
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬","title","content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0,10))
                .withHighlightFields(
                        //高亮显示
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")

                        ).build();

        //底层获取到了高亮显示的值，但是没有返回
        // 将高亮实现的加入原内容中，elasticsearch没有自动返回，解决方法，用Template
         Page<DiscussPost> page = discussPostRepository.search(searchQuery);
         System.out.println(page.getTotalElements());
         System.out.println(page.getTotalPages());
         System.out.println(page.getNumber());
         System.out.println(page.getSize());

         for(DiscussPost discussPost : page){
             System.out.println(discussPost);
         }

    }

    @Test
    public void testSearchByTemplate(){
        //构造搜索条件
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬","title","content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0,10))
                .withHighlightFields(
                        //高亮显示
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")

                ).build();

        //底层获取到了高亮显示的值，但是没有返回
        // 将高亮实现的加入原内容中，elasticsearch没有自动返回，解决方法，用Template
        Page<DiscussPost> page = elasticsearchTemplate.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits searchHits = searchResponse.getHits();
                if(searchHits.getTotalHits() <= 0){
                    return null;
                }
                List<DiscussPost> list = new ArrayList<>();
                for(SearchHit hits : searchHits){
                    DiscussPost post = new DiscussPost();
                    String id = hits.getSourceAsMap().get("id").toString();
                    post.setId(Integer.valueOf(id));

                    String userId = hits.getSourceAsMap().get("userId").toString();
                    post.setUserId(Integer.valueOf(userId));

                    String title =  hits.getSourceAsMap().get("title").toString();
                    post.setTitle(title);

                    String content = hits.getSourceAsMap().get("content").toString();
                    post.setContent(content);

                    String status = hits.getSourceAsMap().get("status").toString();
                    post.setUserId(Integer.valueOf(status));

                    String createTime = hits.getSourceAsMap().get("createTime").toString();
                    post.setCreateTime(new Date(Long.valueOf(createTime)));

                    String commentCount = hits.getSourceAsMap().get("commentCount").toString();
                    post.setCommentCount(Integer.valueOf(commentCount));

                    //处理高亮显示的结果
                    HighlightField titleField = hits.getHighlightFields().get("title");
                    if(titleField != null){
                        post.setTitle(titleField.getFragments()[0].toString());
                    }

                    HighlightField contentField = hits.getHighlightFields().get("content");
                    if(contentField != null){
                        post.setContent(contentField.getFragments()[0].toString());
                    }
                    list.add(post);

                }
                return new AggregatedPageImpl(list,pageable,
                        searchHits.getTotalHits(),
                        searchResponse.getAggregations(),
                        searchResponse.getScrollId(),
                        searchHits.getMaxScore());
            }
        });
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        System.out.println(page.getNumber());
        System.out.println(page.getSize());

        for(DiscussPost discussPost : page){
            System.out.println(discussPost);
        }

    }
}
