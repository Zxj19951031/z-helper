package org.zipper.helper.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zipper.helper.auth.entity.Member;

import java.util.Collection;

/**
 * @param <T> 用户实体类
 * @author zhuxj
 * @since 2020/07/12
 */
public interface IMemberService<T extends Member> extends IService<T> {
    /**
     * 逻辑删除某一个用户
     *
     * @param id 用户ID
     * @return 删除结果
     */
    public boolean delete(Long id);

    /**
     * 逻辑删除一些用户
     *
     * @param ids 用户IDs
     * @return 删除结果
     */
    public boolean delete(Collection<Long> ids);

}
