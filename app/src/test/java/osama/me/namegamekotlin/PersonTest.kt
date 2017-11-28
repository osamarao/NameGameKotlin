package osama.me.namegamekotlin;


import org.junit.Assert
import org.junit.Test;

class PersonTest {

    @Test
    fun givenNullUrlAssertThatPlaceholderIsReturned() {
        val person = Person("", "", "", "", "", "",
                Headshot("", "", "", null, "", 0, 3),
                null)

        Assert.assertEquals("http:placeholder", person.headshot.trueUrl)
    }
}
