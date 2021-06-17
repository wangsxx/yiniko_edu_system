import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @File : test.java
 * @Time : 2021/6/10 10:58
 * @Author: Wangs
 * @Decs :
 */
public class test {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat formatA  = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = formatA.parse("20141030133525");
        System.out.println(new Date());
    }
}
