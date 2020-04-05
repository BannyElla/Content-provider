package org.levelup.dao;

import com.sun.istack.Nullable;
import org.levelup.model.Role;
import org.levelup.model.UserRole;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
@Qualifier("RoleDao")
public class RoleDao extends AbstractDao<Role> implements Dao<Role> {
    @Override
    @Transactional
    public Role create(Role newRole) {
        verify(newRole);
        manager.persist(newRole);
        return newRole;
    }

    @Nullable
    public Role findByName(UserRole name) {
        String sql = "select r from Role r where r.name =: name";
        try {
            return manager.createQuery(sql, Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public long delete(long id) throws Exception {
        return delete(id, Role.class);
    }

    @Override
    protected void verify(Role role) {
        if (role == null) {
            role = new Role(UserRole.USER);
        }
    }

    @Override
    public Role update(Role role) throws Exception {
        throw new Exception("The method isn't implemented");
    }
}
