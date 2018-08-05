package com.rsp.rsp.service.impl;

import com.rsp.rsp.dao.OrgRepository;
import com.rsp.rsp.dao.query.OrgQuery;
import com.rsp.rsp.domain.Org;
import com.rsp.rsp.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * 机构
 * @author sjb
 */
@Service
public class OrgServiceImpl implements OrgService {

    @Autowired
    private OrgRepository orgRepository;

    /**
     * 查询全部
     * @return
     */
    @Override
    public List<Org> findAll() {
        return orgRepository.findAll();
    }

    /**
     * 只有分页没有查询条件
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Org> findOrgNoCriteria(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return orgRepository.findAll(pageable);
    }

    /**
     * 有查询条件的分页
     * @param page
     * @param size
     * @param orgQuery
     * @return
     */
    @Override
    public Page<Org> findOrgCriteria(Integer page, Integer size, OrgQuery orgQuery) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<Org> bookPage = orgRepository.findAll((Specification<Org>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.equal(root.get("name").as(String.class), orgQuery.getName());
//            Predicate p2 = criteriaBuilder.equal(root.get("isbn").as(String.class), orgQuery.getIsbn());
//            Predicate p3 = criteriaBuilder.equal(root.get("author").as(String.class), orgQuery.getAuthor());
//            query.where(criteriaBuilder.and(p1,p2,p3));
            query.where(criteriaBuilder.and(p1));
            return query.getRestriction();
        },pageable);
        return bookPage;

    }

    @Override
    public void save(Org org) {
        orgRepository.save(org);
    }

    @Override
    public void update(Org newOrg) {
        Org org = orgRepository.findById(newOrg.getId());
        //org set value
        orgRepository.save(org);
    }

    @Override
    public void delete(Long id) {
        orgRepository.deleteById(id);
    }
}
