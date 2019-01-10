//package com.yi.core.search.service.impl;
//
//import com.yi.core.search.dao.SearchSolrRepository;
//import com.yi.core.search.domain.SearchSolrVo;
//import com.yi.core.search.service.ISearchSolrService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//@Service
//@Transactional
//public class SearchSolrServiceImpl implements ISearchSolrService {
//
//    @Autowired
//    SearchSolrRepository searchSolrRepository;
//
//    @Override
//    public void save(int id,String name,String desc){
//        SearchSolrVo searchSolrVo=new SearchSolrVo();
//        searchSolrVo.setId(id);
//        searchSolrVo.setName(name);
//        searchSolrVo.setDesc(desc);
//        searchSolrRepository.save(searchSolrVo);
//    }
//
//    @Override
//    public Page<SearchSolrVo> query(String queryString, Pageable pageable) throws Exception {
//        return searchSolrRepository.findByNameContaining(queryString, pageable);
//    }
//}
