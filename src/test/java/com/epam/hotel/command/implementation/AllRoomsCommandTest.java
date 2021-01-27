package com.epam.hotel.command.implementation;

import com.epam.hotel.command.CommandResult;
import com.epam.hotel.exception.ServicesException;
import com.epam.hotel.logic.service.RoomServiceImpl;
import com.epam.hotel.logic.service.api.RoomService;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AllRoomsCommandTest {
    private static final String PARAMETER_PAGE = "page";
    private static final String PARAMETER_COMMAND = "command";
    private static final String ALL_ROOMS_PAGE = "WEB-INF/view/allRooms.jsp";
    private static final int DEFAULT_PAGE = 1;
    private static final int ITEMS_PER_PAGE = 7;
    private static final long AMOUNT_PAGES = 3L;
    private static final String ALL_ROOMS_COMMAND = "allRooms";
    private static final String INVALID_PAGE_NUMBER = "6";
    private static final String VALID_PAGE_NUMBER = "3";
    private AllRoomsCommand allRoomsCommand;
    private RoomService roomService;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeMethod
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        roomService = mock(RoomServiceImpl.class);
        allRoomsCommand = new AllRoomsCommand(roomService);
    }

    @Test
    public void testExecuteShouldReturnForwardAllRoomsWhenDefaultPage() throws ServicesException {
        when(request.getParameter(PARAMETER_PAGE)).thenReturn(null);
        when(request.getParameter(PARAMETER_COMMAND)).thenReturn(ALL_ROOMS_COMMAND);
        when(roomService.findAmountPages(ITEMS_PER_PAGE)).thenReturn(AMOUNT_PAGES);
        when(roomService.findPageRooms(ITEMS_PER_PAGE, DEFAULT_PAGE)).thenReturn(new ArrayList<>());

        CommandResult expected = CommandResult.forward(ALL_ROOMS_PAGE);
        CommandResult actual = allRoomsCommand.execute(request, response);

        Assert.assertEquals(expected, actual);

    }

    @Test
    public void testExecuteShouldReturnForwardAllRoomsWhenPageValid() throws ServicesException {
        when(request.getParameter(PARAMETER_PAGE)).thenReturn(VALID_PAGE_NUMBER);
        when(request.getParameter(PARAMETER_COMMAND)).thenReturn(ALL_ROOMS_COMMAND);
        when(roomService.findAmountPages(ITEMS_PER_PAGE)).thenReturn(AMOUNT_PAGES);
        when(roomService.findPageRooms(ITEMS_PER_PAGE, DEFAULT_PAGE)).thenReturn(new ArrayList<>());

        CommandResult expected = CommandResult.forward(ALL_ROOMS_PAGE);
        CommandResult actual = allRoomsCommand.execute(request, response);

        Assert.assertEquals(expected, actual);
    }

    @Test(expectedExceptions = ServicesException.class)
    public void testExecuteShouldThrowExceptionWhenPageNotValid() throws ServicesException {
        when(request.getParameter(PARAMETER_PAGE)).thenReturn(INVALID_PAGE_NUMBER);
        when(request.getParameter(PARAMETER_COMMAND)).thenReturn(ALL_ROOMS_COMMAND);
        when(roomService.findAmountPages(ITEMS_PER_PAGE)).thenReturn(AMOUNT_PAGES);
        when(roomService.findPageRooms(ITEMS_PER_PAGE, DEFAULT_PAGE)).thenReturn(new ArrayList<>());

        allRoomsCommand.execute(request, response);
    }
}