package com.example.contact.management.dao;

import com.example.contact.management.dto.ContactDTO;
import com.example.contact.management.dto.FetchContactsRequest;
import com.example.contact.management.service.CommonUtilityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ContactCriteriaDAO extends AbstractCriteriaDAO {


    public Page<ContactDTO> fetchContacts(FetchContactsRequest fetchContactsRequest){
        final Pageable pageable = CommonUtilityService.getPageable(fetchContactsRequest.getRecordSize(),fetchContactsRequest.getPageNumber());

        final String selectString = "select new com.example.contact.management.dto.ContactDTO(c.contactCode,c.firstName,c.lastName,c.email,c.phoneNumber,u.id,c.status)" +
                "from Contact c join c.user u where ";

        List<String> whereBuilder = new ArrayList<>();
        Map<String,Object> queryBuilder = new HashMap<>();

        whereBuilder.add("u.id =:userId");
        queryBuilder.put("userId",fetchContactsRequest.getUserId());

        if(StringUtils.hasText(fetchContactsRequest.getSearchString())){
            whereBuilder.add("(c.email like concat('%',concat(:searchStr)) or c.firstName like concat('%',concat(:searchStr)) or c.lastName like concat('%',concat(:searchStr)) )");
            queryBuilder.put("searchStr",fetchContactsRequest.getSearchString());
        }

        final String whereClause = whereBuilder.stream().collect(Collectors.joining(" and "));
        final TypedQuery<ContactDTO> query = entityManager.createQuery(selectString + whereClause,ContactDTO.class);

        final TypedQuery<Long> countQuery = entityManager.createQuery("select count(c.id) from Contact c join c.user u where " + whereClause,Long.class);

        // set parameters
        queryBuilder.entrySet().stream().forEach(param -> {
            query.setParameter(param.getKey(),param.getValue());
            countQuery.setParameter(param.getKey(),param.getValue());
        });

        return executeQuery(pageable,query,countQuery);
    }
}
