package com.rsp.rsp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

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
	public List<Contacts> findByOrg(Long orgId) {
		List<Contacts> list = this.contactsRepository.findAll((Specification<Contacts>) (root, query, criteriaBuilder) -> {
            Predicate p1 = criteriaBuilder.equal(root.get("orgId").as(Long.class), orgId);
            Order order =criteriaBuilder.asc(root.get("order"));
            query.where(criteriaBuilder.and(p1)).orderBy(order);
            return query.getRestriction();
        });
		//返回对象
		List<Contacts> result = new ArrayList<>();
		//固定对象
		Map<Long,Contacts> fixedMap = new LinkedHashMap<>();
		//随机对象
		Map<String,Contacts> randomMap = new HashMap<>();
		//循环组装固定和随机对象
		list.forEach(con->{
			if(con.getOrder()!=null) {
				fixedMap.put(con.getOrder(), con);
			}else {
				randomMap.put(UUID.randomUUID().toString(), con);
			}
		});
		//循环添加对象
		for(long i=1,j=list.size();i<=j;i++) {
			Contacts con = fixedMap.get(i);
			//如果当前index对应的对象在固定map中不存在  则从随机对象中取第一个
			if(con==null) {
				//获取随机对象map中的第一个 同时删除第一个
				con = getFirst(randomMap);
			}else {
				fixedMap.remove(i);
			}
			//此时代表当前index对象不在随机map中  在固定map中  但是固定map中的key比当前index大  此时从固定map中取第一个  
			if(con==null) {
				con = getFixedFirst(fixedMap);
			}
			
			result.add(con);
		}
		//循环过后如果固定map中还存在数据  则追加到result中
		if(fixedMap.size()>0) {
			for (Entry<Long, Contacts> contacts : fixedMap.entrySet()) {
				result.add(contacts.getValue());
			}
		}
		return result;
	}
	
	private Contacts getFirst(Map<String,Contacts> map) {
		Contacts result = null;
		String first = null;
		//获取第一个对象  获取后删除
		for (String key : map.keySet()) {
			result = map.get(key);
			if(result!=null) {
				first = key;
				break;
			}
		}
		map.remove(first);
		return result;
	}
	
	private Contacts getFixedFirst(Map<Long,Contacts> map) {
		Contacts result = null;
		Long first = null;
		//获取第一个对象  获取后删除
		for (Long key : map.keySet()) {
			result = map.get(key);
			if(result!=null) {
				first = key;
				break;
			}
		}
		map.remove(first);
		return result;
	}

	@Override
	public Contacts valOrder(Contacts con) {
		if(con.getOrder()!=null) {
        	Optional<Contacts> val = contactsRepository.findOne((Specification<Contacts>) (root, query, criteriaBuilder) -> {
                Predicate p1 = criteriaBuilder.equal(root.get("orgId").as(Long.class), con.getOrgId());
                Predicate p2 = criteriaBuilder.isNotNull(root.get("order").as(Long.class));
                Predicate p3 = criteriaBuilder.equal(root.get("order").as(Long.class), con.getOrder());
                Predicate p4 = criteriaBuilder.notEqual(root.get("id").as(Long.class), con.getId());
                query.where(criteriaBuilder.and(p1,p2,p3,p4));
                return query.getRestriction();
            });
            if(val.isPresent()) {
            	return val.get();
            };
        }
		return null;
	}
}
