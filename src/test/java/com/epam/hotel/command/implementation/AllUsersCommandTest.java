package com.epam.hotel.command.implementation;

import com.epam.hotel.command.CommandResult;
import com.epam.hotel.exception.ServicesException;
import com.epam.hotel.logic.service.UserDtoServiceImpl;
import com.epam.hotel.logic.service.UserServiceImpl;
import com.epam.hotel.logic.service.api.UserDtoService;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class AllUsersCommandTest {

    private static final String PARAMETER_PAGE = "page";
    private static final String PARAMETER_COMMAND = "command";
    private static final String ALL_INVOICES_PAGE = "WEB-INF/view/allUsers.jsp";
    private static final String ALL_INVOICES_COMMAND = "allUsers";
    private static final int DEFAULT_PAGE = 1;
    private static final int ITEMS_PER_PAGE = 7;
    private static final long AMOUNT_PAGES = 3L;
    private static final String INVALID_PAGE_NUMBER = "6";
    private static final String VALID_PAGE_NUMBER = "3";
    private static final String NULL_VALUE = null;
    private final UserDtoService userDtoService = mock(UserDtoServiceImpl.class);
    private final AllUsersCommand allUsersCommand = new AllUsersCommand(userDtoService);
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeMethod
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    public void testExecuteShouldReturnForwardAllUsersWhenDefaultPage() throws ServicesException {
        when(request.getParameter(PARAMETER_PAGE)).thenReturn(NULL_VALUE);
        when(request.getParameter(PARAMETER_COMMAND)).thenReturn(ALL_INVOICES_COMMAND);
        when(userDtoService.findAmountPages(ITEMS_PER_PAGE)).thenReturn(AMOUNT_PAGES);
        when(userDtoService.findPageUsers(ITEMS_PER_PAGE, DEFAULT_PAGE)).thenReturn(new ArrayList<>());

        CommandResult expected = CommandResult.forward(ALL_INVOICES_PAGE);
        CommandResult actual = allUsersCommand.execute(request, response);

        Assert.assertEquals(actual, expected);
        verify(request, times(4)).setAttribute(anyString(), any());
    }

    @Test
    public void testExecuteShouldReturnForwardAllUsersWhenPageValid() throws ServicesException {
        when(request.getParameter(PARAMETER_PAGE)).thenReturn(VALID_PAGE_NUMBER);
        when(request.getParameter(PARAMETER_COMMAND)).thenReturn(ALL_INVOICES_COMMAND);
        when(userDtoService.findAmountPages(ITEMS_PER_PAGE)).thenReturn(AMOUNT_PAGES);
        when(userDtoService.findPageUsers(ITEMS_PER_PAGE, DEFAULT_PAGE)).thenReturn(new ArrayList<>());

        CommandResult expected = CommandResult.forward(ALL_INVOICES_PAGE);
        CommandResult actual = allUsersCommand.execute(request, response);

        Assert.assertEquals(actual, expected);
        verify(request, times(4)).setAttribute(anyString(), any());
    }

    @Test(expectedExceptions = ServicesException.class)
    public void testExecuteShouldThrowExceptionWhenPageNotValid() throws ServicesException {
        when(request.getParameter(PARAMETER_PAGE)).thenReturn(INVALID_PAGE_NUMBER);
        when(request.getParameter(PARAMETER_COMMAND)).thenReturn(ALL_INVOICES_COMMAND);
        when(userDtoService.findAmountPages(ITEMS_PER_PAGE)).thenReturn(AMOUNT_PAGES);
        when(userDtoService.findPageUsers(ITEMS_PER_PAGE, DEFAULT_PAGE)).thenReturn(new ArrayList<>());

        allUsersCommand.execute(request, response);
        verify(request, times(0)).setAttribute(anyString(), any());
    }
}