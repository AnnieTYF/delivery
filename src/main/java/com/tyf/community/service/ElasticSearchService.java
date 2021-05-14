package com.tyf.community.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ElasticSearchService {

    @Autowired
    private DiscussPostRepository discussPostRepository;
    @Autowired
    private ElasticsearchTemplate template;

    /**
     * 存取帖子
     * @param discussPost
     */
    public void saveDiscussPost(DiscussPost discussPost){
        discussPostRepository.save(discussPost);
    }

    /**
     * 删除帖子
     * @param id
     */
    public void deleteDiscussPost(int id){
        discussPostRepository.deleteById(id);
    }

    /**
     * 搜索帖子
     * @param keyWord
     * @param current
     * @param limit
     * @return
     */
    public Page<DiscussPost> searchDiscussPost(String keyWord, int current, int limit){
        //构造搜索条件
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyWord,"title","content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(current,limit))
                .withHighlightFields(
                        //高亮显示
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")

                ).build();

        //底层获取到了高亮显示的值，但是没有返回
        // 将高亮实现的加入原内容中，elasticsearch没有自动返回，解决方法，用Template
       return  template.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper() {
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
    }



}
