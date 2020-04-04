package org.levelup.dao;

import com.sun.istack.Nullable;
import org.levelup.model.Role;
import org.levelup.model.UserRole;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class RoleDao extends AbstractDao<Role>  implements Dao<Role>{
    public RoleDao(EntityManager manager) {
        super(manager);
    }

    @Override
    public Role create(Role newRole) {
        verify(newRole);
        return persist(newRole);
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

    @Override
    public long delete(long id) throws Exception {
        try {
            manager.getTransaction().begin();
            Role role = manager.find(Role.class, id);
            if (role == null) {
                throw new Exception("Role with id = " + id + " doesn't exist.");
            }
            manager.remove(role);
            manager.getTransaction().commit();
            return role.getId();
        } catch (Exception e) {
            throw new Exception("Role id = " + id + " can't be deleted. " + e.getMessage());
        }
    }

    @Override
    protected void verify(Role role) {
        if (role == null) {
           role = new Role(UserRole.USER);
        }
    }
}
