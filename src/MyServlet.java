import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Enumeration;

/**
 * Created by Александр on 01.05.2016.
 */
@WebServlet(name = "MyServlet")
public class MyServlet extends HttpServlet {
    private String resultFileName = "result.txt";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Enumeration<String> parameterNames = request.getParameterNames();

        int packageNumber = 0;
        String checksum = null;
        byte[] data = null;
        boolean isLast = false;

        byte[] digest;
        MessageDigest messageDigest;

        while (parameterNames.hasMoreElements()) {
            String param = parameterNames.nextElement();
            String paramValue = request.getParameter(param);
            if (param.equals("number")) {
                packageNumber = Integer.parseInt(paramValue);
                continue;
            }
            if (param.equals("checksum")) {
                checksum = paramValue;
                continue;
            }
            if (param.equals("data")) {
                data = Base64.getDecoder().decode(paramValue.getBytes());
                continue;
            }
            if (param.equals("isLast")) {
                isLast = Boolean.parseBoolean(paramValue);
                continue;
            }
        }
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            if (data != null) {
                messageDigest.update(data);
            } else {
                response.getWriter().println("Repeat please");
                return;
            }
            digest = messageDigest.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String md5Hex = bigInt.toString(16);

            while( md5Hex.length() < 32 ){
                md5Hex = "0" + md5Hex;
            }
            if (checksum != null) {
                if (md5Hex.equals(checksum)) {
                    if (!FileCollector.isStartCollect()) {
                        FileCollector.resetCollector(System.getenv("CATALINA_HOME"), resultFileName);
                    }
                    if (FileCollector.isFileCollected()) {
                        response.getWriter().println("File has been received");
                        return;
                    }
                    FileCollector.addPackage(new FilePackage(packageNumber, checksum, data, isLast));
                    response.getWriter().println("OK");
                } else {
                    response.getWriter().println("Repeat please");
                }
            } else {
                response.getWriter().println("Repeat please");
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
