package com.epam.hotel.logic.service;

import com.epam.hotel.dao.DaoHelper;
import com.epam.hotel.dao.DaoHelperFactory;
import com.epam.hotel.dao.api.UserDtoDao;
import com.epam.hotel.entity.dto.UserDto;
import com.epam.hotel.exception.DaoException;
import com.epam.hotel.exception.ServicesException;
import com.epam.hotel.logic.service.api.UserDtoService;

import java.sql.SQLException;
import java.util.List;

public class UserDtoServiceImpl implements UserDtoService {
    private final DaoHelperFactory daoHelperFactory;

    public UserDtoServiceImpl(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    @Override
    public long findAmountPages(int itemsPerPage) throws ServicesException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDtoDao dao = daoHelper.createUserDtoDao();
            long totalUsers = dao.findRowCount();
            if (totalUsers <= itemsPerPage) {
                return 1;
            }
            return (int) Math.ceil(totalUsers / (double) itemsPerPage);
        } catch (DaoException | SQLException e) {
            throw new ServicesException(e);
        }
    }

    @Override
    public List<UserDto> findPageUsers(int itemsPerPage, int page) throws ServicesException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDtoDao dao = daoHelper.createUserDtoDao();
            int begin = (page - 1) * itemsPerPage;
            return dao.findPaginatePage(itemsPerPage, begin);
        } catch (DaoException | SQLException e) {
            throw new ServicesException(e);
        }
    }
}
