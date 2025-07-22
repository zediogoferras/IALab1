import org.junit.jupiter.api.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import static org.junit.Assert.*;

class PuzzleUnitTests {

    @Test
    public void testConstructor(){
        Board b = new Board("023145678");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter ( writer ) ;
        pw.println(" 23");
        pw.println("145");
        pw.println("678");
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }

    @Test
    public void testConstructor2() {
        Board b = new Board("123485670");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter (writer) ;
        pw.println("123");
        pw.println("485");
        pw.println("67 ");
        assertEquals(b.toString(), writer.toString());
        pw.close();
    }
    @org.junit.Test
    public void testEquals()
    {
        Board b1 = new Board("123485670");
        Board b2 = new Board("123485670");
        assertTrue(b1.equals(b2));
    }

    @org.junit.Test
    public void testNotEquals()
    {
        Board b1 = new Board("123485670");
        Board b2 = new Board("123485271");
        assertFalse(b1.equals(b2));
    }


    @org.junit.Test
    public void testIsGoal()
    {
        Board b1 = new Board("123485670");
        Board b2 = new Board("123485670");
        assertTrue(b1.isGoal(b2));
    }

    @org.junit.Test
    public void testIsGoal1()
    {
        Board b1 = new Board("123485670");
        Board b2 = new Board("123485671");
        assertFalse(b1.isGoal(b2));
    }

    @org.junit.Test
    public void testChildren() throws CloneNotSupportedException {
        Board b1 = new Board("123485670");
        Board b2 = new Board("123485670");
        assertTrue(b1.children().equals(b2.children()));
    }

    @org.junit.Test
    public void testChildren1() throws CloneNotSupportedException {
        Board b1 = new Board("123485670");
        Board b2 = new Board("123485671");
        assertFalse(b1.children().equals(b2.children()));
    }

}