package com.rsp.rsp.service.impl;

import com.rsp.rsp.dao.OrgRepository;
import com.rsp.rsp.domain.query.OrgQuery;
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
     * @param start
     * @param size
     * @return
     */
    @Override
    public Page<Org> findOrgNoCriteria(Integer start, Integer size) {
        Pageable pageable = PageRequest.of(start/size, size, Sort.Direction.ASC, "id");
        return orgRepository.findAll(pageable);
    }

    /**
     * 有查询条件的分页
     * @param start
     * @param size
     * @param orgQuery
     * @return
     */
    @Override
    public Page<Org> findOrgCriteria(Integer start, Integer size, OrgQuery orgQuery) {
        Pageable pageable = PageRequest.of(start/size, size, Sort.Direction.ASC, "id");
        Page<Org> bookPage = orgRepository.findAll((Specification<Org>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+orgQuery.getsSearch()+"%");
//            Predicate p2 = criteriaBuilder.equal(root.get("id").as(String.class), orgQuery.getsSearch());
            query.where(criteriaBuilder.or(p1));
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
        org.setName(newOrg.getName());
        org.setLimit(newOrg.getLimit());
        org.setTerm(newOrg.getTerm());
        org.setLogo(newOrg.getLogo());
        org.setContacts(newOrg.getContacts());
        org.setDesc(newOrg.getDesc());
        org.setInterestRate(newOrg.getInterestRate());
        org.setMaterial(newOrg.getMaterial());
        org.setPhone(newOrg.getPhone());
        org.setRequirements(newOrg.getRequirements());
        org.setStrengths(newOrg.getStrengths());
        orgRepository.save(org);
    }

    @Override
    public void delete(Long id) {
        orgRepository.deleteById(id);
    }

    @Override
    public Org findById(Long id) {
        return orgRepository.findById(id);
    }
}
