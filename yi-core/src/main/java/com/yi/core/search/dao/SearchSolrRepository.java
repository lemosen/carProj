//package com.yi.core.search.dao;
//
//import com.yi.core.search.domain.SearchableCommodityDefinition;
//import com.yi.core.search.domain.SearchSolrVo;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.solr.core.query.result.HighlightPage;
//import org.springframework.data.solr.repository.Highlight;
//import org.springframework.data.solr.repository.Query;
//import org.springframework.data.solr.repository.SolrCrudRepository;
//
//import java.util.Collection;
//
//public interface SearchSolrRepository extends SolrCrudRepository<SearchSolrVo, Integer> {
//
////    @Highlight(prefix = "<b>", postfix = "</b>")
////    @Query(fields = {SearchableCommodityDefinition.ID_FIELD_NAME, SearchableCommodityDefinition.NAME_FIELD_NAME,
////            SearchableCommodityDefinition.PRICE_FIELD_NAME, SearchableCommodityDefinition.SHORT_NAME_FIELD_NAME,
////            SearchableCommodityDefinition.DESC_FIELD_NAME}, defaultOperator = org.springframework.data.solr.core.query.Query.Operator.AND)
////    HighlightPage<SearchSolrVo> findByNameIn(Collection<String> names, Pageable page);
//
//    @Highlight(prefix = "<b>", postfix = "</b>")
//    Page<SearchSolrVo> findByNameContaining(String name, Pageable pageable);
//}
