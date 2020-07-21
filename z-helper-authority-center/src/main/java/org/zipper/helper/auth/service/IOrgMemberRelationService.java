package org.zipper.helper.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zipper.helper.auth.entity.relation.OrgMemberRelation;

import java.util.Collection;

/**
 * @author zhuxj
 * @since 2020/07/12
 */
public interface IOrgMemberRelationService extends IService<OrgMemberRelation> {
    boolean deleteByMemberId(Long id);

    boolean deleteInMemberIds(Collection<Long> ids);

    boolean existMember(Long id);

    boolean existMembers(Collection<Long> ids);
}
