package icm.censo.a3_code.com.censoicm;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Censo;
import service.CensoService;
import service.CensoServiceImpl;
import validator.CensoValidator;
import validator.CensoValidatorImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by luucasAlbuq on 29/06/2017.
 */
public class CensoServiceTest {

    private static CensoService censoServiceMock;
    private static Censo censoMock;
    private static CensoValidator censoValidator;

    @BeforeClass
    public static void setUp(){
        censoServiceMock = mock(CensoServiceImpl.class);
        censoValidator = mock(CensoValidatorImpl.class);
        censoMock = mock(Censo.class);
    }

    @Test
    public void saveValidCensoTest() throws Exception {
        Assert.assertNotNull(censoMock);
        when(censoServiceMock.cadastrar(censoMock)).thenReturn(true);
        censoServiceMock.cadastrar(censoMock);
        verify(censoServiceMock,times(1)).cadastrar(censoMock);
        Assert.assertTrue(censoServiceMock.cadastrar(censoMock));
    }

    @Test(expected = Exception.class)
    public void saveNullCensoTest() throws Exception {
        when(censoServiceMock.cadastrar(null)).thenThrow(new Exception());
        censoServiceMock.cadastrar(null);
        verify(censoValidator,times(1)).isCensoValidForSave(censoMock);
        verify(censoServiceMock,times(1)).cadastrar(censoMock);
    }

    @Test
    public void deleteCensoTest() throws Exception {
        when(censoMock.getId()).thenReturn("15646");
        when(censoServiceMock.delete(censoMock.getId())).thenReturn(true);
        Assert.assertTrue(censoServiceMock.delete(censoMock.getId()));
        //verify(censoValidator, times(1)).isCensoValidForDelete(censoMock.getId());
        verify(censoServiceMock,times(1)).delete(censoMock.getId());
    }

    @Test(expected = Exception.class)
    public void deleteInvalidCensoTest() throws Exception {
        when(censoMock.getId()).thenReturn(null);
        when(censoServiceMock.delete(censoMock.getId())).thenThrow(new Exception());
        censoServiceMock.delete(censoMock.getId());

        when(censoMock.getId()).thenReturn("");
        when(censoServiceMock.delete(censoMock.getId())).thenThrow(new Exception());
        censoServiceMock.delete(censoMock.getId());

        verify(censoValidator, times(2)).isCensoValidForDelete(censoMock.getId());
        verify(censoServiceMock,times(2)).delete(censoMock.getId());
    }

    @Test
    public void updateCensoTest() throws Exception {
        when(censoMock.getId()).thenReturn("12345");
        when(censoServiceMock.atualizar(censoMock, censoMock.getId())).thenReturn(true);
        Assert.assertTrue(censoServiceMock.atualizar(censoMock,censoMock.getId()));
        verify(censoServiceMock, times(1)).atualizar(censoMock, censoMock.getId());
        //verify(censoValidator,times(1)).isCensoValidForUpdate(censoMock,censoMock.getId());
    }

    @Test(expected = Exception.class)
    public void updateInvalidCensoTest() throws Exception{
        when(censoMock.getId()).thenReturn("12345");
        when(censoServiceMock.atualizar(censoMock, "555")).thenThrow(new Exception());
        when(censoServiceMock.atualizar(censoMock, null)).thenThrow(new Exception());
        when(censoServiceMock.atualizar(null, "555")).thenThrow(new Exception());
        when(censoServiceMock.atualizar(null, null)).thenThrow(new Exception());
        censoServiceMock.atualizar(censoMock,"555");
        censoServiceMock.atualizar(censoMock,null);
        censoServiceMock.atualizar(null,"555");
        censoServiceMock.atualizar(null, null);

        verify(censoServiceMock, times(4)).atualizar((Censo) any(),(String)any());
        verify(censoValidator,times(4)).isCensoValidForUpdate((Censo) any(),(String)any());
    }

    @Test
    public void getCensoByDataTest() throws Exception{
        Date date = mock(Date.class);
        when(censoServiceMock.getCensoByData(date)).thenReturn(ArgumentMatchers.<Censo>anyList());
        Assert.assertNotNull(censoServiceMock.getCensoByData(date));
        verify(censoServiceMock, times(1)).getCensoByData(date);
    }

    @Test(expected = Exception.class)
    public void getCensoByInvalidDataTest() throws Exception{
        when(censoServiceMock.getCensoByData(null)).thenThrow(new Exception());
        Assert.assertNull(censoServiceMock.getCensoByData(null));
        verify(censoServiceMock, times(1)).getCensoByData(null);
    }

    @Test
    public void getCensoBetweenDates() throws Exception{
        Date date = mock(Date.class);
        List<Censo> result = new ArrayList<>();
        result.add(mock(Censo.class));
        when(censoServiceMock.getCensoBetweenDates(date,date)).thenReturn(result);
        Assert.assertNotNull(censoServiceMock.getCensoBetweenDates(date,date));
        Assert.assertEquals(censoServiceMock.getCensoBetweenDates(date,date),result);
        verify(censoServiceMock,times(2)).getCensoBetweenDates(date,date);
    }

    @Test(expected = Exception.class)
    public void getCensoBetweenInvalidDates() throws Exception{
        Date dateFirst = mock(Date.class);
        Date dateLast = mock(Date.class);
        when(dateFirst.after(dateLast)).thenReturn(true);
        when(censoServiceMock.getCensoBetweenDates(dateFirst,dateLast)).thenThrow(new Exception());
        when(censoServiceMock.getCensoBetweenDates(dateFirst,null)).thenThrow(new Exception());
        when(censoServiceMock.getCensoBetweenDates(null,dateLast)).thenThrow(new Exception());
        when(censoServiceMock.getCensoBetweenDates(null,null)).thenThrow(new Exception());
        censoServiceMock.getCensoBetweenDates(dateFirst,dateLast);
        censoServiceMock.getCensoBetweenDates(dateFirst,null);
        censoServiceMock.getCensoBetweenDates(null,dateLast);
        censoServiceMock.getCensoBetweenDates(null,null);

        verify(censoServiceMock,times(4)).getCensoBetweenDates((Date) any(), (Date) any());
        verify(censoValidator,times(4)).isCensoValidSearchBetweenDates((Date) any(), (Date) any());
    }

}
