package com.rsp.rsp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.rsp.rsp.dao.ContactsRepository;
import com.rsp.rsp.domain.Contacts;
import com.rsp.rsp.domain.query.ContactsQuery;
import com.rsp.rsp.service.ContactsService;

/**
 * 大类
 * @author sjb
 */
@Service
public class ContactsServiceImpl implements ContactsService {

    @Autowired
    private ContactsRepository contactsRepository;

    @Override
    public List<Contacts> findAll() {
        return contactsRepository.findAll();
    }

    @Override
    public Page<Contacts> findContactsNoCriteria(Integer start, Integer size) {
        Pageable pageable = PageRequest.of(start/size, size, Sort.Direction.ASC, "orgId","order");
        return contactsRepository.findAll(pageable);
    }

    @Override
    public Page<Contacts> findContactsCriteria(Integer start, Integer size, ContactsQuery contactsQuery) {
        Pageable pageable = PageRequest.of(start/size, size, Sort.Direction.ASC, "orgId","order");
        Page<Contacts> bookPage = contactsRepository.findAll((Specification<Contacts>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.like(root.get("name").as(String.class), "%"+contactsQuery.getsSearch()+"%");
            Predicate p2 = criteriaBuilder.like(root.get("phone").as(String.class), "%"+contactsQuery.getsSearch()+"%");
            Predicate p3 = criteriaBuilder.like(root.get("orgName").as(String.class), "%"+contactsQuery.getsSearch()+"%");
            query.where(criteriaBuilder.or(p1,p2,p3));
            return query.getRestriction();
        },pageable);
        return bookPage;
    }

    @Override
    public Contacts save(Contacts Contacts) {
        return contactsRepository.save(Contacts);
    }

    @Override
    public void update(Contacts newContacts) {
        Contacts contacts = contactsRepository.findById(newContacts.getId());
        contacts.setName(newContacts.getName());
        contacts.setPhone(newContacts.getPhone());
        contacts.setOrgId(newContacts.getOrgId());
        contacts.setOrgName(newContacts.getOrgName());
        contacts.setOrder(newContacts.getOrder());
        contactsRepository.save(contacts);
    }

    /**
     * 删除大类时删除小类和小类对应的公式
     * @param id
     */
    @Override
    public void delete(Long id) {
        contactsRepository.deleteById(id);
    }

    @Override
    public Contacts findById(Long id) {
        return contactsRepository.findById(id);
    }

	@Override
	public Map<String, List<Contacts>> findByOrg(Long orgId) {
		Map<String,List<Contacts>> result = new HashMap<>();
		List<Contacts> slider = new ArrayList<>();
		List<Contacts> fixed = new ArrayList<>();
		List<Contacts> list = this.contactsRepository.findAll((Specification<Contacts>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.equal(root.get("orgId").as(Long.class), orgId);
            Order order =criteriaBuilder.asc(root.get("order"));
            query.where(criteriaBuilder.and(p1)).orderBy(order);
            return query.getRestriction();
        });
		list.forEach(con->{
			if(con.getOrder()==null) {
				slider.add(con);
			}else {
				fixed.add(con);
			}
		});
		result.put("slider", slider);
		result.put("fixed", fixed);
		return result;
	}

}
