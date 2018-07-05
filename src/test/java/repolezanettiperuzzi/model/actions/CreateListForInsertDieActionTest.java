package repolezanettiperuzzi.model.actions;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

//test classe createlistforinsertdieaction
public class CreateListForInsertDieActionTest {

    CreateListForInsertDieAction testCreateListForInsertDieAction=new CreateListForInsertDieAction();
    String stringFromClient="1 223 21111";

    //testo che svolga l'azione in modo corretto e ritorni i codici giusti di errore
    @Test
    public void testDoAction() {

        ArrayList<Integer> parameter=new ArrayList<>();
        parameter=(ArrayList<Integer>)testCreateListForInsertDieAction.doAction(stringFromClient);
        int n1=parameter.get(0);
        int n2=parameter.get(1);
        int n3=parameter.get(2);

        assertEquals(3,parameter.size());
        assertEquals(1,n1);
        assertEquals(223,n2);
        assertEquals(21111,n3);
    }
}